package worlds.gregs.hestia

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.link.EntityLinkManager
import io.netty.channel.ChannelHandlerContext
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.SubscribeAnnotationFinder
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.client.AutoConnection
import world.gregs.hestia.core.network.codec.ChannelFilter
import world.gregs.hestia.core.network.codec.decode.SimplePacketDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageEncoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandler
import world.gregs.hestia.core.network.pipe.SessionPipeline
import world.gregs.hestia.core.network.protocol.Details
import world.gregs.hestia.core.network.server.Network
import world.gregs.hestia.core.services.Loader
import worlds.gregs.hestia.api.client.ClientPlugin
import worlds.gregs.hestia.artemis.event.PollingEventDispatcher
import worlds.gregs.hestia.game.Engine
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.map.MapPlugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.EVENT_PROCESS_PRIORITY
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PACKET_PROCESS_PRIORITY
import worlds.gregs.hestia.game.plugin.PluginLoader
import worlds.gregs.hestia.network.WorldChangeListener
import worlds.gregs.hestia.network.client.*
import worlds.gregs.hestia.network.world.WorldCodec
import worlds.gregs.hestia.network.world.WorldConnection
import worlds.gregs.hestia.network.world.WorldDetails
import worlds.gregs.hestia.network.world.WorldMessages
import worlds.gregs.hestia.services.Xteas
import kotlin.system.measureNanoTime

class GameServer(worldDetails: Details) : Engine(), WorldChangeListener {
    private val log = LoggerFactory.getLogger(this::class.java)
    /**
     * Packets to redirect to social server
     * [Opcode, Size, Process after handshake]
     */
    private val socialDecoders = ArrayList<Triple<Int, Int, Boolean>>()

    private lateinit var network: Network
    var server: World? = null//TODO rename to world after kotlin fix

    override fun tick(time: Long, delta: Float) {
        val took = measureNanoTime {
//            println("Tick $delta")
            server?.setDelta(600f)
            server?.process()
        }
        if (took > 1000000L) {
            log.info("Took ${(took / 1000000)}ms")
        }
    }

    init {
        //List of social server packets
        val codec = WorldCodec(socialDecoders)

        val pipeline = SessionPipeline {
            it.addLast(SimplePacketDecoder(codec))
        }

        pipeline.apply {
            //Decode
            add(SimpleMessageDecoder(codec))
            //Handle
            add(SimpleMessageHandler(WorldMessages(worldDetails, loginSessions, gameCodec)))
            //Encode
            add(SimpleMessageEncoder(codec))
            //Connection
            add(WorldConnection(worldDetails))
        }
        //Checks for social server connection every 10 seconds
        AutoConnection(pipeline, NetworkConstants.LOCALHOST, NetworkConstants.BASE_PORT - 1)
    }

    override fun init(id: Int) {
        if (!isRunning) {
            //Single init
            isRunning = true
            PluginLoader.init()
            //Configure game world
            val builder = WorldConfigurationBuilder().with(EntityLinkManager())
            builder.with(EVENT_PROCESS_PRIORITY, eventSystem)
            //Load archetypes folder
            val pluginLoader = Loader(Settings.getString("pluginPath"))
            //Load plugins
            PluginLoader.setup(builder)

//            builder.register(BenchmarkStrategy())
            //Temp
            builder.dependsOn(MapPlugin::class.java)//TODO move to plugins/core
            builder.dependsOn(ClientPlugin::class.java)//TODO remove
            builder.with(PACKET_PROCESS_PRIORITY, gameMessages)
            //Build world config
            val config = builder.build()
            //Initialize world
            val server = World(config)
            this.server = server
            //
            PluginLoader.init(server, dispatcher)
            //Load entity archetypes
            EntityFactory.init(server)
            EntityFactory.load(pluginLoader)

            //Set delta
            server.setDelta(1F)

            //Handles messages
            val handshake = ClientHandshake(gameMessages)
            //Setup network pipeline
            val pipeline = SessionPipeline {
                //Decodes packets
                it.addLast("packet", ClientPacketDecoder(socialDecoders, gameCodec, handshake))
            }
            pipeline.apply {
                //Limit external connections
                add(ChannelFilter(Settings.getInt("connectionLimit")!!))
                //Redirects or decodes packets into messages
                add(ClientMessageDecoder(socialDecoders, gameCodec, handshake))
                //Handle
                add(handshake, "handler")
                //Encode
                add(SimpleMessageEncoder(gameCodec), "encoder")
            }

            //Initiate the server
            network = Network(name = "World $id", channel = pipeline)

            //Bind to port
            network.start(50000 + id)

            //Start your engines!
            start()
        }
    }

    companion object {
        private val loginSessions = HashMap<Int, ChannelHandlerContext>()
        val dispatcher = PollingEventDispatcher()
        val eventSystem = EventSystem(dispatcher, SubscribeAnnotationFinder())
        val gameMessages = ClientMessages(loginSessions)
        lateinit var server: GameServer
        var worldSession: Session? = null//TODO handle better
        private val gameCodec = ClientCodec()

        fun start(args: Array<String>) {
            val id = if (args.isNotEmpty()) args[0].toInt() else -1
            Settings.load()
            Xteas
            val worldDetails = WorldDetails(NetworkConstants.LOCALHOST, "UK", WorldDetails.Country.UK, 0, "Main World", WorldDetails.Setting.MEMBERS)
            server = GameServer(worldDetails)
            //Stand-alone boot
            if (id != -1) {
                worldDetails.id = id
                server.init(id)
            }
        }
    }
}