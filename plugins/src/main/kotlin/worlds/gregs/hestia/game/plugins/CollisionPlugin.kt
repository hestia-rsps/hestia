package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugins.collision.systems.CollisionSystem
import worlds.gregs.hestia.game.plugins.collision.systems.EntityCollisionSystem
import worlds.gregs.hestia.game.plugins.collision.systems.ObjectCollisionSystem

class CollisionPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(CollisionSystem(), ObjectCollisionSystem(), EntityCollisionSystem())
    }

}