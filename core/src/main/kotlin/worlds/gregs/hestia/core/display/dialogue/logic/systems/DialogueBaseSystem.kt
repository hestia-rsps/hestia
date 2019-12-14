package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.widget.logic.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.components.getDeferral
import worlds.gregs.hestia.artemis.send

abstract class DialogueBaseSystem : PassiveSystem() {
    private val logger = LoggerFactory.getLogger(DialogueBaseSystem::class.java)!!
    private lateinit var taskQueueMapper: ComponentMapper<TaskQueue>
    internal lateinit var es: EventSystem
    internal lateinit var taskQueue: Tasks
    private lateinit var dialogueBoxSystem: DialogueBoxSystem

    internal fun getDeferral(entityId: Int): Dialogue? {
        val defer = taskQueueMapper.getDeferral(entityId)
        return if(defer !is Dialogue) {
            logger.debug("Queue not expected dialogue $defer")
            null
        } else {
            defer
        }
    }

    internal fun send(entityId: Int, interfaceId: Int, componentStart: Int, title: String?, lines: List<String>) {
        //Open
        dialogueBoxSystem.openChatInterface(entityId, interfaceId)
        //Title
        es.send(entityId, WidgetComponentText(interfaceId, componentStart, title ?: ""))
        //Lines
        lines.forEachIndexed { index, text ->
            es.send(entityId, WidgetComponentText(interfaceId, componentStart + index + 1, text))
        }
    }

    companion object {
        internal const val ENTITY_ID = 241
        internal const val STATEMENT_ID = 210
        internal const val TWO_OPTIONS_ID = 236
        internal const val THREE_OPTIONS_ID = 231
        internal const val FOUR_OPTIONS_ID = 237
        internal const val FIVE_OPTIONS_ID = 238
    }
}