package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.display.window.model.events.ButtonClick
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.WidgetOption

class WidgetOptionHandler : MessageHandlerSystem<WidgetOption>() {

    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: WidgetOption) {
        val (hash, fromSlot, toSlot, option) = message
        es.dispatch(ButtonClick(entityId, hash, fromSlot, toSlot, option))
    }

}