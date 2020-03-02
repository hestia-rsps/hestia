package worlds.gregs.hestia.game.plugin

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import io.github.classgraph.ClassGraph
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.artemis.event.ExtendedEventListener
import worlds.gregs.hestia.artemis.event.ExtendedFastEventDispatcher
import worlds.gregs.hestia.game
import worlds.gregs.hestia.game.plugin.ScriptLoader.initialisers
import kotlin.system.measureTimeMillis

object ScriptLoader : Plug {
    val listeners = mutableListOf<ExtendedEventListener>()
    val initialisers = mutableListOf<() -> Unit>()
    private val logger = LoggerFactory.getLogger(ScriptLoader::class.java)!!

    override fun setup(b: WorldConfigurationBuilder) {
    }

    override fun init(world: World, dispatcher: ExtendedFastEventDispatcher) {
        var scripts = 0
        val time = measureTimeMillis {
            val arguments = emptyArray<String>()
            ClassGraph()
                    .enableClassInfo()
                    .filterClasspathElements { it.contains("content") && !it.contains("tmp") }
                    .scan().use { scanResult ->
                        for (script in scanResult.allClasses.filter { it.extendsSuperclass("kotlin.script.templates.standard.ScriptTemplateWithArgs") }) {
                            val klass = script.loadClass()
                            val obj = klass.constructors.first().newInstance(arguments)
                            game.injector.inject(obj)
                            scripts++
                        }
                    }
            initialisers.forEach { init ->
                init.invoke()
            }
            listeners.forEach {
                dispatcher.register(it)
            }
        }
        logger.debug("$scripts ${"script".plural(scripts)} and ${listeners.size} ${"listener".plural(listeners.size)} loaded in ${time}ms")
        listeners.clear()
    }
}

fun init(function: () -> Unit) = initialisers.add(function)