package world.gregs.hestia.game.systems.creation

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import world.gregs.hestia.game.archetypes.EntityFactory
import world.gregs.hestia.game.archetypes.PlayerFactory
import world.gregs.hestia.game.component.update.DisplayName
import world.gregs.hestia.game.component.NetworkSession
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.events.CreatePlayer
import world.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.game.component.entity.Player

class PlayerCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var networkSessionMapper: ComponentMapper<NetworkSession>
    private lateinit var subscription: EntitySubscription

    override fun initialize() {
        super.initialize()
        subscription = world.aspectSubscriptionManager.get(Aspect.all(Player::class))
    }

    @Subscribe
    fun create(event: CreatePlayer): Int {
        val entityId = EntityFactory.create(PlayerFactory::class)

        if(event.session.channel != null) {
            val session = networkSessionMapper.create(entityId)
            session.channel = event.session.channel!!
        }
        event.session.id = entityId

        val displayName = displayNameMapper.get(entityId)
        displayName?.name = event.name

        val position = positionMapper.get(entityId)
        position.y = 3501 - subscription.entities.size()
        position.x = 3087
        return entityId
    }
}