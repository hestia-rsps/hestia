package worlds.gregs.hestia.game.systems.creation

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.component.update.DisplayName
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.events.CreateBot
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.archetypes.EntityFactory

class BotCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>

    @Subscribe
    fun create(event: CreateBot) {
        val entityId = EntityFactory.create(worlds.gregs.hestia.game.bot.archetypes.BotFactory::class)

        val displayName = displayNameMapper.get(entityId)
        displayName?.name = event.name

        val position = positionMapper.get(entityId)
        position.set(event.x, event.y, event.plane)
    }
}