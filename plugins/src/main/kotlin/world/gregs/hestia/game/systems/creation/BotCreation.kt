package world.gregs.hestia.game.systems.creation

import com.artemis.ComponentMapper
import world.gregs.hestia.game.archetypes.BotFactory
import world.gregs.hestia.game.component.update.DisplayName
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.events.CreateBot
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.game.archetypes.EntityFactory
import java.util.concurrent.atomic.AtomicInteger

class BotCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>

    @Subscribe
    fun create(event: CreateBot) {
        val entityId = EntityFactory.create(BotFactory::class)

        val displayName = displayNameMapper.get(entityId)
        displayName?.name = event.name

        val position = positionMapper.get(entityId)
        position.x = event.x
        position.y = event.y
        position.plane = event.plane
    }
}