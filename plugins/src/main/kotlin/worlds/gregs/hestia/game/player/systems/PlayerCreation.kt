package worlds.gregs.hestia.game.player.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.component.update.DisplayName
import worlds.gregs.hestia.game.component.NetworkSession
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.events.CreatePlayer
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import java.util.concurrent.atomic.AtomicInteger
import worlds.gregs.hestia.game.player.archetypes.PlayerFactory

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