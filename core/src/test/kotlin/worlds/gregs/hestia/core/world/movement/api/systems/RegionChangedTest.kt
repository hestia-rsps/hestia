package worlds.gregs.hestia.core.world.movement.api.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.artemis.dependsOn
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.MovementPlugin
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.calc.Step
import worlds.gregs.hestia.core.world.movement.model.components.types.MoveStep
import worlds.gregs.hestia.core.world.region.RegionPlugin
import worlds.gregs.hestia.core.world.region.model.events.CreateRegion
import worlds.gregs.hestia.game.plugin.Plugin

internal class RegionChangedTest : GameTest(WorldConfigurationBuilder().dependsOn(RegionPlugin::class, MovementPlugin::class).with(Plugin.PRE_SHIFT_PRIORITY, RegionChange())) {

    private class ChangedRegion : Exception()
    private class RegionChange : RegionChanged(Mobile::class) {
        override fun changedRegion(entityId: Int, from: Int, to: Int) {
            throw ChangedRegion()
        }
    }

    private fun create(x: Int, y: Int, plane: Int = 0): Entity {
        es.dispatch(CreateRegion(Position.regionId(0, 0)))
        val entity = world.createEntity()
        entity.edit().add(Position(x, y, plane)).add(Mobile())
        tick()
        return entity
    }

    @Test
    fun movedLocal() {
        val entity = create(7, 0)
        entity.edit().add(MoveStep(65, 0, 0))
        assertThrows<ChangedRegion> {
            tick()
        }
    }

    @Test
    fun navigate() {
        val entity = create(63, 0)
        entity.edit().add(Step(65, 0, 0))
        assertThrows<ChangedRegion> {
            tick()
        }
    }
}