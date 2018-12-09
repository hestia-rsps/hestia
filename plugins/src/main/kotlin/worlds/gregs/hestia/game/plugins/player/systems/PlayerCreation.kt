package worlds.gregs.hestia.game.plugins.player.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.api.client.ClientNetwork
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.PlayerFactory
import worlds.gregs.hestia.game.events.CreatePlayer
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.entity.components.update.DisplayName
import java.util.concurrent.atomic.AtomicInteger

@Wire(failOnNull = false)
class PlayerCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>
    private var network: ClientNetwork? = null
    private val count = AtomicInteger(0)

    @Subscribe
    fun create(event: CreatePlayer): Int {
        val entityId = EntityFactory.create(PlayerFactory::class)

        if(event.session.channel != null) {
            network?.setup(entityId, event.session.channel!!)
        }

        event.session.id = entityId

        val displayName = displayNameMapper.get(entityId)
        displayName?.name = event.name

        val position = positionMapper.get(entityId)
        position.set(3082, 3500)// - count.getAndIncrement())
        return entityId
    }
}