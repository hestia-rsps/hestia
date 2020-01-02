package worlds.gregs.hestia.core.world.map.api.container

import com.artemis.utils.IntBag
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.map.logic.Spiral
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.forEach
import worlds.gregs.hestia.artemis.toArray
import java.util.function.BiPredicate

internal class EntityMapTest {
    lateinit var map: EntityMap

    @BeforeEach
    fun setup() {
        map = object : EntityMap(Aspect.all()) {
            override fun update(entityId: Int, shift: Shift) {}
        }
    }

    @Test
    fun `Single point entity`() {
        //When
        map.insert(1, 100, 100, 2)
        //Then
        assertTile(100, 100, 1)
        assertTile(100, 100, 2, 1)
    }

    @Test
    fun `Single point wide entity`() {
        //When
        map.insert(1, 100, 100, 2, 2, 1)
        //Then
        assertTile(100, 100, 1)
        assertTile(101, 100, 1)
        assertTile(100, 100, 2, 1)
        assertTile(101, 100, 2, 1)
    }

    @Test
    fun `In exact area`() {
        //When
        map.insert(1, 100, 100, 2, 1, 1)
        //Then
        assertArea(98, 98, 2, 4, 4, 1)
    }

    @Test
    fun `On opposite of exact area border`() {
        //When
        map.insert(1, 3, 3, 2, 1, 1)
        map.insert(2, 4, 4, 2, 1, 1)
        //Then
        assertArea(1, 1, 2, 3, 3, 1)
    }

    @Test
    fun `On exact area border`() {
        //When
        map.insert(1, 1, 1, 2, 1, 1)
        //Then
        assertArea(1, 1, 2, 2, 2, 1)
        assertArea(2, 2, 2, 2, 2)
    }

    @Test
    fun `Radius area`() {
        //When
        map.insert(1, 3, 3, 2, 1, 1)
        //Then
        assertRadius(1, 1, 2, 1, 1, 1)
        assertRadius(1, 1, 2, 1, 1, 2, 1)
    }

    @Test
    fun `Test order by distance`() {
        val points = ArrayList<Pair<Int, Int>>()
        //When
        var i = 0
        for(x in 0 until 3) {
            for(y in 0 until 3) {
                points.add(Pair(x, y))
                println("${i++} $x $y")
            }
        }

        points.forEachIndexed { id, pos ->
            map.insert(id, pos.first, pos.second, 0, 1, 1)
        }

        println()

        val x = 1
        val y = 1
        val width = 1
        val height = 1
        val ids = map.get(IntBag(), 5, 5, 0, 1, 1, 5)
        val distances = HashMap<Int, Double>()
        ids.forEach {
            val position = points[it]
            val cx = Math.max(Math.min(position.first, x + width), x).toDouble()
            val cy = Math.max(Math.min(position.second, y + height), y).toDouble()
            distances[it] = Math.sqrt((position.first - cx) * (position.first - cx) + (position.second - cy) * (position.second - cy))
        }
        val fill = IntBag()
        val sorted = distances.toList().sortedBy { (_, value) -> value }.toMap()
        sorted.forEach { (id, dist) ->
            println("${points[id].first} ${points[id].second} $dist")
            fill.add(id)
        }
        //Then
        println(fill.toArray().toList())
    }

    @Test
    fun `Test order by spiral`() {
        val points = ArrayList<Pair<Int, Int>>()
        //When
        for(x in 0 until 3) {
            for(y in 0 until 3) {
                points.add(Pair(x, y))
            }
        }

        points.forEachIndexed { id, pos ->
            map.insert(id, pos.first, pos.second, 0, 1, 1)
        }

        println()

        val x = 1
        val y = 1
        val width = 1
        val height = 1
        val radius = 1
        val ids = map.get(IntBag(), x, y, 0, width, height, radius)

        val spiral = HashMap<Int, Int>()
        var i = 0
        Spiral.outwards(x, y, width, height, radius, BiPredicate { x, y ->
            spiral[Position.hash24Bit(x, y)] = i++
            true
        })

        val distances = HashMap<Int, Int>()
        ids.forEach {
            val position = points[it]
            distances[it] = spiral[Position.hash24Bit(position.first, position.second)] ?: return@forEach
        }
        val fill = IntBag()
        val sorted = distances.toList().sortedBy { (_, value) -> value }.toMap()
        sorted.forEach { (id, dist) ->
            println("${points[id].first} ${points[id].second} $dist")
            fill.add(id)
        }
        //Then
        println(fill.toArray().toList())
    }

    private fun assertTile(x: Int, y: Int, plane: Int, vararg expected: Int) {
        if(expected.isEmpty()) {
            assertThat(map.getExact(IntBag(), x, y, plane).toArray()).isEmpty()
        } else {
            assertThat(map.getExact(IntBag(), x, y, plane).toArray()).containsExactly(*expected)
        }
    }

    private fun assertArea(x: Int, y: Int, plane: Int, width: Int, height: Int, vararg expected: Int) {
        if(expected.isEmpty()) {
            assertThat(map.getExact(IntBag(), x, y, plane, width, height).toArray()).isEmpty()
        } else {
            assertThat(map.getExact(IntBag(), x, y, plane, width, height).toArray()).containsExactly(*expected)
        }
    }

    private fun assertRadius(x: Int, y: Int, plane: Int, width: Int, height: Int, radius: Int, vararg expected: Int) {
        if(expected.isEmpty()) {
            assertThat(map.getExact(IntBag(), x, y, plane, width, height, radius).toArray()).isEmpty()
        } else {
            assertThat(map.getExact(IntBag(), x, y, plane, width, height, radius).toArray()).containsExactly(*expected)
        }
    }
}