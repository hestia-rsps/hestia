package worlds.gregs.hestia.game.systems

import com.artemis.World
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.services.Loader
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.archetypes.EntityFactory
import kotlin.system.measureNanoTime

class ChunkTest {
    lateinit var w: World
    @BeforeEach
    fun setUp() {
        w = World()
        EntityFactory.init(w)
        EntityFactory.load(Loader("./src/main/kotlin/"))
    }

    @Test
    fun memory() {
        val time = measureNanoTime {
            for (x in 0..150) {
                for (y in 0..150) {
//                    RegionFactory.create()
                }
            }
        }
        println("${150 * 150} regions created in ${time / 1000000}ms")
        val runtime = Runtime.getRuntime()
        val mb = 1024 * 1024
        println("Heap utilization statistics [MB]")
        println("Used Memory: ${(runtime.totalMemory() - runtime.freeMemory()) / mb}")
        println("Free Memory: ${runtime.freeMemory() / mb}")
        println("Total Memory: ${runtime.totalMemory() / mb}")
        println("Max Memory: ${runtime.maxMemory() / mb}")
    }

    @Test
    fun getChunks() {
        val position = Position.create(3079, 3488, 0)
//        ChunkSystem().getChunks(position)
    }

    @Test
    fun hashTest() {
        println(10000 shr 3)
        val regionX = 1000
        val regionY = 239
        val plane = 1
        val hash = regionY + (regionX shl 14) + (plane shl 28)

        val x = hash shr 14 and 0x3fff
        val y = hash and 0x3fff
        val z = hash shr 28

        println("$x, $y, $z")

    }
}