package world.gregs.hestia.game

import com.artemis.Aspect
import com.artemis.Component
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.PreserveProcessVisiblity
import com.artemis.link.EntityLinkManager
import com.artemis.link.LinkAdapter
import com.artemis.utils.Bag
import world.gregs.hestia.game.component.movement.Running
import world.gregs.hestia.services.getComponent
import org.junit.jupiter.api.Test
import world.gregs.hestia.services.getSystem

@PreserveProcessVisiblity
class LinkTest : GameTest(WorldConfigurationBuilder().with(EntityLinkManager())) {

    @Test
    fun test() {

        val player = fakePlayer()

        w.getSystem(EntityLinkManager::class).register(Running::class.java, object : LinkAdapter() {
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
        player.edit().add(Running())
        player.getComponent(Running::class)?.entity = player.id

        tick()
        var bag = w.componentManager.getComponentsFor(player.id, Bag<Component>())

        println("Components ${bag.toList()}")


        w.deleteEntity(player)
        tick()

        bag = w.componentManager.getComponentsFor(player.id, Bag<Component>())

        println(w.aspectSubscriptionManager.get(Aspect.all(Running::class.java)).entities.size())
        println("Components ${bag.toList()}")
    }

}