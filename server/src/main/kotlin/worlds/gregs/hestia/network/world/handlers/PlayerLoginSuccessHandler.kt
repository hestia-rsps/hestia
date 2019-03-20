package worlds.gregs.hestia.network.world.handlers

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.cache.crypto.Isaac
import world.gregs.hestia.core.network.codec.Codec
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.codec.message.SimpleIsaacMessageEncoder
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.core.network.protocol.messages.PlayerLoginSuccess
import worlds.gregs.hestia.GameServer
import worlds.gregs.hestia.game.events.CreatePlayer
import worlds.gregs.hestia.network.client.ClientConnection
import worlds.gregs.hestia.network.client.ClientPacketDecoder
import worlds.gregs.hestia.network.client.encoders.messages.LoginDetails

class PlayerLoginSuccessHandler(private val sessions: HashMap<Int, ChannelHandlerContext>, codec: Codec) : MessageHandler<PlayerLoginSuccess> {

    init {
        PlayerLoginSuccessHandler.codec = codec
    }

    override fun handle(ctx: ChannelHandlerContext, message: PlayerLoginSuccess) {
        val (session, name, isaacKeys, mode, width, height) = message
        val playerSession = sessions[session]
        if (playerSession == null) {
            logger.info("Error finding session $session $name")
            return
        }
        addDetails(name, playerSession, isaacKeys)
        GameServer.eventSystem.dispatch(CreatePlayer(playerSession.getSession(), name, mode, width, height))
        sessions.remove(session)
    }

    companion object {

        private lateinit var codec: Codec

        private val logger = LoggerFactory.getLogger(PlayerLoginSuccessHandler::class.java)!!

        fun switchPipeline(name: String) {
            val details = sessionDetails[name]
            if (details == null) {
                logger.info("Error finding session $name")
                return
            }

            val playerSession = details.first
            val isaacKeys = details.second

            val inRandom = Isaac(isaacKeys)
            for (i in isaacKeys.indices)
                isaacKeys[i] += 50
            val outRandom = Isaac(isaacKeys)

            playerSession.pipeline().apply {
                get(ClientPacketDecoder::class.java).cipher = inRandom
                replace("encoder", "encoder", object : SimpleIsaacMessageEncoder(codec, outRandom) {
                    override fun encode(ctx: ChannelHandlerContext, msg: Message, out: ByteBuf) {
                        if (msg is LoginDetails) {
                            val encoder = codec.get(msg::class) as? MessageEncoder<Message>
                                    ?: run {
                                        logger.warn("No encoder for message: $msg")
                                        return
                                    }

                            try {
                                encoder.write(msg, out, null)
                            } catch (t: Throwable) {
                                t.printStackTrace()
                            }
                        } else {
                            super.encode(ctx, msg, out)
                        }
                    }
                })
                addLast(ClientConnection())
            }
        }

        fun addDetails(name: String, session: ChannelHandlerContext, isaacKeys: IntArray) {
            sessionDetails[name] = Pair(session, isaacKeys)
        }

        private val sessionDetails = HashMap<String, Pair<ChannelHandlerContext, IntArray>>()
    }
}