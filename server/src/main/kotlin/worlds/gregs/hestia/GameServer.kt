package worlds.gregs.hestia

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.link.EntityLinkManager
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.WorldDetails
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.client.AutoConnection
import world.gregs.hestia.core.network.codec.Encoder
import world.gregs.hestia.core.network.codec.Pipeline
import world.gregs.hestia.core.network.login.LoginHandshake
import world.gregs.hestia.core.network.server.Network
import world.gregs.hestia.core.services.Cache
import world.gregs.hestia.core.services.load.PacketLoader
import worlds.gregs.hestia.game.Engine
import worlds.gregs.hestia.game.plugin.PluginLoader
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.network.GamePacketInboundHandler
import worlds.gregs.hestia.network.LoginAttempt
import worlds.gregs.hestia.network.LoginServerInboundHandler
import worlds.gregs.hestia.network.WorldChangeListener
import worlds.gregs.hestia.network.login.Filter

class GameServer(info: WorldDetails) : Engine(), WorldChangeListener {
    private val loader = PacketLoader(Settings.getString("sourcePath"))

    private lateinit var network: Network
    private lateinit var server: World

    override fun tick(time: Long, delta: Float) {
        server.setDelta(delta)
        server.process()
    }

    init {
        //List of world packets
        val worldPackets = loader.load("worlds.gregs.hestia.network.world.in")
        //Handles communication between game server & login server
        val loginHandler = LoginServerInboundHandler(info, this, worldPackets)

        //Checks for login server connection every 10 seconds
        AutoConnection(Pipeline(loginHandler, Encoder()), NetworkConstants.LOCALHOST, NetworkConstants.BASE_PORT - 1)
    }

    override fun disconnect(id: Int) {
        server.delete(id)
    }

    override fun init(id: Int) {
        if (!isRunning) {
            //Configure game world
            val builder = WorldConfigurationBuilder().with(EventSystem(), EntityLinkManager())
            //Load plugin folder
            val pluginLoader = PacketLoader(Settings.getString("pluginPath"))
            //Load plugins
            PluginLoader().setup(builder, pluginLoader)

            //Build world config
            val config = builder.build()
            //Register non-artemis wires
//            config.register()

            //Initialize world
            server = World(config)
            //Load entity archetypes
            EntityFactory.init(server)
            EntityFactory.load(pluginLoader)

            //List of game packets
            val gamePackets = pluginLoader.load("worlds.gregs.hestia.network.in")
            //Handles client -> server communications
            val gameHandler = GamePacketInboundHandler(server, gamePackets)

            //List of login packets
            val loginPackets = loader.load("worlds.gregs.hestia.network.login.in")
            //Handles login attempts/requests
            val loginHandler = LoginHandshake(loginPackets, LoginAttempt(server, gameHandler))

            //Initiate the server
            network = Network(channel = Pipeline(Filter(), loginHandler, Encoder()))

            //Bind to port
            network.start(NetworkConstants.BASE_PORT + 1 + id)

            //Start your engines!
            start()
        }
    }

    companion object {
        fun start(args: Array<String>) {
            val id = if (args.isNotEmpty()) args[0].toInt() else 1
            Settings.load()
            Cache.init()
            val info = WorldDetails("Main World", NetworkConstants.LOCALHOST, "UK", WorldDetails.Country.UK, WorldDetails.Setting.MEMBERS)
            val server = GameServer(info)

            //Stand-alone boot
            if (id != -1) {
                info.id = id
                server.init(id)
            }
        }
    }
}