package worlds.gregs.hestia.game.plugin

import com.artemis.WorldConfigurationBuilder
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.services.load.Loader
import world.gregs.hestia.core.services.plural
import kotlin.system.measureNanoTime

class PluginLoader {

    private val logger = LoggerFactory.getLogger(PluginLoader::class.java)

    fun setup(b: WorldConfigurationBuilder, loader: Loader) {
        var count = 0
        var total = 0
        val time = measureNanoTime {
            val plugins = loader.loadJavaClasses("worlds.gregs.hestia.game.plugins", Plugin::class.java)
            plugins.forEach {
                if(!it.isAnnotationPresent(DisablePlugin::class.java)) {
                    b.dependsOn(it)
                    count++
                }
            }
            total = plugins.size
        }
        val inactive = total - count
        logger.debug("$count ${"plugin".plural(count)} loaded in ${time / 1000000}ms ${ if(inactive > 0) "$inactive disabled" else "" }")
        b.dependsOn()
    }
}

/**
 * Annotation for deactivating plugins
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class DisablePlugin