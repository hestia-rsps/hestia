package worlds.gregs.hestia.artemis

import com.artemis.utils.IntBag
import net.mostlyoriginal.api.utils.QuadTree
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.math.abs
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
                if(withinRange(x, y, center, center, radi)) {
                    println("$x, $y")
                }
            }
        }
    }

    private fun withinRange(x: Int, y: Int, x2: Int, y2: Int, radius: Int): Boolean {
        return abs(x - x2) <= radius && abs(y - y2) <= radius
    }


}