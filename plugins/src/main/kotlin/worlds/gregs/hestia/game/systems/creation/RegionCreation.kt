package worlds.gregs.hestia.game.systems.creation

import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.RegionFactory
import worlds.gregs.hestia.game.events.CreateRegion
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem

class RegionCreation : PassiveSystem() {

    @Subscribe
    fun create(event: CreateRegion) {
        val region = EntityFactory.create(RegionFactory::class)
//        region.edit().add(Bounds(64, 64))
//            return region
    }

}