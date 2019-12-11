package worlds.gregs.hestia.core.client.systems.network.`in`

import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.artemis.events.StringEntered
import worlds.gregs.hestia.game.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.StringEntry

class StringEntryHandler : MessageHandlerSystem<StringEntry>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    private lateinit var es: EventSystem

    override fun handle(entityId: Int, message: StringEntry) {
        es.dispatch(StringEntered(entityId, message.text))
    }

}