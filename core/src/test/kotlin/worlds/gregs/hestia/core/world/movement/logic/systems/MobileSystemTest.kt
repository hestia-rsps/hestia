package worlds.gregs.hestia.core.world.movement.logic.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.getComponent

internal class MobileSystemTest : GameTest(WorldConfigurationBuilder().with(MobileSystem())) {

    @Test
    fun process() {
        val entity = create()
        //Adding
        val mobile = Mobile()
        entity.edit().add(mobile)
        tick()
        check(mobile, 0, 0, 0)
        tick()
        //Changing
        val position = entity.getComponent(Position::class)!!
        position.x = 1
        position.y = 1
        check(mobile, 0, 0, 0)
        tick()
        check(mobile, 1, 1, 0)

        //Removal
        entity.edit().remove(mobile)
        tick()
        position.x = 2
        position.y = 2
        tick()
        check(mobile, 1, 1, 0)
    }

    private fun check(mobile: Mobile, x: Int, y: Int, plane: Int) {
        assertThat(mobile.x).isEqualTo(x)
        assertThat(mobile.y).isEqualTo(y)
        assertThat(mobile.plane).isEqualTo(plane)
    }

    private fun create(): Entity {
        val entity = world.createEntity()
        entity.edit().add(Position(0, 0, 0))
        tick()
        return entity
    }
}