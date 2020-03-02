package worlds.gregs.hestia.game.plugin

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.artemis.event.ExtendedFastEventDispatcher
import kotlin.system.measureNanoTime

object PluginLoader : Plug {

    private val plugins = mutableListOf<PluginBase>()
    private val logger = LoggerFactory.getLogger(PluginLoader::class.java)!!
    private var loadTime = -1L

    fun init() {
        val list = Settings.get<List<String>>("pluginRootPackage", emptyList())
        loadTime = 0L
        measure {
            list.forEach {
                load(it)
            }
        }
    }

    fun load(path: String) {
        val script = Class.forName(path)
        plugins.add(script.getConstructor().newInstance() as PluginBase)
    }

    override fun setup(b: WorldConfigurationBuilder) {
        measure {
            plugins.forEach {
                it.setup(b)
            }
        }
    }

    override fun init(world: World, dispatcher: ExtendedFastEventDispatcher) {
        measure {
            plugins.forEach {
                it.init(world, dispatcher)
            }
        }

        val count = world.systems.size() - 5//Ignoring Artemis' own systems
        logger.debug("$count ${"system".plural(count)} loaded in ${loadTime / 1000000}ms")
    }

    private fun measure(action: () -> Unit) {
        loadTime += measureNanoTime(action)
    }
}