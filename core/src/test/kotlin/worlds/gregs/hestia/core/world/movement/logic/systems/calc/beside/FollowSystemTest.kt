package worlds.gregs.hestia.core.world.movement.logic.systems.calc.beside

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.dependsOn
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.MovementPlugin
import worlds.gregs.hestia.core.world.movement.model.components.calc.Following
import worlds.gregs.hestia.core.world.movement.model.components.calc.Step
import worlds.gregs.hestia.service.cache.definition.systems.ObjectDefinitionSystem
import worlds.gregs.hestia.tools.PlayerTester

class FollowSystemTest : PlayerTester(WorldConfigurationBuilder().dependsOn(MovementPlugin::class)) {

    override fun config(config: WorldConfigurationBuilder) {
        config.with(ObjectDefinitionSystem(mock()))
    }

    @Test
    fun `Follow watches entity`() {
        val player = fakePlayer(0, 0)
        val partner = fakePlayer(0, 1)
        tick()
        partner.edit().add(Following(player.id))
        tick()

//        val watch = partner.getComponent(Watch::class)
//        assertThat(watch).isNotNull
//        assertThat(watch!!.entity).isEqualTo(player.id)
    }

    @Test
    fun `Follow delay between ticks`() {
        val player = fakePlayer(0, 0)
        val partner = fakePlayer(0, 1)
        tick()
        partner.edit().add(Following(player.id))

        tick()

        println("Player steps east")
        player.edit().add(Step(1, 0))
        tick()
        //Check player has moved
        assertPosition(player, 1, 0)
        //And partner hasn't moved yet
        assertPosition(partner, 0, 1)
        tick()
        println("Partner steps south")
        //Check partner moved
        assertPosition(partner, 0, 0)
    }
    /*
    Only npcs follow beside
        Steps to last position
        Unless step is towards the follower then the follower steps backwards (the same direction as the followee)
     */

    private fun assertPosition(entity: Entity, x: Int, y: Int) {
        val position = entity.getComponent(Position::class)
        assertThat(position).isNotNull
        assertThat(position!!.x).isEqualTo(x)
        assertThat(position.y).isEqualTo(y)
    }

    @Test
    fun `Follow dancing`() {
        //Begin
        println("Player is at 0, 0")
        println("Partner is at 0, 1")
        val player = fakePlayer(0, 0)
        val partner = fakePlayer(0, 1)

        tick()

        println("Partner follows player")
        //Partner is following player
        partner.edit().add(Following(player.id))
        assertPosition(player, 0, 0)
        assertPosition(partner, 0, 1)

        tick()

        //[]
        //[]->
        println("Player steps east")
        //Player steps to the right
        player.edit().add(Step(1, 0))
        //Player begins to follow partner
        player.edit().add(Following(partner.id))

        tick()

        //[]
        //  []
        println("Player moves east")
        assertPosition(player, 1, 0)
        assertPosition(partner, 0, 1)

        tick()

        //
        //[][]
        println("Partner moves south")
        assertPosition(partner, 0, 0)

        tick()

        //[]
        //[]
        println("Player moves north-west")
        assertPosition(player, 0, 1)

        tick()

        //[]
        //  []
        println("Partner moves east")
        assertPosition(partner, 1, 0)

        tick()

        //
        //[][]
        println("Player moves south")
        assertPosition(player, 0, 0)

        tick()

        //
        //[][]
        println("Partner moves north-west")
        assertPosition(partner, 0, 1)

        //Rinse and repeat...
    }
}
