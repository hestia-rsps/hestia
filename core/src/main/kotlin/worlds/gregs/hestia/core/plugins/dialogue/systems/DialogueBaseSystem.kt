package worlds.gregs.hestia.core.plugins.dialogue.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.api.task.Tasks
import worlds.gregs.hestia.core.plugins.dialogue.components.Dialogue
import worlds.gregs.hestia.core.plugins.task.components.TaskQueue
import worlds.gregs.hestia.core.plugins.task.components.getDeferral
import worlds.gregs.hestia.core.plugins.widget.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.services.send

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
    }
}