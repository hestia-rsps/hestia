package worlds.gregs.hestia.core.entity.`object`

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectClippingSystem
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectCreation
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectSubscriptionSystem
import worlds.gregs.hestia.game.plugin.Plugin

class ObjectPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ObjectClippingSystem(), ObjectCreation(), ObjectSubscriptionSystem())
    }

}