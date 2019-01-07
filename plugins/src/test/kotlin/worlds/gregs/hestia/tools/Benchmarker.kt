package worlds.gregs.hestia.tools

import org.junit.jupiter.api.Test
import kotlin.system.measureNanoTime

internal class Benchmarker {

    @Test
    fun benchmark() {
        benchmark(1000000, {
        }, {
        })
    }

    fun benchmark(times: Int, first: ()-> Unit, second: () -> Unit) {
        var t1 = 0L
        var t2 = 0L

        //Warm up
        for(i in 0 until times * 10) {
            first()
            second()
        }

        for(i in 0 until times) {
            if(i % 2 == 0) {
                t2 += measureNanoTime {
                    second()
                }
                t1 += measureNanoTime {
                    first()
                }
            } else {
                t1 += measureNanoTime {
                    first()
                }
                t2 += measureNanoTime {
                    second()
                }
            }
        }
        println("Average ${t1/times}ns ${t2/times}ns Dif ${(t2/times) - (t1/times)}ns")
    }
}