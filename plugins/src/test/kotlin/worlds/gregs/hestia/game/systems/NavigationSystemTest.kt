package worlds.gregs.hestia.game.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import world.gregs.hestia.core.network.Session
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.PlayerFactory
import worlds.gregs.hestia.game.events.CreatePlayer
import worlds.gregs.hestia.game.plugins.MovementPlugin
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.movement.components.RunToggled
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.plugins.movement.components.navigate
import worlds.gregs.hestia.game.plugins.player.systems.PlayerCreation
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.services.dependsOn
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.getSystem
import worlds.gregs.hestia.services.remove

internal class NavigationSystemTest : GameTest(WorldConfigurationBuilder().dependsOn(MovementPlugin::class).with(PlayerCreation())) {

    //TODO clipping checks

    @BeforeEach
    override fun setUp() {
        super.setUp()
        EntityFactory.add(PlayerFactory())
    }

    @Test
    fun walk() {
        val player = fakePlayer(3501, 3087)
        val counter = DirectionUtils.DELTA_X.size

        //Check every direction near and far
        for(index in 0 until counter) {
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], false)
            test(player, DirectionUtils.DELTA_X[index] * 10, DirectionUtils.DELTA_Y[index] * 10, false)
        }
    }

    @Test
    fun run() {
        val player = fakePlayer(3501, 3087)
        player.edit().add(RunToggled())
        val counter = DirectionUtils.DELTA_X.size

        //Check every direction near and far
        for(index in 0 until counter) {
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], false)
            test(player, DirectionUtils.DELTA_X[index] * 10, DirectionUtils.DELTA_Y[index] * 10, false)
        }
    }

    private fun test(entity: Entity, xOffset: Int, yOffset: Int, check: Boolean, startX: Int = 100, startY: Int = 100) {
        //Set starting position
        val position = entity.getComponent(Position::class) ?: return
        position.x = startX
        position.y = startY

        //Navigate to position
        entity.navigate(position.x + xOffset, position.y + yOffset, check = check)

        //Process i = max offset ticks
        for(i in 0 until Math.max(Math.abs(xOffset), Math.abs(yOffset))) {
            tick()
        }

        //Check position
        assertThat(position.x).describedAs("Offset: %s, %s Start: %s, %s Check: %s", xOffset, yOffset, startX, startY, check).isEqualTo(startX + xOffset)
        assertThat(position.y).describedAs("Offset: %s, %s Start: %s, %s Check: %s", xOffset, yOffset, startX, startY, check).isEqualTo(startY + yOffset)

        //Clear last position
        entity.edit().remove(Steps::class)
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