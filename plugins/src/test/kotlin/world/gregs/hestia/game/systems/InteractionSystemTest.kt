package world.gregs.hestia.game.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.movement.Running
import world.gregs.hestia.game.component.movement.Steps
import world.gregs.hestia.game.component.movement.interact
import world.gregs.hestia.game.plugins.MovementPlugin
import world.gregs.hestia.services.getComponent
import world.gregs.hestia.services.remove
import world.gregs.hestia.game.GameTest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import world.gregs.hestia.game.update.DirectionUtils
import world.gregs.hestia.services.dependsOn

internal class InteractionSystemTest : GameTest(WorldConfigurationBuilder().dependsOn(MovementPlugin::class)) {

    //TODO with & without clipping checks

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
        player.edit().add(Running())
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
}