package worlds.gregs.hestia.tools

import com.artemis.utils.BitVector
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.bag.BitBag
import kotlin.system.measureNanoTime

internal class Benchmarker {

    @Test
    fun benchmark() {
        val points = ArrayList<Pair<Int, Int>>()
        //When
        for (x in 0 until 255) {
            for (y in 0 until 255) {
                points.add(Pair(x, y))
            }
        }

        val ids = points.mapIndexed { index, _ -> index }
        val x = 1
        val y = 1
        val width = 1
        val height = 1
        val radius = 1


        /*
        TODO benchmark position map spiral vs spiral sort
         */

        val first = BitVector()
        first.set(1000)
        first.set(110000)
        val second = BitBag()
        second.add(1000)
        second.add(110000)
        benchmark(1000000, {
            first.get(100)
        }, {
            second.contains(100)
        })
    }

    fun benchmark(times: Int, first: () -> Unit, second: () -> Unit) {
        var t1 = 0L
        var max1 = 0L
        var max2 = 0L
        var min1 = 0L
        var min2 = 0L
        var t2 = 0L

        //Warm up
        for (i in 0 until times * 10) {
            first()
            second()
        }
        var t: Long
        for (i in 0 until times) {
            if (i % 2 == 0) {
                t = measureNanoTime {
                    second()
                }
                t2 += t
                max2 = Math.max(max2, t)
                min2 = Math.min(min2, t)
                t = measureNanoTime {
                    first()
                }
                t1 += t
                max1 = Math.max(max1, t)
                min1 = Math.min(min1, t)
            } else {
                t = measureNanoTime {
                    first()
                }
                t1 += t
                max1 = Math.max(max1, t)
                min1 = Math.min(min1, t)
                t = measureNanoTime {
                    second()
                }
                t2 += t
                max2 = Math.max(max2, t)
                min2 = Math.min(min2, t)
            }
        }
        println("Average ${t1 / times}ns ${t2 / times}ns Dif ${(t2 / times) - (t1 / times)}ns")
        println("Min ${min1}ns ${min2}ns Dif ${min1 - min2}ns")
        println("Max ${max1}ns ${max2}ns Dif ${max1 - max2}ns")
    }
}