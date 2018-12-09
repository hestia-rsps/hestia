package worlds.gregs.hestia.game.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.MobFactory
import worlds.gregs.hestia.game.events.CreateMob
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.api.mob.Mob
import worlds.gregs.hestia.game.plugins.mob.systems.MobCreation
import worlds.gregs.hestia.game.plugins.mob.systems.sync.MobIndexSystem
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.getSystem

internal class MobIndexSystemTest : GameTest(WorldConfigurationBuilder().with(MobCreation(), MobIndexSystem())) {

    @Test
    override fun setUp() {
        super.setUp()
        EntityFactory.add(MobFactory())
    }

    private fun fakeMob(id: Int = 0): Entity {
        val mc = world.getSystem(MobCreation::class)
        return world.getEntity(mc.create(CreateMob(id, 0, 0)))
    }

    @Test
    fun testIndexChanges() {
        val sub = world.aspectSubscriptionManager.get(Aspect.all(Mob::class))
        for(i in 0 until 5) {
            fakeMob()
        }

        tick()

        Assertions.assertThat(sub.entities.size()).isEqualTo(5)

        world.delete(0)//Delete the first entity

        tick()

        val second = world.getEntity(1)

        Assertions.assertThat(second.getComponent(ClientIndex::class)?.index).isEqualTo(2)

        val fifth = fakeMob()

        tick()

        Assertions.assertThat(fifth.getComponent(ClientIndex::class)?.index).isEqualTo(1)
    }
}