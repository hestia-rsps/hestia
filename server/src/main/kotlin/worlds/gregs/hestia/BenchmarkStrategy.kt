package worlds.gregs.hestia

import com.artemis.SystemInvocationStrategy
import kotlin.system.measureNanoTime

class BenchmarkStrategy : SystemInvocationStrategy() {

    /** Processes all systems in order.  */
    override fun process() {
        val systemsData = systems.data
        var i = 0
        val s = systems.size()
        while (s > i) {
            if (disabled.get(i)) {
                i++
                continue
            }

            val states = measureNanoTime {
                updateEntityStates()
            }
            val system = measureNanoTime {
                systemsData[i].process()
            }
            if(states + system > 1000000) {
                println("Processed ${systemsData[i]::class.java.simpleName} in ${system / 1000000}ms states ${states / 1000000}ms")
            }
            i++
        }

        updateEntityStates()
    }

}
