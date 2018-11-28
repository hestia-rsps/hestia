package worlds.gregs.hestia.game.plugins.region.systems

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.events.CreateRegion
import worlds.gregs.hestia.game.archetypes.RegionFactory

class RegionCreation : PassiveSystem() {

    @Subscribe
    fun create(event: CreateRegion) {
        val region = EntityFactory.create(RegionFactory::class)
//        region.edit().add(Bounds(64, 64))
//            return region
    }

}