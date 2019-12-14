package worlds.gregs.hestia.core.task.api

import com.artemis.BaseSystem
import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.World
import kotlinx.coroutines.CompletionHandler
import net.mostlyoriginal.api.event.common.Event
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.DialogueBuilder
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.dialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.options
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.statement
import worlds.gregs.hestia.core.display.update.model.components.ForceChat
import worlds.gregs.hestia.core.display.update.model.components.Transform
import worlds.gregs.hestia.core.entity.entity.logic.systems.update.animate
import worlds.gregs.hestia.core.entity.entity.logic.systems.update.graphic
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.events.Hit
import worlds.gregs.hestia.core.entity.item.container.api.Composition
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerTransformBuilder
import worlds.gregs.hestia.core.entity.item.container.logic.transform
import worlds.gregs.hestia.core.entity.item.container.model.Inventory
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.core.script.dsl.task.*
import worlds.gregs.hestia.core.task.logic.systems.TaskSystem
import worlds.gregs.hestia.core.world.movement.model.components.calc.Follow
import worlds.gregs.hestia.core.world.movement.model.components.types.MoveStep
import worlds.gregs.hestia.game.update.blocks.Marker
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import kotlin.coroutines.Continuation
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KClass

interface Task : Continuation<Any> {
    override val context: TaskContext

    var suspension: TaskType<*>?

    fun onCancel(handler: CompletionHandler)

    val isActive: Boolean
        get() = suspension?.continuation?.isActive ?: false

    val isCancelled: Boolean
        get() = suspension?.continuation?.isCancelled ?: false

    val isCompleted: Boolean
        get() = suspension?.continuation?.isCompleted ?: false


    /*
        Extension infix's would require [compound extensions](https://github.com/Kotlin/KEEP/pull/176/commits).
     */

    suspend fun cancel(cancellation: TaskCancellation) = suspendCoroutine<Unit> {
        val tasks = world system TaskSystem::class
        tasks.cancel(entity, cancellation)
    }

    infix fun <T : Component> Int.get(c: KClass<T>): T {
        return world.getMapper(c.java).get(this)
    }

    infix fun <T : Component> Int.create(c: KClass<T>): T {
        return world.getMapper(c.java).create(this)
    }

    infix fun <T : Component> Int.remove(c: KClass<T>) {
        world.getMapper(c.java).remove(this)
    }

    infix fun <T : Component> Int.has(c: KClass<T>): Boolean {
        return world.getMapper(c.java).has(this)
    }

    infix fun <T : BaseSystem> World.system(c: KClass<T>): T {
        return getSystem(c.java)
    }

    infix fun Int.send(message: Message) {
        val es = world.getSystem(EventSystem::class)
        es.send(this, message)
    }

    infix fun World.dispatch(event: Event) {
        val es = this system EventSystem::class
        es.dispatch(event)
    }

    /*
        Interact
     */

    infix fun Int.distance(distance: Int) = DistanceBuilder(entity = this, distance = distance)

    suspend infix fun Int.interact(target: Int) = DistanceBuilder(entity = this).interact(target)

    suspend infix fun DistanceBuilder.interact(target: Int) = this@Task.interact(InteractBuilder(this, target))

    /*
        Dialogue
     */
    infix fun Int.animation(animation: Int) = DialogueBuilder(target = this, animation = animation)

    infix fun DialogueBuilder.animation(animation: Int) = apply { this.animation = animation }

    infix fun Int.title(title: String) = DialogueBuilder(target = this, title = title)

    infix fun DialogueBuilder.title(title: String) = apply { this.title = title }

    suspend infix fun Int.dialogue(message: String) = DialogueBuilder(target = this).dialogue(message)

    suspend infix fun DialogueBuilder.dialogue(message: String) = dialogue(apply { this.message = message })

    suspend infix fun Int.statement(message: String) = DialogueBuilder(target = this).statement(message)

    suspend infix fun DialogueBuilder.statement(message: String) = statement(message, title)

