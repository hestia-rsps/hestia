package worlds.gregs.hestia.game

import com.artemis.BaseSystem
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SystemTest {
    lateinit var w: World

    private class System1 : BaseSystem() {
        override fun processSystem() {
            println("Process 1")
        }
    }

    private class System2 : BaseSystem() {
        override fun processSystem() {
            println("Process 2")
        }
    }

    private class System3 : BaseSystem() {
        override fun processSystem() {
            println("Process 3")
        }
    }

    @BeforeEach
    fun setUp() {
        val config = WorldConfigurationBuilder().with(SystemTest.System1(), SystemTest.System2()).build()
        w = World(config)

    }

    @Test
    fun test() {
        w.process()
    }
}