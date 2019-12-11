package worlds.gregs.hestia.scripts

import com.artemis.Aspect
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import net.mostlyoriginal.api.event.common.EventSystem
import net.mostlyoriginal.api.event.common.SubscribeAnnotationFinder
import org.junit.jupiter.api.BeforeEach
import worlds.gregs.hestia.artemis.event.PollingEventDispatcher
import worlds.gregs.hestia.core.scripts.ScriptBuilder

abstract class ScriptTesterWorld<T : ScriptBuilder>(private val aspect: Aspect.Builder? = null) : ScriptLoader {

    protected lateinit var script: T
    protected lateinit var world: World
    protected lateinit var eventSystem: EventSystem

    open fun build(builder: WorldConfigurationBuilder) {
        script.build(builder)
    }

    open fun initiate(dispatcher: PollingEventDispatcher) {
        script.build(world, dispatcher)
    }


    @BeforeEach
    fun setup() {
        script = if (aspect != null) {
            loadScriptClass().getConstructor(Aspect.Builder::class.java).newInstance(aspect) as T
        } else {
            loadScript()!!
        }

        val dispatcher = PollingEventDispatcher()
        eventSystem = EventSystem(dispatcher, SubscribeAnnotationFinder())

        val builder = WorldConfigurationBuilder()
        builder.with(eventSystem)
        build(builder)

        val config = builder.build()
        world = World(config)

        initiate(dispatcher)
    }

}