    suspend infix fun Int.options(message: String) = DialogueBuilder(target = this).options(message)

    suspend infix fun DialogueBuilder.options(message: String) = options(apply { this.message = message })

    /*
        Containers
     */

    infix fun Int.overflow(overflow: Boolean) = ContainerTransformBuilder(overflow)

    infix fun Int.inventory(f: Composition) = this get Inventory::class transform f

    infix fun Inventory.transform(f: Composition) = ContainerTransformBuilder().transform(f)

    infix fun ContainerTransformBuilder.transform(f: Composition) = transform(apply { function = f })

    /**
     * Abstraction of common error messages
     */
    infix fun Int.purchase(f: Composition): Boolean {
        return when (val result = overflow(false).transform(f)) {
            is ItemResult.Success -> true
            is ItemResult.Issue -> {
                entity message when (result) {
                    ItemResult.Issue.Invalid -> {
                        log("Issue with purchase. ${(entity get Inventory::class).items.toList()}")
                        "Whoops, looks like something went wrong, please try again."
                    }
                    ItemResult.Issue.Full -> "You don't have enough inventory space."
                    is ItemResult.Issue.Underflow ->
                        "You don't have enough ${getSystem(ItemDefinitionSystem::class).get(result.item.type).name.plural(result.item.amount)}."
                }
                false
            }
            else -> false
        }
    }

    /*
        Movement
     */
    infix fun Int.move(position: Position) = move(position.x, position.y, position.plane)

    fun Int.move(x: Int, y: Int, plane: Int? = null) {
        val step = this create MoveStep::class
        step.x = x
        step.y = y
        step.plane = plane ?: (this get Position::class).plane
    }

    infix fun Int.follow(target: Int) {
        val follow = this create Follow::class
        follow.entity = target
    }
    /*
        Message
     */

    infix fun Int.message(message: String) = ChatBuilder(this).message(message)

    infix fun ChatBuilder.message(message: String) = chat(apply { this.message = message })

    infix fun Int.type(type: Int) = ChatBuilder(this, type = type)

    infix fun ChatBuilder.type(type: Int) = apply { this.type = type }

    infix fun ChatBuilder.tile(tile: Int) = apply { this.tile = tile }

    infix fun ChatBuilder.name(name: String) = apply { this.name = name }



    infix fun Int.face(target: Int) {

    }

    suspend infix fun Int.watch(target: Int) = watch(this, target)

    infix fun World.delete(target: Int) = world.delete(target)


    infix fun Int.samePosition(target: Int): Boolean {
        val position = this get Position::class
        val target = target get Position::class
        return position.same(target)
    }

    infix fun Int.notSamePosition(target: Int): Boolean = !samePosition(target)

    infix fun Int.animate(id: Int) = this@Task.animate(id)

    infix fun Int.graphic(id: Int) = this@Task.graphic(id)

    fun Int.updateAppearance() = dispatch(UpdateAppearance(this))

    infix fun Int.transform(mob: Int) {
        create(Transform::class).mobId = mob
    }

    /**
     * Force chat
     */
    infix fun Int.say(message: String) {
        create(ForceChat::class).message = message
    }

    infix fun Int.hit(amount: Int) {
        dispatch(Hit(this, amount, Marker.MELEE, 0, false, -1, -1))
    }

    fun log(message: String) = logger.warn(message)

    companion object {
        private val logger = LoggerFactory.getLogger(Task::class.java)!!
        const val FIRST = 0
        const val SECOND = 1
        const val THIRD = 2
        const val FOURTH = 3
        const val FIFTH = 4
        val SUCCESS = null
    }
}

fun <T : Component> Task.getMapper(c: KClass<T>): ComponentMapper<T> = world.getMapper(c.java)

fun <T : Component> Task.getComponent(c: KClass<T>): T = getMapper(c).get(entity)

fun <T : BaseSystem> Task.getSystem(c: KClass<T>): T = world.getSystem(c.java)