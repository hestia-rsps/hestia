package worlds.gregs.hestia.core.world.movement.api.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.artemis.dependsOn
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Position.Companion.regionId
import worlds.gregs.hestia.core.world.movement.MovementPlugin
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.calc.Step
import worlds.gregs.hestia.core.world.movement.model.components.types.MoveStep
import worlds.gregs.hestia.core.world.movement.model.components.types.Movement
import worlds.gregs.hestia.core.world.region.RegionPlugin
import worlds.gregs.hestia.core.world.region.model.events.CreateRegion
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem

internal class ChunkChangedTest : GameTest(WorldConfigurationBuilder().dependsOn(RegionPlugin::class, MovementPlugin::class).with(PRE_SHIFT_PRIORITY, ChunkChange())) {

    override fun config(config: WorldConfigurationBuilder) {
        config.with(ObjectDefinitionSystem(mock()))
    }

    private class ChangedChunk : Exception()
    private class ChunkChange : ChunkChanged(Mobile::class) {
        override fun changedChunk(entityId: Int, from: Int, to: Int) {
            throw ChangedChunk()
        }
    }

    private fun create(x: Int, y: Int, plane: Int = 0): Entity {
        es.dispatch(CreateRegion(regionId(0, 0)))
        val entity = world.createEntity()
        entity.edit().add(Position(x, y, plane)).add(Mobile()).add(Movement())
        tick()
        return entity
    }

    @Test
    fun movedLocal() {
        val entity = create(7, 0)
        entity.edit().add(MoveStep(8, 0, 0))
        assertThrows<ChangedChunk> {
            tick()
        }
    }

    @Test
    fun movedRegion() {
        val entity = create(63, 0)
        entity.edit().add(MoveStep(64, 0, 0))
        assertThrows<ChangedChunk> {
            tick()
        }
    }

    @Test
    fun navigate() {
        val entity = create(7, 0)
        entity.edit().add(Step(8, 0, 0))
        assertThrows<ChangedChunk> {
            tick()
        }
    }

    @Test
    fun navigateRegion() {
        val entity = create(63, 0)
        entity.edit().add(Step(64, 0, 0))
        assertThrows<ChangedChunk> {
            tick()
        }
    }
}