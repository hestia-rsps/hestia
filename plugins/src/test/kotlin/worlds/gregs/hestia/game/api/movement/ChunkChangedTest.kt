package worlds.gregs.hestia.game.api.movement

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.RegionFactory
import worlds.gregs.hestia.game.events.CreateRegion
import worlds.gregs.hestia.game.plugin.Plugin.Companion.PRE_SHIFT_PRIORITY
import worlds.gregs.hestia.game.plugins.MovementPlugin
import worlds.gregs.hestia.game.plugins.RegionPlugin
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Position.Companion.regionId
import worlds.gregs.hestia.game.plugins.entity.systems.move
import worlds.gregs.hestia.game.plugins.entity.systems.navigate
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.services.dependsOn

internal class ChunkChangedTest : GameTest(WorldConfigurationBuilder().dependsOn(RegionPlugin::class, MovementPlugin::class).with(PRE_SHIFT_PRIORITY, ChunkChange())) {

    private class ChangedChunk : Exception()
    private class ChunkChange : ChunkChanged(Mobile::class) {
        override fun changedChunk(entityId: Int, from: Int, to: Int) {
            throw ChangedChunk()
        }
    }

    @BeforeEach
    override fun setUp() {
        super.setUp()
        EntityFactory.add(RegionFactory())
    }

    private fun create(x: Int, y: Int, plane: Int = 0): Entity {
        es.dispatch(CreateRegion(regionId(0, 0)))
        val entity = world.createEntity()
        entity.edit().add(Position(x, y, plane)).add(Mobile())
        tick()
        return entity
    }

    @Test
    fun movedLocal() {
        val entity = create(7, 0)
        entity.move(8, 0, 0)
        assertThrows<ChangedChunk> {
            tick()
        }
    }

    @Test
    fun movedRegion() {
        val entity = create(63, 0)
        entity.move(64, 0, 0)
        assertThrows<ChangedChunk> {
            tick()
        }
    }

    @Test
    fun navigate() {
        val entity = create(7, 0)
        entity.navigate(8, 0, 0)
        assertThrows<ChangedChunk> {
            tick()
        }
    }

    @Test
    fun navigateRegion() {
        val entity = create(63, 0)
        entity.navigate(64, 0, 0)
        assertThrows<ChangedChunk> {
            tick()
        }
    }
}