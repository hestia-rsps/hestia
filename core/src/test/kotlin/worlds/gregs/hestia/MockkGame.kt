package worlds.gregs.hestia

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import io.mockk.spyk
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.BeforeEach

abstract class MockkGame(private val config: WorldConfigurationBuilder = WorldConfigurationBuilder()) {

    lateinit var actualWorld: World
    lateinit var world: World
    lateinit var es: EventSystem

    @BeforeEach
    open fun setup() {
        es = spyk(EventSystem())
        config.with(es)
        config(config)
        val config = config.build()
        actualWorld = World(config)
        world = spyk(actualWorld)
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