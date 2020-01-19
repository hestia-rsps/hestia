package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.type.DoubleChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat4
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadMob
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadPlayer

class DoubleChatSystem : DialogueBaseSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>

    @Subscribe
    private fun doubleChat(action: DoubleChat) {
        val entityId = action.entity
        val mobType = typeMapper.get(action.mob).id
        //Choose interface
        val id = when (action.lines.size) {
            1 -> DoubleChat1
            2 -> DoubleChat2
            3 -> DoubleChat3
            4 -> DoubleChat4
            else -> return logger.warn("Invalid double chat $action")
        }
        //Open
        openDialogue(entityId, id)
        //Send left model
        es.send(entityId, InterfaceHeadMob(id, 1, mobType))
        es.send(entityId, InterfaceAnimation(id, 1, action.animation))
        //Send right model
        es.send(entityId, InterfaceHeadPlayer(id, 2))
        es.send(entityId, InterfaceAnimation(id, 2, action.playerAnimation))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 3 + index, line))
        }
    }
}