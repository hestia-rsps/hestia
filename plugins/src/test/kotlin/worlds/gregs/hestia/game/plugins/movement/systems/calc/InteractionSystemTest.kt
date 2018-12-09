package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.PlayerFactory
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.entity.systems.interact
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.RunToggled
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.plugins.movement.systems.PositionShiftSystem
import worlds.gregs.hestia.game.plugins.movement.systems.WalkSystem
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.remove

internal class InteractionSystemTest : GameTest(WorldConfigurationBuilder().with(InteractionSystem(), WalkSystem(), PositionShiftSystem())) {

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
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], 2, true)
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], 2, false)
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], 10, true)
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], 10, false)
        }
    }

    @Test
    fun run() {
        val player = fakePlayer(3501, 3087)
        player.edit().add(RunToggled())
        val counter = DirectionUtils.DELTA_X.size

        //Check every direction near and far
        for(index in 0 until counter) {
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], 2, true)
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], 2, false)
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], 10, true)
            test(player, DirectionUtils.DELTA_X[index], DirectionUtils.DELTA_Y[index], 10, false)
        }
    }

    private fun test(entity: Entity, xOffset: Int, yOffset: Int, multiplier: Int, calculate: Boolean, startX: Int = 100, startY: Int = 100) {
        //Set starting position
        val position = entity.getComponent(Position::class) ?: return
        position.x = startX
        position.y = startY

        //Navigate to position
        entity.interact(position.x + xOffset * multiplier, position.y + yOffset * multiplier, check = false, calculate = calculate)

        //Process i = max offset ticks
        for(i in 0 until Math.max(Math.abs(xOffset * multiplier), Math.abs(yOffset * multiplier))) {
            tick()
        }

        //Check position
        Assertions.assertThat(position.x).describedAs("Offset: %s, %s Start: %s, %s Multiplier: %s Calc: %s", xOffset, yOffset, startX, startY, multiplier, calculate).isEqualTo(startX + (xOffset * multiplier) - xOffset)
        Assertions.assertThat(position.y).describedAs("Offset: %s, %s Start: %s, %s Multiplier: %s Calc: %s", xOffset, yOffset, startX, startY, multiplier, calculate).isEqualTo(startY + (yOffset * multiplier) - yOffset)

        //Clear last position
        entity.edit().remove(Steps::class)
    }

    private fun fakePlayer(x: Int = 0, y: Int = 0): Entity {
        val entity = world.createEntity()
        entity.edit().add(Mobile()).add(Steps()).add(Position(x, y))
        return entity
    }
}