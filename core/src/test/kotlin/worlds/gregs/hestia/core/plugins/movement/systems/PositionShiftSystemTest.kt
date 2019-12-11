package worlds.gregs.hestia.core.plugins.movement.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.services.getComponent

internal class PositionShiftSystemTest : GameTest(WorldConfigurationBuilder().with(PositionShiftSystem())) {

    @Test
    fun process() {
        val entity = create()
        val position = entity.getComponent(Position::class)!!
        check(position, 0, 0, 0)
        //Addition
        entity.edit().add(Shift(4, 0, 0))
        tick()
        check(position, 4, 0, 0)
        //Subtraction
        entity.edit().add(Shift(-2, 0, 0))
        tick()
        check(position, 2, 0, 0)
    }

    private fun check(position: Position, x: Int, y: Int, plane: Int) {
        Assertions.assertThat(position.x).isEqualTo(x)
        Assertions.assertThat(position.y).isEqualTo(y)
        Assertions.assertThat(position.plane).isEqualTo(plane)
    }

    private fun create(): Entity {
        val entity = world.createEntity()
        entity.edit().add(Position(0, 0, 0))
        tick()
        return entity
    }
}