package worlds.gregs.hestia.game

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import org.junit.jupiter.api.BeforeEach
import world.gregs.hestia.core.network.Session
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.PlayerFactory
import worlds.gregs.hestia.game.events.CreatePlayer
import worlds.gregs.hestia.game.plugins.player.systems.PlayerCreation
import worlds.gregs.hestia.services.getComponent
import worlds.gregs.hestia.services.getSystem

abstract class PlayerTester(config: WorldConfigurationBuilder) : GameTest(config.with(PlayerCreation())) {

    @BeforeEach
    fun setup() {
        EntityFactory.add(PlayerFactory())
    }

    internal fun fakePlayer(x: Int = 0, y: Int = 0, name: String = "Dummy"): Entity {
        val pc = world.getSystem(PlayerCreation::class)
        val entityId = pc.create(CreatePlayer(Session(), name))
        val player = world.getEntity(entityId)
        val position = player.getComponent(Position::class)!!
        position.x = x
        position.y = y
        return player
    }

}