package worlds.gregs.hestia.game.plugins.movement.systems.calc.beside

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.tools.PlayerTester
import worlds.gregs.hestia.game.plugins.MovementPlugin
import worlds.gregs.hestia.game.update.components.direction.Watch
import worlds.gregs.hestia.game.plugins.entity.systems.follow
import worlds.gregs.hestia.game.plugins.entity.systems.step
import worlds.gregs.hestia.services.dependsOn
import worlds.gregs.hestia.services.getComponent

class FollowSystemTest : PlayerTester(WorldConfigurationBuilder().dependsOn(MovementPlugin::class)) {

    @Test
    fun `Follow watches entity`() {
        val player = fakePlayer(0, 0)
        val partner = fakePlayer(0, 1)
        tick()
        partner.follow(player)
        tick()

        val watch = partner.getComponent(Watch::class)
        assertThat(watch).isNotNull
        assertThat(watch!!.entity).isEqualTo(player.id)
    }

    @Test
    fun `Follow delay between ticks`() {
        val player = fakePlayer(0, 0)
        val partner = fakePlayer(0, 1)
        tick()
        partner.follow(player)

        tick()

        println("Player steps east")
        player.step(1, 0)
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
    Only mobs follow beside
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
        partner.follow(player)
        assertPosition(player, 0, 0)
        assertPosition(partner, 0, 1)

        tick()

        //[]
        //[]->
        println("Player steps east")
        //Player steps to the right
        player.step(1, 0)
        //Player begins to follow partner
        player.follow(partner)

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
