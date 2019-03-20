package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import world.gregs.hestia.core.network.protocol.messages.FriendsChatName
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.MessageHandlerSystem
import worlds.gregs.hestia.network.client.decoders.messages.StringEntry

class StringEntryHandler : MessageHandlerSystem<StringEntry>() {

    override fun initialize() {
        super.initialize()
        GameServer.gameMessages.bind(this)
    }

    override fun handle(entityId: Int, message: StringEntry) {
        //TODO handling/call back other things
        GameServer.worldSession?.write(FriendsChatName(entityId, message.text))
    }

}