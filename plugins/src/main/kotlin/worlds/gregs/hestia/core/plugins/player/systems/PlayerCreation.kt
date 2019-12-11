package worlds.gregs.hestia.core.plugins.player.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.client.ClientNetwork
import worlds.gregs.hestia.api.client.update.components.DisplayName
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.artemis.events.CreatePlayer
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.core.archetypes.PlayerFactory
import worlds.gregs.hestia.game.entity.components.Position
import java.util.concurrent.atomic.AtomicInteger

@Wire(failOnNull = false)
class PlayerCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private var network: ClientNetwork? = null
    private val count = AtomicInteger(0)

    @Subscribe
    fun create(event: CreatePlayer): Int {
        val entityId = EntityFactory.create(PlayerFactory::class)

        if(event.session.channel != null) {
            network?.setup(entityId, event.session.channel!!)
        }

        val gameFrame = gameFrameMapper.get(entityId)
        gameFrame.displayMode = event.displayMode
        gameFrame.width = event.screenWidth
        gameFrame.height = event.screenHeight

        event.session.id = entityId

        val displayName = displayNameMapper.get(entityId)
        displayName?.name = event.name

        val position = positionMapper.get(entityId)
        position.set(3087, 3497)// - count.getAndIncrement())
        return entityId
    }
}