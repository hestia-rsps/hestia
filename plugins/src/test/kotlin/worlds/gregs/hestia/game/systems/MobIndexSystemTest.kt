package worlds.gregs.hestia.game.systems

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.component.entity.ClientIndex
import worlds.gregs.hestia.game.mob.component.Mob
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.getComponent
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.mob.systems.sync.MobIndexSystem

internal class MobIndexSystemTest : GameTest(WorldConfigurationBuilder().with(MobIndexSystem())) {

    @Test
    fun testIndexChanges() {
        val sub = w.aspectSubscriptionManager.get(Aspect.all(Mob::class))
        for(i in 0 until 5) {
            fakeMob()
        }

        tick()

        Assertions.assertThat(sub.entities.size()).isEqualTo(5)

        w.delete(0)//Delete the first entity

        tick()

        val second = w.getEntity(1)

        Assertions.assertThat(second.getComponent(ClientIndex::class)?.index).isEqualTo(2)

        val fifth = fakeMob()

        tick()

        Assertions.assertThat(fifth.getComponent(ClientIndex::class)?.index).isEqualTo(1)
    }
}