package worlds.gregs.hestia.core.entity.`object`

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.entity.`object`.logic.ObjectFactory
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectClippingSystem
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectCreation
import worlds.gregs.hestia.core.entity.`object`.logic.systems.ObjectSubscriptionSystem
import worlds.gregs.hestia.core.entity.bot.logic.BotFactory
import worlds.gregs.hestia.core.entity.mob.logic.MobFactory
import worlds.gregs.hestia.core.entity.player.logic.PlayerFactory
import worlds.gregs.hestia.core.world.region.logic.RegionFactory
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.plugin.Plugin

class ObjectPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(ObjectClippingSystem(), ObjectCreation(), ObjectSubscriptionSystem())
    }

}