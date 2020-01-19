package worlds.gregs.hestia.core.entity.npc.logic.systems.sync

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.entity.npc.api.Npc
import worlds.gregs.hestia.core.entity.npc.logic.systems.NpcCreation
import worlds.gregs.hestia.core.entity.npc.model.events.CreateNpc

internal class NpcIndexSystemTest : GameTest(WorldConfigurationBuilder().with(NpcCreation(false), NpcIndexSystem())) {

    private fun fakeNpc(id: Int = 0): Entity {
        val mc = world.getSystem(NpcCreation::class)
        return world.getEntity(mc.create(CreateNpc(id, 0, 0)))
    }

    @Test
    fun testIndexChanges() {
        val sub = world.aspectSubscriptionManager.get(Aspect.all(Npc::class))
        for(i in 0 until 5) {
            fakeNpc()
        }

        tick()

        Assertions.assertThat(sub.entities.size()).isEqualTo(5)

        world.delete(0)//Delete the first entity

        tick()

        val second = world.getEntity(1)

        Assertions.assertThat(second.getComponent(ClientIndex::class)?.index).isEqualTo(2)

        val fifth = fakeNpc()

        tick()

        Assertions.assertThat(fifth.getComponent(ClientIndex::class)?.index).isEqualTo(1)
    }
}