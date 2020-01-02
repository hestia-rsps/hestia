package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.Logger
import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.widget.logic.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.core.task.api.Tasks

abstract class DialogueBaseSystem : PassiveSystem() {
    abstract val logger: Logger
    internal lateinit var es: EventSystem
    internal lateinit var tasks: Tasks
    private lateinit var dialogueBoxSystem: DialogueBoxSystem

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
        internal const val PLAYER_ID = 64
        internal const val ENTITY_ID = 241
        internal const val STATEMENT_ID = 210
        internal const val TWO_OPTIONS_ID = 236
        internal const val THREE_OPTIONS_ID = 231
        internal const val FOUR_OPTIONS_ID = 237
        internal const val FIVE_OPTIONS_ID = 238
    }
}