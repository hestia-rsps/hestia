package worlds.gregs.hestia.game

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import net.mostlyoriginal.api.event.common.EventSystem
import org.junit.jupiter.api.BeforeEach
import worlds.gregs.hestia.game.archetypes.EntityFactory

abstract class GameTest(private val config: WorldConfigurationBuilder) {

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

    fun tick() {
        world.process()
    }

}