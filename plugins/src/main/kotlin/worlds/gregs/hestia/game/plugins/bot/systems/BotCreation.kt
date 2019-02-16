package worlds.gregs.hestia.game.plugins.bot.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.archetypes.BotFactory
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.events.CreateBot
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.components.DisplayName

class BotCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>

    @Subscribe
    fun create(event: CreateBot) {
        val entityId = EntityFactory.create(BotFactory::class)

        val displayName = displayNameMapper.get(entityId)
        displayName?.name = event.name

        val position = positionMapper.get(entityId)
        position.set(event.x, event.y, event.plane)
    }
}