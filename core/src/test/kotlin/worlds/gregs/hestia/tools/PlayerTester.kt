package worlds.gregs.hestia.tools

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import world.gregs.hestia.core.network.Session
import worlds.gregs.hestia.GameTest
import worlds.gregs.hestia.artemis.events.CreatePlayer
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.artemis.getSystem
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.player.logic.systems.PlayerCreation

abstract class PlayerTester(config: WorldConfigurationBuilder) : GameTest(config.with(PlayerCreation())) {

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