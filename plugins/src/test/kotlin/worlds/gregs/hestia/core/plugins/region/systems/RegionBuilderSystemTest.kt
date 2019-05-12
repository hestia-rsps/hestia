package worlds.gregs.hestia.core.plugins.region.systems

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.map.Chunk.getRotatedChunkPlane
import worlds.gregs.hestia.game.map.Chunk.getRotatedChunkRotation
import worlds.gregs.hestia.game.map.Chunk.getRotatedChunkX
import worlds.gregs.hestia.game.map.Chunk.getRotatedChunkY
import worlds.gregs.hestia.services.getSystem

internal class RegionBuilderSystemTest : GameTest(WorldConfigurationBuilder().with(RegionBuilderSystem())) {

    private lateinit var system: RegionBuilderSystem

    @BeforeEach
    override fun setUp() {
        super.setUp()
        system = world.getSystem(RegionBuilderSystem::class)
        system.reset()
    }

    @Test
    fun set() {
        system.set(0, 0, 0, 1, 0, 0)
        testSet(0, 0, 0, 1, 0, 0,0)
        system.reset()
        //Override
        system.set(0, 0, 0, 2, 0, 0)
        testSet(0, 0, 0, 2, 0, 0 ,0)
        assertThat(system.set.size).isEqualTo(1)
    }

    @Test
    fun setSize() {
        //Size 2x2
        system.set(0, 0, 0, 1, 0, 0, 2, 2, 0)
        testSet(0, 0, 0,1, 0, 0, 0)
        testSet(1, 0, 0,2, 0, 0, 0)
        testSet(0, 1, 0,1, 1, 0, 0)
        testSet(1, 1, 0,2, 1, 0, 0)
        system.reset()
        //Rotation 0
        //  1
        //  0
        system.set(0, 0, 0, 1, 0, 0, 1, 2, 0)
        testSet(0, 0, 0,1, 0, 0, 0)
        testSet(0, 1, 0,1, 1, 0, 0)
        system.reset()
        //Rotation 1
        //  0   1
        system.set(0, 0, 0, 1, 0, 0, 1, 2, 1)
        testSet(0, 0, 0,1, 0, 0, 1)
        testSet(1, 0, 0,1, 1, 0, 1)
        system.reset()
        //Rotation 2
        //  0
        //  1
        system.set(0, 0, 0, 1, 0, 0, 1, 2, 2)
        testSet(0, 1, 0,1, 0, 0, 2)
        testSet(0, 0, 0,1, 1, 0, 2)
        system.reset()
        //Rotation 3
        //  1   0
        system.set(0, 0, 0, 1, 0, 0, 1, 2, 3)
        testSet(1, 0, 0,1, 0, 0, 3)
        testSet(0, 0, 0,1, 1, 0, 3)
    }

    @Test
    fun setRegion() {
        val oldRegion = (0 shl 8) + 0
        val newRegion = (1 shl 8) + 0
        //Rotation 0
        system.set(oldRegion, newRegion, 0)
        testSet(0, 0, 0,8, 0, 0, 0)
        testSet(7, 0, 0,15, 0, 0, 0)
        system.reset()
        //Rotation 1
        system.set(oldRegion, newRegion, 1)
        testSet(0, 0, 0,15, 0, 0, 1)
        testSet(7, 0, 0,15, 7, 0, 1)
        system.reset()
        //Rotation 2
        system.set(oldRegion, newRegion, 2)
        testSet(0, 0, 0,15, 7, 0, 2)
        testSet(7, 0, 0,8, 7, 0, 2)
        system.reset()
        //Rotation 3
        system.set(oldRegion, newRegion, 3)
        testSet(0, 0, 0,8, 7, 0, 3)
        testSet(7, 0, 0,8, 0, 0, 3)
        system.reset()

    }

    private fun testSet(chunkX: Int, chunkY: Int, plane: Int, targetX: Int, targetY: Int, targetPlane: Int, rotation: Int) {
        val key = chunkY + (chunkX shl 14) + (plane shl 28)
        assertThat(system.set.containsKey(key)).isTrue()
        val value = rotation shl 1 or (targetPlane shl 24) or (targetX shl 14) or (targetY shl 3)
        assertThat(system.set[key]).describedAs("RegionX: %s RegionY: %s RegionPlane: %s Rotation: %s", getRotatedChunkX(system.set[key]!!), getRotatedChunkY(system.set[key]!!), getRotatedChunkPlane(system.set[key]!!), getRotatedChunkRotation(system.set[key]!!)).isEqualTo(value)
    }

    @Test
    fun clear() {
        system.clear(0, 0, 0)
        testClear(0, 0, 0)
        //Override
        system.clear(0, 0, 0)
        assertThat(system.clear.size).isEqualTo(1)
    }

    @Test
    fun clearSize() {
        system.clear(0, 0, 0, 2, 2)
        testClear(0, 0, 0)
        testClear(1, 0, 0)
        testClear(0, 1, 0)
        testClear(1, 1, 0)
        system.reset()
    }

    @Test
    fun clearRegion() {
        val oldRegion = (0 shl 8) + 0
        system.clear(oldRegion)
        testClear(0, 0, 0)
        testClear(7, 7, 0)
        system.reset()
    }

    private fun testClear(chunkX: Int, chunkY: Int, plane: Int) {
        val key = chunkY + (chunkX shl 14) + (plane shl 28)
        assertThat(system.clear.contains(key)).isTrue()
    }

    @Test
    fun reset() {
        system.reset(0, 0, 0)
        testReset(0, 0, 0)
    }

    @Test
    fun resetSize() {
        system.reset(0, 0, 0, 2, 2)
        testReset(0, 0, 0)
        testReset(1, 1, 0)
    }

    @Test
    fun resetRegion() {
        val oldRegion = (0 shl 8) + 0
        system.reset(oldRegion)
        testReset(0, 0, 0)
        testReset(7, 7, 0)
    }

    private fun testReset(chunkX: Int, chunkY: Int, plane: Int) {
        testSet(chunkX, chunkY, plane, chunkX, chunkY, plane, 0)
    }
}