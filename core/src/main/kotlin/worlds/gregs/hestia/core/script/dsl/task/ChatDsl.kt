package worlds.gregs.hestia.core.script.dsl.task

import world.gregs.hestia.core.network.protocol.encoders.messages.Chat
import worlds.gregs.hestia.core.task.api.Task

object ChatType {
    const val Game = 0
    const val GameFilter = 109
    const val ChatAdmin = 1
    const val Chat = 2
    const val PrivateFrom = 3
    const val PrivateRed = 4
    const val PrivateTo = 5
    const val PrivateFromAdmin = 6
    const val FriendsChat = 7
    const val FriendsChatGame = 8
    const val QuickChat = 9
    const val Trade = 100
    const val GameTrade = 103
    const val ChallengeDuel = 101
    const val Assist = 102
    const val GameAssist = 104
    const val ChallengeClanWar = 107
    const val Alliance = 108
}
data class ChatBuilder(val entity: Int, var message: String? = null, var type: Int = 0, var tile: Int = 0, var name: String? = null)


fun Task.chat(builder: ChatBuilder) {
    builder.entity.send(Chat(builder.type, builder.tile, builder.name, builder.message ?: return))
}