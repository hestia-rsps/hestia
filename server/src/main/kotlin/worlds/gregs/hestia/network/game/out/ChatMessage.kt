package worlds.gregs.hestia.network.game.out

import com.artemis.Entity
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.formatName
import worlds.gregs.hestia.game.events.send

//TODO duplicate in social server
class ChatMessage(text: String, type: Int, name: String?) : Packet.Builder(102, Packet.Type.VAR_BYTE) {

    constructor(text: String, filter: Boolean = false) : this(text, if (filter) 109 else 0, null)

    init {
        var maskData = 0
        val different = name != name.formatName()
        if (name != null) {
            maskData = maskData or 0x1
            if (different) {
                maskData = maskData or 0x2
            }
        }

        writeSmart(type)
        writeInt(0)//Tile position hash = y + (x << 14) + (plane << 28)
        writeByte(maskData)
        if (name != null) {
            writeString(name)
            if (different) {
                writeString(name.formatName())
            }
        }
        writeString(text)
    }
    /*
    Chat message types
    0 - Normal game message
    1 - Chat message (can't be hidden/admin)
    2 - Chat message
    3 - From private message
    5 - Red highlighted private chat message
    6 - To private message
    7 - From private message (can't be hidden/admin)
    9 - Friends chat message
    11 - Friends chat channel game-message
    17 - Chat message (quick message?)
    18 - From private message
    19 - To private message
    20 - Friends chat message (quick chat?)
    30 - Private channel game-message (can't hide)
    31 - Private channel game-message (can't hide)
    41 - Clan chat message
    42 - Clan chat message
    43 - Clan chat channel game-message
    44 - Clan chat message orange
    45 - Clan chat message orange
    98 - console text?
    99 - console message
    100 - trade
    101 - challenge
    102 - give assistance
    103 - Trade channel game-message
    104 - Assist channel game-message
    105 - challenge
    106 - challenge
    107 - clan war challenge
    108 - alliance
    109 - filterable game message
     */
}

fun Entity.message(message: String, filter: Boolean = false) {
    send(ChatMessage(message, filter))
}