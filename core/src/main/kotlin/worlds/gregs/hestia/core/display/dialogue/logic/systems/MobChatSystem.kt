package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.type.MobChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobChatNp4
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadMob
import worlds.gregs.hestia.service.cache.definition.systems.MobDefinitionSystem

class MobChatSystem : DialogueBaseSystem() {

    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var definitions: MobDefinitionSystem

    @Subscribe
    private fun mobChat(action: MobChat) {
        val entityId = action.entity
        val mobType = typeMapper.get(action.mob).id
        //Choose window
        val id = when (action.lines.size) {
            1 -> if (action.`continue`) MobChat1 else MobChatNp1
            2 -> if (action.`continue`) MobChat2 else MobChatNp2
            3 -> if (action.`continue`) MobChat3 else MobChatNp3
            4 -> if (action.`continue`) MobChat4 else MobChatNp4
            else -> return logger.warn("Invalid mob chat $action")
        }
        //Open
        openDialogue(entityId, id)
        //Send model
        es.send(entityId, InterfaceHeadMob(id, if (action.large) 1 else 2, mobType))
        es.send(entityId, InterfaceAnimation(id, if (action.large) 1 else 2, action.animation))
        //Send title
        es.send(entityId, InterfaceComponentText(id, 3, definitions.get(mobType).name))
        //Send lines
        action.lines.forEachIndexed { index, line ->
            es.send(entityId, InterfaceComponentText(id, 4 + index, line))
        }
    }
}