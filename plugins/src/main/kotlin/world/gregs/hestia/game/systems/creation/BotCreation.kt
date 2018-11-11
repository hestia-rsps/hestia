package world.gregs.hestia.game.systems.creation

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import world.gregs.hestia.game.archetypes.BotFactory
import world.gregs.hestia.game.component.update.DisplayName
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.events.CreateBot
import world.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.game.archetypes.EntityFactory
import world.gregs.hestia.game.component.entity.Player

class BotCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var subscription: EntitySubscription

    override fun initialize() {
        super.initialize()
        subscription = world.aspectSubscriptionManager.get(Aspect.all(Player::class))
    }

    @Subscribe
    fun create(event: CreateBot) {
        val entityId = EntityFactory.create(BotFactory::class)

        val displayName = displayNameMapper.get(entityId)
        displayName?.name = event.name

        val position = positionMapper.get(entityId)
        position.y = 3501 - subscription.entities.size()
        position.x = 3087
    }
}