package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.type.PlayerChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp4
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadPlayer

class PlayerChatSystem : DialogueBaseSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>

    @Subscribe
    private fun playerChat(action: PlayerChat) {
        val entityId = action.entity
        //Choose window
        val id = when (action.lines.size) {
            1 -> if (action.`continue`) Chat1 else ChatNp1
            2 -> if (action.`continue`) Chat2 else ChatNp2
            3 -> if (action.`continue`) Chat3 else ChatNp3
            4 -> if (action.`continue`) Chat4 else ChatNp4
            else -> return logger.warn("Invalid player chat $action")
        }
        //Open
        openDialogue(entityId, id)

        //Send model
        es.send(entityId, InterfaceHeadPlayer(id, if (action.large) 1 else 2))
        es.send(entityId, InterfaceAnimation(id, if (action.large) 1 else 2, action.animation))
        //Send title
        es.send(entityId, InterfaceComponentText(id, 3, displayNameMapper.get(entityId)?.name ?: ""))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 4 + index, line))
        }
    }
}