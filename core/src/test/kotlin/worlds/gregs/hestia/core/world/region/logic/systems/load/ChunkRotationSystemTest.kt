package worlds.gregs.hestia.core.world.region.logic.systems.load

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.service.getSystem

internal class ChunkRotationSystemTest : GameTest(WorldConfigurationBuilder().with(ChunkRotationSystem())) {

    @Test
    fun translateX() {
        val system = world.getSystem(ChunkRotationSystem::class)
        assertThat(system.translateX(1, 0, 0)).isEqualTo(1)
        assertThat(system.translateX(1, 0, 1)).isEqualTo(0)
        assertThat(system.translateX(1, 0, 2)).isEqualTo(6)
        assertThat(system.translateX(1, 0, 3)).isEqualTo(7)
    }

    @Test
    fun translateY() {
        val system = world.getSystem(ChunkRotationSystem::class)
        assertThat(system.translateY(0, 1, 0)).isEqualTo(1)
        assertThat(system.translateY(0, 1, 1)).isEqualTo(7)
        assertThat(system.translateY(0, 1, 2)).isEqualTo(6)
        assertThat(system.translateY(0, 1, 3)).isEqualTo(0)
    }

    @Test
    fun translateXSize() {
        val system = world.getSystem(ChunkRotationSystem::class)
        (0..3).forEach { rotation ->
            (0..3).forEach { objRotation ->
                (0..2).forEach { sizeX ->
                    (0..2).forEach { sizeY ->
                        assertThat(system.translateX(1, 1, rotation, sizeX.toShort(), sizeY.toShort(), objRotation)).isEqualTo(translate(1, 1, rotation, sizeX, sizeY, objRotation)[0])
                    }
                }
            }
        }
    }

    @Test
    fun translateYSize() {
        val system = world.getSystem(ChunkRotationSystem::class)
        (0..3).forEach { rotation ->
            (0..3).forEach { objRotation ->
                (0..2).forEach { sizeX ->
                    (0..2).forEach { sizeY ->
                        assertThat(system.translateY(1, 1, rotation, sizeX.toShort(), sizeY.toShort(), objRotation)).isEqualTo(translate(1, 1, rotation, sizeX, sizeY, objRotation)[1])
                    }
                }
            }
        }
    }

    private fun translate(x: Int, y: Int, mapRotation: Int, sizeX: Int, sizeY: Int, objectRotation: Int): IntArray {
        var sizeX = sizeX
        var sizeY = sizeY
        val coords = IntArray(2)
        if (objectRotation and 0x1 == 1) {
            val prevSizeX = sizeX
            sizeX = sizeY
            sizeY = prevSizeX
        }
        when (mapRotation) {
            0 -> {
                coords[0] = x
                coords[1] = y
            }
            1 -> {
                coords[0] = y
                coords[1] = 7 - x - (sizeX - 1)
            }
            2 -> {
                coords[0] = 7 - x - (sizeX - 1)
                coords[1] = 7 - y - (sizeY - 1)
            }
            3 -> {
                coords[0] = 7 - y - (sizeY - 1)
                coords[1] = x
            }
        }
        return coords
    }
}