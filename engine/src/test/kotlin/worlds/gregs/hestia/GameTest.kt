package worlds.gregs.hestia

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.BeforeEach

abstract class GameTest(private val config: WorldConfigurationBuilder) {

    lateinit var world: World
    lateinit var es: EventSystem

    @BeforeEach
    open fun setup() {
        es = EventSystem()
        config.with(es)
        config(config)
        val config = config.build()
        world = World(config)
        start()
    }

    open fun start() {
    }

    open fun config(config: WorldConfigurationBuilder) {
    }

    open fun tick() {
        world.process()
    }

}