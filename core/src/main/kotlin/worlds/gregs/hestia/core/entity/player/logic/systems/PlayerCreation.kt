package worlds.gregs.hestia.core.entity.player.logic.systems

import com.artemis.Archetype
import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.events.CreatePlayer
import worlds.gregs.hestia.core.display.client.api.ClientNetwork
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.interfaces.model.components.GameFrame
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.player.logic.PlayerFactory
import java.util.concurrent.atomic.AtomicInteger

@Wire(failOnNull = false)
class PlayerCreation : PassiveSystem() {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var gameFrameMapper: ComponentMapper<GameFrame>
    private var network: ClientNetwork? = null
    private val count = AtomicInteger(0)
    private lateinit var archetype: Archetype

    override fun initialize() {
        archetype = PlayerFactory().getBuilder().build(world)
    }

    @Subscribe
    fun create(event: CreatePlayer): Int {
        val entityId = world.create(archetype)

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