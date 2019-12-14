package worlds.gregs.hestia.core.entity.mob.logic.systems.sync

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.core.entity.mob.api.Mob
import worlds.gregs.hestia.core.entity.entity.logic.EntityFactory
import worlds.gregs.hestia.core.entity.mob.logic.MobFactory
import worlds.gregs.hestia.core.entity.mob.model.events.CreateMob
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.entity.mob.logic.systems.MobCreation
import worlds.gregs.hestia.service.Aspect
import worlds.gregs.hestia.core.getComponent
import worlds.gregs.hestia.service.getSystem

internal class MobIndexSystemTest : GameTest(WorldConfigurationBuilder().with(MobCreation(), MobIndexSystem())) {

    @BeforeEach
    override fun setup() {
        super.setup()
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