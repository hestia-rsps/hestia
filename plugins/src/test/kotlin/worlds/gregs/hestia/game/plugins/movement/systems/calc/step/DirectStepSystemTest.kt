package worlds.gregs.hestia.game.plugins.movement.systems.calc.step

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.tools.PlayerTester
import worlds.gregs.hestia.game.plugins.MovementPlugin
import worlds.gregs.hestia.game.plugins.entity.systems.step
import worlds.gregs.hestia.game.plugins.movement.components.RunToggled
import worlds.gregs.hestia.game.plugins.movement.components.Steps
import worlds.gregs.hestia.game.client.update.block.Direction
import worlds.gregs.hestia.game.client.update.block.Direction.Companion.deltaX
import worlds.gregs.hestia.game.client.update.block.Direction.Companion.deltaY
import worlds.gregs.hestia.services.dependsOn
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.remove

internal class DirectStepSystemTest : PlayerTester(WorldConfigurationBuilder().dependsOn(MovementPlugin::class)) {


    @Test
    fun walk() {
        val player = fakePlayer(3501, 3087)
        val counter = Direction.size

        //Check every direction near and far
        for(index in 0 until counter) {
            test(player, deltaX(index), deltaY(index), false)
            test(player, deltaX(index) * 10, deltaY(index) * 10, false)
        }
    }

    @Test
    fun run() {
        val player = fakePlayer(3501, 3087)
        player.edit().add(RunToggled())
        val counter = Direction.size

        //Check every direction near and far
        for(index in 0 until counter) {
            test(player, deltaX(index), deltaY(index), false)
            test(player, deltaX(index) * 10, deltaY(index) * 10, false)
        }
    }

    private fun test(entity: Entity, xOffset: Int, yOffset: Int, check: Boolean, startX: Int = 100, startY: Int = 100) {
        //Set starting position
        val position = entity.getComponent(Position::class) ?: return
        position.x = startX
        position.y = startY

        //Navigate to position
        entity.step(position.x + xOffset, position.y + yOffset, check = check)

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
}