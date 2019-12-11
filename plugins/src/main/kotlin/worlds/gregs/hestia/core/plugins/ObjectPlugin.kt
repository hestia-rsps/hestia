package worlds.gregs.hestia.core.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.core.plugins.`object`.systems.ObjectClippingSystem
import worlds.gregs.hestia.core.plugins.`object`.systems.ObjectCreation
import worlds.gregs.hestia.core.plugins.`object`.systems.ObjectSubscriptionSystem

class ObjectPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ObjectClippingSystem(), ObjectCreation(), ObjectSubscriptionSystem())
    }

}