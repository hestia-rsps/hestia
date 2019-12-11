package worlds.gregs.hestia.game

import com.artemis.utils.IntBag
import net.mostlyoriginal.api.utils.QuadTree
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.services.forEach
import java.util.*
import kotlin.system.measureNanoTime


class QuadTreeTest {

    val qt = QuadTree(0f, 0f, 50000f, 50000f)

    @Test
    fun `QuadTree test`() {
        val rand = Random()
        println("Insert took ${measureNanoTime {
            repeat(50000) {
                qt.insert(it, rand.nextInt(16000).toFloat(), rand.nextInt(16000).toFloat(), 1f, 1f)
            }
        }}")
        val results = IntBag()
        println("Query took ${measureNanoTime {
            qt.getExact(results, 8f, 8f, 20f, 20f)
        }}")
        println("Query took ${measureNanoTime {
            qt.get(results, 8f, 8f, 20f, 20f)
        }}")

        results.forEach {
            println("Found $it")
        }
    }

    @Test
    fun `QuadTree test size`() {
        qt.insert(1, 1f, 2f, 1f, 1f)
        qt.insert(2, 2f, 1f, 1f, 1f)
        qt.insert(3, 1f, 1f, 1f, 1f)
        qt.insert(4, 2f, 2f, 1f, 1f)

        val results = IntBag()
        println("Query took ${measureNanoTime {
            qt.getExact(results, 1f, 1f, 1f, 1f)
        }}")

        results.forEach {
            println("Found $it")
        }
    }

    @Test
    fun `Circle radius`() {
        val center = 16
        val radi = 15
        for(x in center - radi..center + radi) {
            for(y in center - radi..center + radi) {
                if(Position.withinRange(x, y, center, center, radi)) {
                    println("$x, $y")
                }
            }
        }
    }


}