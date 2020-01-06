package worlds.gregs.hestia.core.display.client.logic.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.core.action.perform
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.game.entity.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.StringEntry

class StringEntryHandler : MessageHandlerSystem<StringEntry>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var es: EventSystem

    override fun handle(entityId: Int, message: StringEntry) {
        es.perform(entityId, StringEntered(message.text))
    }

}