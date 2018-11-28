package worlds.gregs.hestia.game

import com.artemis.Aspect
import com.artemis.Component
import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import com.artemis.annotations.PreserveProcessVisiblity
import com.artemis.link.EntityLinkManager
import com.artemis.link.LinkAdapter
import com.artemis.utils.Bag
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.Session
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.PlayerFactory
import worlds.gregs.hestia.game.events.CreatePlayer
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.movement.components.RunToggled
import worlds.gregs.hestia.game.plugins.player.systems.PlayerCreation
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.getSystem

@PreserveProcessVisiblity
class LinkTest : GameTest(WorldConfigurationBuilder().with(EntityLinkManager(), PlayerCreation())) {


    @BeforeEach
    override fun setUp() {
        super.setUp()
        EntityFactory.add(PlayerFactory())
    }

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
        player.getComponent(RunToggled::class)?.entity = player.id

        tick()
        var bag = world.componentManager.getComponentsFor(player.id, Bag<Component>())

        println("Components ${bag.toList()}")


        world.deleteEntity(player)
        tick()

        bag = world.componentManager.getComponentsFor(player.id, Bag<Component>())

        println(world.aspectSubscriptionManager.get(Aspect.all(RunToggled::class.java)).entities.size())
        println("Components ${bag.toList()}")
    }


    private fun fakePlayer(x: Int = 0, y: Int = 0, name: String = "Dummy"): Entity {
        val pc = world.getSystem(PlayerCreation::class)
        val entityId = pc.create(CreatePlayer(Session(), name))
        val player = world.getEntity(entityId)
        val position = player.getComponent(Position::class)!!
        position.x = x
        position.y = y
        return player
    }
}