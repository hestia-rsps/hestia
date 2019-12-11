package worlds.gregs.hestia.game

import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.BeforeEach
import java.util.*

abstract class GameTest {

    lateinit var world: World
    private val configInit = LinkedList<(config: WorldConfigurationBuilder) -> Unit>()
    private val worldInit = LinkedList<(world: World) -> Unit>()

    abstract fun config(): WorldConfigurationBuilder

    @BeforeEach
    open fun setup() {
        worldInit.clear()
        configInit.clear()
    }

    fun start() {
        val config = config()
        configInit.forEach {
            it.invoke(config)
        }
        world = World(config.build())
        worldInit.forEach {
            it.invoke(world)
        }
    }

    protected fun config(action: (config: WorldConfigurationBuilder) -> Unit) {
        configInit.add(action)
    }

    protected fun world(action: (world: World) -> Unit) {
        worldInit.add(action)
    }

    open fun tick() {
        world.process()
    }

}