package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.type.ItemBox
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ObjBox
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSprite
import worlds.gregs.hestia.network.client.encoders.messages.Script

class ItemBoxSystem : DialogueBaseSystem() {

    @Subscribe
    private fun itemBox(action: ItemBox) {
        val entityId = action.entity
        val id = ObjBox
        openDialogue(entityId, id)

        es.send(entityId, Script(3449, action.model, action.zoom))
        if(action.sprite != null) {
            es.send(entityId, InterfaceSprite(id, 3, action.sprite))
        }
        es.send(entityId, InterfaceComponentText(id, 1, action.text))
    }
}