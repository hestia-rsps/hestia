package worlds.gregs.hestia.game.component.map

import org.junit.jupiter.api.Test

import worlds.gregs.hestia.game.plugins.core.components.map.Position
import kotlin.system.measureNanoTime

internal class ViewportTest {

    @Test
    fun update() {
        val map = mapOf(Pair("three", 3), Pair("two", 2), Pair("one", 1), Pair("two_two", 2))
        val sorted = map.toList().sortedBy { (_, value) -> value }.toMap()
        println(sorted.keys) // [one, two, three]
        println(sorted.values) // [1, 2, 3]
    }

    @Test
    fun performance() {
        val position = Position.create(100, 100, 0)
        val second = Position.EMPTY

        println(measureNanoTime {
            for(i in 0 until 100000) {
                position.getDistance(second)
            }
        })
    }
}