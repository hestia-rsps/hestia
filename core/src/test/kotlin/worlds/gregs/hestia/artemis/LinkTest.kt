package worlds.gregs.hestia.artemis

import com.artemis.Aspect
import com.artemis.Component
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.PreserveProcessVisiblity
import com.artemis.link.EntityLinkManager
import com.artemis.link.LinkAdapter
import com.artemis.utils.Bag
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.world.movement.model.components.RunToggled
import worlds.gregs.hestia.tools.PlayerTester

@PreserveProcessVisiblity
class LinkTest : PlayerTester(WorldConfigurationBuilder().with(EntityLinkManager())) {

    @Test
    fun test() {

        val player = fakePlayer()

        world.getSystem(EntityLinkManager::class).register(RunToggled::class.java, object : LinkAdapter() {
            override fun onLinkEstablished(sourceId: Int, targetId: Int) {
                println("Linked $sourceId $targetId")
                super.onLinkEstablished(sourceId, targetId)
            }

            override fun onTargetChanged(sourceId: Int, targetId: Int, oldTargetId: Int) {
                println("Target change")
                super.onTargetChanged(sourceId, targetId, oldTargetId)
            }

            override fun onTargetDead(sourceId: Int, deadTargetId: Int) {
                println("Target dead $sourceId $deadTargetId")
                super.onTargetDead(sourceId, deadTargetId)
            }

            override fun onLinkKilled(sourceId: Int, targetId: Int) {
                println("Link killed $sourceId $targetId")
                super.onLinkKilled(sourceId, targetId)
            }
        })
        player.edit().add(RunToggled())

        tick()
        var bag = world.componentManager.getComponentsFor(player.id, Bag<Component>())

        println("Components ${bag.toList()}")


        world.deleteEntity(player)
        tick()

        bag = world.componentManager.getComponentsFor(player.id, Bag<Component>())

        println(world.aspectSubscriptionManager.get(Aspect.all(RunToggled::class.java)).entities.size())
        println("Components ${bag.toList()}")
    }
}