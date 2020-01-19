package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.type.Statement
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp5

class StatementSystem : DialogueBaseSystem() {

    @Subscribe
    private fun statement(action: Statement) {
        val entityId = action.entity
        //Choose interface
        val id = when (action.lines.size) {
            1 -> if (action.`continue`) Message1 else MessageNp1
            2 -> if (action.`continue`) Message2 else MessageNp2
            3 -> if (action.`continue`) Message3 else MessageNp3
            4 -> if (action.`continue`) Message4 else MessageNp4
            5 -> if (action.`continue`) Message5 else MessageNp5
            else -> return logger.warn("Invalid statement $action")
        }
        //Open
        openDialogue(entityId, id)
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 1 + index, line))
        }
    }
}