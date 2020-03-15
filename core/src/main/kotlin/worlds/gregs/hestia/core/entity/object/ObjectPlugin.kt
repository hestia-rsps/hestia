package worlds.gregs.hestia.core.entity.`object`

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectCollisionLoadingSystem
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectCreation
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectSubscriptionSystem
import worlds.gregs.hestia.game.plugin.Plugin

class ObjectPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ObjectCollisionLoadingSystem(), ObjectCreation(), ObjectSubscriptionSystem())
    }

}