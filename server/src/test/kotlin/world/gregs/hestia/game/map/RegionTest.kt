package world.gregs.hestia.game.map

import com.artemis.World
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.services.load.Loader
import world.gregs.hestia.game.archetypes.EntityFactory
import kotlin.system.measureNanoTime

class RegionTest {
    lateinit var w: World
    @BeforeEach
    fun setUp() {
        w = World()
        EntityFactory.init(w)
        EntityFactory.load(Loader("./plugins/src/main/kotlin/"))
    }

    @Test
    fun memory() {
        val time = measureNanoTime {
            for(x in 0..150) {
                for(y in 0..150) {
//                    RegionFactory.create()
                }
            }
        }
        println("${150 * 150} regions creaed in ${time/1000000}ms")
        val runtime = Runtime.getRuntime()
        val mb = 1024 * 1024
        println("Heap utilization statistics [MB]")
        println("Used Memory: ${(runtime.totalMemory() - runtime.freeMemory()) / mb}")
        println("Free Memory: ${runtime.freeMemory() / mb}")
        println("Total Memory: ${runtime.totalMemory() / mb}")
        println("Max Memory: ${runtime.maxMemory() / mb}")
    }
}