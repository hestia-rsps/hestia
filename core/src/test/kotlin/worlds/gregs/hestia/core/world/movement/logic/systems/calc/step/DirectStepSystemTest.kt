package worlds.gregs.hestia.core.world.movement.logic.systems.calc.step

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.artemis.dependsOn
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.artemis.remove
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.display.update.model.Direction.Companion.deltaX
import worlds.gregs.hestia.core.display.update.model.Direction.Companion.deltaY
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.movement.MovementPlugin
import worlds.gregs.hestia.core.world.movement.model.components.RunToggled
import worlds.gregs.hestia.core.world.movement.model.components.Steps
import worlds.gregs.hestia.core.world.movement.model.components.calc.Step
import worlds.gregs.hestia.tools.PlayerTester
import kotlin.math.abs
import kotlin.math.max

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
        entity.edit().add(Step(position.x + xOffset, position.y + yOffset, check = check))

        //Process i = max offset ticks
        for(i in 0 until max(abs(xOffset), abs(yOffset))) {
            tick()
        }

        //Check position
        assertThat(position.x).describedAs("Offset: %s, %s Start: %s, %s Check: %s", xOffset, yOffset, startX, startY, check).isEqualTo(startX + xOffset)
        assertThat(position.y).describedAs("Offset: %s, %s Start: %s, %s Check: %s", xOffset, yOffset, startX, startY, check).isEqualTo(startY + yOffset)

        //Clear last position
        entity.edit().remove(Steps::class)
    }
}