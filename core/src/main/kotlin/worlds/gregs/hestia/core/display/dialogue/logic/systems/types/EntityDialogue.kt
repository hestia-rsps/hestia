package worlds.gregs.hestia.core.display.dialogue.logic.systems.types

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import org.slf4j.LoggerFactory
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.api.LinesDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueBaseSystem
import worlds.gregs.hestia.core.display.dialogue.model.Expression.Talking
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.mob.api.Mob
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.entity
import worlds.gregs.hestia.core.task.api.world
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadMob
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadPlayer
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItem
import worlds.gregs.hestia.network.client.encoders.messages.WindowWidgetAnimation
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import worlds.gregs.hestia.service.cache.definition.systems.MobDefinitionSystem

/**
 * For quickly identifying entity dialogues
 */
abstract class EntityDialogue : TaskType<Unit>, LinesDialogue()

@Wire(injectInherited = true)
class EntityDialogueSystem : DialogueBaseSystem() {

    override val logger = LoggerFactory.getLogger(EntityDialogueSystem::class.java)!!
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var itemDefinitions: ItemDefinitionSystem
    private lateinit var mobDefinitions: MobDefinitionSystem

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspension(event: ProcessTaskSuspension) {
        val (entityId, dialogue) = event
        if (dialogue is EntityDialogue) {
            val window = ENTITY_ID + dialogue.lines.size - 1
            val title = getTitle(entityId, dialogue)
            send(entityId, window, 3, title, dialogue.lines)
            when (dialogue) {
                is ItemDialogue -> es.send(entityId, WidgetItem(window, 2, dialogue.item, -1))
                is MobDialogue -> {
                    es.send(entityId, WidgetHeadMob(window, 2, dialogue.mob))
                    es.send(entityId, WindowWidgetAnimation(window, 2, dialogue.animation))
                }
                is PlayerDialogue -> {
                    es.send(entityId, WidgetHeadPlayer(window, 2))
                    es.send(entityId, WindowWidgetAnimation(window, 2, dialogue.animation))
                }
            }
            event.isCancelled = true
        }
    }

    internal fun getTitle(entityId: Int, dialogue: EntityDialogue): String? {
        return dialogue.title ?: when (dialogue) {
            is ItemDialogue -> itemDefinitions.get(dialogue.item).name
            is MobDialogue -> mobDefinitions.get(dialogue.mob).name
            is PlayerDialogue -> displayNameMapper.get(entityId).name
            else -> null
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleContinue(event: ContinueDialogue) {
        val (entityId, _, option, _) = event
        val suspension = tasks.getSuspension(entityId)
        if (suspension is EntityDialogue) {
            //Verify button
            val continueButton = 3 + suspension.lines.size + 1
            if (continueButton != -1 && option != continueButton) {
                return logger.debug("Unexpected button press: $event")
            }
            //Continue
            tasks.resume(entityId, suspension, Unit)
            event.isCancelled = true
        }
    }

}

data class DialogueBuilder(val target: Int, var animation: Int = Talking, var title: String? = null, var message: String = "")

@Throws(UnsupportedOperationException::class)
suspend fun Task.dialogue(builder: DialogueBuilder) {
    if (builder.target == entity) {
        player(builder.message, builder.animation, builder.title)
    } else if (builder.target has Mob::class) {
        val type = builder.target get Type::class
        mob(builder.message, type.id, builder.animation, builder.title)
    } else {
        val definitions = world system ItemDefinitionSystem::class
        if (builder.target < definitions.reader.size) {//TODO Check if needs to be <=
            val type = builder.target get Type::class
            item(builder.message, type.id, builder.title)
        } else if (builder.target != -1) {
            throw UnsupportedOperationException("Unknown target $builder")
        }
    }
}
