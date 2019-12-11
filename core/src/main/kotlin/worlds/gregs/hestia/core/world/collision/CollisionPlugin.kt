package worlds.gregs.hestia.core.world.collision

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.core.world.collision.systems.CollisionSystem
import worlds.gregs.hestia.core.world.collision.systems.EntityCollisionSystem
import worlds.gregs.hestia.core.world.collision.systems.ObjectCollisionSystem

class CollisionPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(CollisionSystem(), ObjectCollisionSystem(), EntityCollisionSystem())
    }

}