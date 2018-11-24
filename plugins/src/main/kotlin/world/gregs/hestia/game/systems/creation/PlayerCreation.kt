package world.gregs.hestia.game.systems.creation

import com.artemis.ComponentMapper
import world.gregs.hestia.game.archetypes.EntityFactory
import world.gregs.hestia.game.archetypes.PlayerFactory
import world.gregs.hestia.game.component.update.DisplayName
import world.gregs.hestia.game.component.NetworkSession
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.events.CreatePlayer
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import java.util.concurrent.atomic.AtomicInteger

class PlayerCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var networkSessionMapper: ComponentMapper<NetworkSession>
    private val count = AtomicInteger(0)

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
        position.set(3087, 3501 - count.getAndIncrement())
        return entityId
    }
}