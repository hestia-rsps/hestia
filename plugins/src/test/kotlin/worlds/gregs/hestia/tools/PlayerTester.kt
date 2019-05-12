package worlds.gregs.hestia.tools

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.BeforeEach
import world.gregs.hestia.core.network.Session
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.core.archetypes.PlayerFactory
import worlds.gregs.hestia.artemis.events.CreatePlayer
import worlds.gregs.hestia.core.plugins.player.systems.PlayerCreation
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.getSystem

abstract class PlayerTester(config: WorldConfigurationBuilder) : GameTest(config.with(PlayerCreation())) {

    @BeforeEach
    fun setup() {
        EntityFactory.add(PlayerFactory())
    }

    internal fun fakePlayer(x: Int = 0, y: Int = 0, name: String = "Dummy"): Entity {
        val pc = world.getSystem(PlayerCreation::class)
        val entityId = pc.create(CreatePlayer(Session(), name, 0, 0, 0))
        val player = world.getEntity(entityId)
        val position = player.getComponent(Position::class)!!
        position.x = x
        position.y = y
        return player
    }

}