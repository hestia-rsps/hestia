package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.type.NpcChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp4
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadNpc
import worlds.gregs.hestia.service.cache.definition.systems.NpcDefinitionSystem

class NpcChatSystem : DialogueBaseSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var definitions: NpcDefinitionSystem

    @Subscribe
    private fun npcChat(action: NpcChat) {
        val entityId = action.entity
        val npcType = typeMapper.get(action.npc).id
        //Choose window
        val id = when (action.lines.size) {
            1 -> if (action.`continue`) NpcChat1 else NpcChatNp1
            2 -> if (action.`continue`) NpcChat2 else NpcChatNp2
            3 -> if (action.`continue`) NpcChat3 else NpcChatNp3
            4 -> if (action.`continue`) NpcChat4 else NpcChatNp4
            else -> return logger.warn("Invalid npc chat $action")
        }
        //Open
        openDialogue(entityId, id)
        //Send model
        es.send(entityId, InterfaceHeadNpc(id, if (action.large) 1 else 2, npcType))
        es.send(entityId, InterfaceAnimation(id, if (action.large) 1 else 2, action.animation))
        //Send title
        es.send(entityId, InterfaceComponentText(id, 3, definitions.get(npcType).name))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 4 + index, line))
        }
    }
}