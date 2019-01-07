package worlds.gregs.hestia

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.link.EntityLinkManager
import net.mostlyoriginal.api.event.common.EventSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.client.AutoConnection
import world.gregs.hestia.core.network.codec.Encoder
import world.gregs.hestia.core.network.codec.Pipeline
import world.gregs.hestia.core.network.codec.inbound.packet.PacketHandler
import world.gregs.hestia.core.network.login.LoginHandshake
import world.gregs.hestia.core.network.login.LoginHandshakeDecoder
import world.gregs.hestia.core.network.server.Network
import world.gregs.hestia.core.services.Cache
import world.gregs.hestia.core.services.load.PacketLoader
import world.gregs.hestia.core.world.WorldDetails
import worlds.gregs.hestia.game.Engine
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.plugin.PluginLoader
import worlds.gregs.hestia.network.LoginAttempt
import worlds.gregs.hestia.network.WorldChangeListener
import worlds.gregs.hestia.network.game.Filter
import worlds.gregs.hestia.network.game.GameClientConnection
import worlds.gregs.hestia.network.game.GamePacketDecoder
import worlds.gregs.hestia.network.game.GamePacketHandler
import worlds.gregs.hestia.network.game.`in`.GameLogin
import worlds.gregs.hestia.network.social.SocialConnection
import worlds.gregs.hestia.network.social.SocialPacketDecoder
import kotlin.system.measureNanoTime

class GameServer : Engine(), WorldChangeListener {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val loader = PacketLoader(Settings.getString("sourcePath"))

    private lateinit var network: Network
    var server: World? = null//TODO rename to world after kotlin 1.3.30 fix

    override fun tick(time: Long, delta: Float) {
        val took = measureNanoTime {
            server?.process()
        }
        if (took > 1000000L) {
            log.info("Took ${(took / 1000000)}ms")
        }
    }

    init {
        //List of social server packets
        val socialPackets = loader.load("worlds.gregs.hestia.network.social.in")
        SocialPacketDecoder.packets = socialPackets

        val pipeline = Pipeline()
        pipeline.apply {
            //Decodes incoming social server packets
            add(SocialPacketDecoder::class)
            //Processes social server packets
            add(PacketHandler(socialPackets))
            //Handles dis/connecting to social server
            add(SocialConnection())
            //Encodes outgoing packets
            add(Encoder())
        }
        //Checks for social server connection every 10 seconds
        AutoConnection(pipeline, NetworkConstants.LOCALHOST, NetworkConstants.BASE_PORT - 1)
    }

    override fun init(id: Int) {
        if (!isRunning) {
            //Single init
            isRunning = true
            //Configure game world
            val builder = WorldConfigurationBuilder().with(eventSystem, EntityLinkManager())
            //Load plugin folder
            val pluginLoader = PacketLoader(Settings.getString("pluginPath"))
            //Load plugins
            PluginLoader().setup(builder, pluginLoader)

            //Build world config
            val config = builder.build()
            //Initialize world
            val server = World(config)
            this.server = server
            //Load entity archetypes
            EntityFactory.init(server)
            EntityFactory.load(pluginLoader)

            //Set delta
            server.setDelta(1F)

            //Setup network pipeline
            val pipeline = Pipeline()
            pipeline.apply {
                //Filters multi-login limits
                add(Filter())
                //Decodes login packet
                add(LoginHandshakeDecoder::class)
                //After game login, pipeline switches with game decoders
                add(LoginHandshake(GameLogin(), LoginAttempt(server, GamePacketDecoder::class, GamePacketHandler(), GameClientConnection())))
                //Outgoing packet encoder
                add(Encoder())
            }

            //Initiate the server
            network = Network(name = "World $id", channel = pipeline)

            //Bind to port
            network.start(NetworkConstants.BASE_PORT + 1 + id)

            //Start your engines!
            start()
        }
    }

    companion object {
        val eventSystem = EventSystem()
        lateinit var worldDetails: WorldDetails
        var worldSession: Session? = null
        lateinit var server: GameServer
        val socialPackets = HashMap<Int, Int>()
        val loginSessions = HashMap<Int, Session>()

        fun start(args: Array<String>) {
            val id = if (args.isNotEmpty()) args[0].toInt() else -1
            Settings.load()
            Cache.init()
            worldDetails = WorldDetails("Main World", NetworkConstants.LOCALHOST, "UK", WorldDetails.Country.UK, WorldDetails.Setting.MEMBERS)
            server = GameServer()
            //Stand-alone boot
            if (id != -1) {
                worldDetails.id = id
                server.init(id)
            }
        }
    }
}