package world.gregs.hestia.game.plugin

import com.artemis.WorldConfigurationBuilder
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.services.load.Loader
import world.gregs.hestia.core.services.plural
import kotlin.system.measureNanoTime

class PluginLoader {

    private val logger = LoggerFactory.getLogger(PluginLoader::class.java)

    fun setup(b: WorldConfigurationBuilder, loader: Loader) {
        var count = 0
        val time = measureNanoTime {
            val plugins = loader.loadJavaClasses("world.gregs.hestia.game.plugins", Plugin::class.java)
            plugins.forEach {
                b.dependsOn(it)
            }
            count = plugins.size
        }
        logger.debug("$count ${"plugin".plural(count)} loaded in ${time / 1000000}ms")
        b.dependsOn()
    }
}