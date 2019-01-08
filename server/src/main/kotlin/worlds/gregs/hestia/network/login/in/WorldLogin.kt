package worlds.gregs.hestia.network.login.`in`

import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import world.gregs.hestia.core.network.packets.out.Response
import world.gregs.hestia.core.services.Cache
import world.gregs.hestia.core.services.Encryption
import worlds.gregs.hestia.game.events.CreatePlayer
import worlds.gregs.hestia.network.game.`in`.GamePacket

@PacketSize(-2)
@PacketOpcode(16)
class WorldLogin : GamePacket() {

    override fun read(session: Session, packet: Packet, length: Int): Boolean {
        /*
        val world = session.worlds[0]
        if (world.updateTime != 0L) {
            session.send(ClientResponse(AddressingFeature.Responses.SERVER_BEING_UPDATED))
            return
        }*/
        val username = packet.readString().toLowerCase().capitalize()
        packet.readUnsignedByte() // unknown
        val displayMode = packet.readUnsignedByte()
        val screenWidth = packet.readShort()
        val screenHeight = packet.readShort()
        val unknown2 = packet.readUnsignedByte()
        packet.skip(24) // 24bytes directly from a file, no idea whats there
        val settings = packet.readString()
        val affId = packet.readInt()
        packet.skip(packet.readUnsignedByte()) // useless settings
        if (packet.readUnsignedByte() != 5) {
            session.respond(Response.BAD_SESSION_ID)
            return false
        }
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readShort()
        packet.readUnsignedByte()
        packet.readTriByte()
        packet.readShort()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readUnsignedByte()
        packet.readJagString()
        packet.readJagString()
        packet.readJagString()
        packet.readJagString()
        packet.readUnsignedByte()
        packet.readShort()
        val unknown3 = packet.readInt()
        val userFlow = packet.readLong()
        val hasAdditionalInformation = packet.readUnsignedByte() == 1
        if (hasAdditionalInformation) packet.readString() // additionalInformation
        val hasJagtheora = packet.readUnsignedByte() == 1
        val js = packet.readUnsignedByte() == 1
        val hc = packet.readUnsignedByte() == 1
        for (index in 0 until Cache.indexCount()) {
            val crc = Cache.getIndex(index)?.crc ?: 0
            val receivedCRC = packet.readInt()
            if (crc != receivedCRC && index < 32) {
                session.respond(Response.GAME_UPDATED)
                return false
            }
        }
        // invalid chars
        if (username.length <= 1 || username.length >= 15 || username.contains("?") || username.contains(":")
                || username.endsWith("<") || username.contains("\\") || username.contains("*") || username.startsWith("_")
                || username.contains("\"")) {
            session.respond(Response.INVALID_CREDENTIALS)
            return false
        }
        /*if (world.players().size >= Settings.PLAYERS_LIMIT - 10) {
            session.respond(Response.WORLD_FULL)
            return
        }

        //Contains player
        if (world.players().any { it.username == username }) {
            session.respond(Response.ACCOUNT_ONLINE)
            return
        }

        val player: Player?
        if (!playerExists(username)) {
            val salt: String = session.salt()
            password = session.hashSHA(password + salt)
            player = Player(password, salt)
        } else {
            player = loadPlayer(username)
            if (player == null) {
                session.respond(Response.INVALID_LOGIN_SERVER)
                return
            }
            password = session.hashSHA(password + player.salt)
            if (player.password != password) {
                session.respond(Response.INVALID_CREDENTIALS)
                return
            }
        }

        if (player.isPermBanned || player.banned > TimeUtils.time) {
            session.respond(Response.ACCOUNT_DISABLED)
            return
        }*/
        val salt: String = Encryption.salt
        val password = Encryption.hashSHA("test$salt")
        es.dispatch(CreatePlayer(session, username/*, password*/, displayMode, screenWidth, screenHeight))
//        player.init(ClientDetails(session, username, displayMode, screenWidth, screenHeight), world)
//        session.setDecoder(3, player)
//        player.start()
        return true
    }
}