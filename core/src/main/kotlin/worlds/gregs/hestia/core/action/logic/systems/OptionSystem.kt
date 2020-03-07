package worlds.gregs.hestia.core.action.logic.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.action.model.*
import worlds.gregs.hestia.core.display.interfaces.model.PlayerOptions
import worlds.gregs.hestia.game
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import worlds.gregs.hestia.service.cache.definition.systems.NpcDefinitionSystem
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem

private typealias OptionSetup = OptionBuilder.() -> Unit
private typealias OptionAction = EntityActions.(Int) -> Unit

class NpcOptionSystem : OptionSystem()

class ObjectOptionSystem : OptionSystem()

class FloorItemOptionSystem : OptionSystem()

class PlayerOptionSystem : OptionSystem()

abstract class OptionSystem : SubscriptionSystem(Aspect.all(ActionContext::class)) {

    val options = mutableMapOf<String, MutableMap<Int, OptionAction>>()
    private lateinit var actionContextMapper: ComponentMapper<ActionContext>

    override fun inserted(entityId: Int) {
        val actionContext = actionContextMapper.get(entityId)
        actionContext.entity = entityId
        actionContext.world = world
    }

    fun add(id: Int, option: String, action: OptionAction) {
        options.getOrPut(option) { mutableMapOf() }[id] = action
    }

    fun get(option: String, id: Int): OptionAction? {
        val options = options[option] ?: return null
        return options[id] ?: options[-1]
    }
}

class OptionBuilder(var action: (OptionAction)? = null) {
    fun then(action: OptionAction) {
        this.action = action
    }
}

fun on(o: SingleOptionType, option: String, vararg ids: Int, action: OptionAction) {
    add(o, ids, option, action)
}

fun on(o: SingleOptionType, option: String, setup: OptionSetup) {
    add(o, IntArray(0), option, getAction(setup))
}

fun on(o: SingleOptionType, option: String, vararg ids: Int, setup: OptionSetup) {
    add(o, ids, option, getAction(setup))
}

fun on(o: SingleOptionType, option: String, vararg names: String, action: OptionAction) {
    addAction(o, option, names, action)
}

fun on(o: SingleOptionType, option: String, action: OptionAction) {
    add(o, IntArray(0), option, action)
}

fun on(o: SingleOptionType, option: String, vararg names: String, setup: OptionSetup) {
    addAction(o, option, names, getAction(setup))
}

private fun addAction(o: SingleOptionType, option: String, names: Array<out String>, action: OptionAction) {
    val map = when (o) {
        is NpcOption -> game.getSystem(NpcDefinitionSystem::class).names
        is ObjectOption -> game.getSystem(ObjectDefinitionSystem::class).names
        is FloorItemOption -> game.getSystem(ItemDefinitionSystem::class).names
        is PlayerOption -> PlayerOptions.names
    }
    val ids = names.flatMap { name -> map.filter { it.first == name }.map { it.second } }.toIntArray()
    add(o, ids, option, action)
}

private fun getAction(setup: OptionSetup) = OptionBuilder().apply(setup).action!!

private fun add(o: SingleOptionType, ids: IntArray, option: String, action: OptionAction) {
    val system = when(o) {
        NpcOption -> game.getSystem(NpcOptionSystem::class)
        ObjectOption -> game.getSystem(ObjectOptionSystem::class)
        PlayerOption -> game.getSystem(PlayerOptionSystem::class)
        FloorItemOption -> game.getSystem(FloorItemOptionSystem::class)
    }
    if (ids.isEmpty()) {
        system.add(-1, option, action)
    } else {
        ids.forEach { id ->
            system.add(id, option, action)
        }
    }
}