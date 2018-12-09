package worlds.gregs.hestia.game

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.BeforeEach
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.plugins.movement.systems.calc.MovementTester

abstract class GameTest(private val config: WorldConfigurationBuilder) {

    private lateinit var clipping: MovementTester.ClippingBuilderTester
    lateinit var world: World
    lateinit var es: EventSystem

    @BeforeEach
    open fun setUp() {
        es = EventSystem()
        config.with(es)
        val config = config.build()
        world = World(config)
        EntityFactory.init(world)
    }

    open fun tick() {
        world.process()
    }

}