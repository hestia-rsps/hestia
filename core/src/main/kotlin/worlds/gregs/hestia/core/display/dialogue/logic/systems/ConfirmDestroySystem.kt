package worlds.gregs.hestia.core.display.dialogue.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.model.type.Destroy
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItem
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

class ConfirmDestroySystem : DialogueBaseSystem() {

    private lateinit var definitions: ItemDefinitionSystem

    @Subscribe
    private fun confirmDestroy(action: Destroy) {
        val entityId = action.entity
        val id = Interfaces.ConfirmDestroy
        //Open
        openDialogue(entityId, id)

        es.send(entityId, InterfaceComponentText(id, 7, action.text))
        es.send(entityId, InterfaceComponentText(id, 8, definitions.get(action.item).name))
        es.send(entityId, InterfaceItem(id, 9, action.item, 1))
    }
}