package worlds.gregs.hestia.core.world.region.logic.systems.load

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.world.region.logic.RegionFactory
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier
import worlds.gregs.hestia.core.world.region.model.events.CreateRegion
import worlds.gregs.hestia.core.entity.entity.logic.EntityFactory

class RegionCreation : PassiveSystem() {

    private lateinit var regionMapper: ComponentMapper<RegionIdentifier>

    @Subscribe
    fun create(event: CreateRegion): Int {
        val entityId = EntityFactory.create(RegionFactory::class)
        val region = regionMapper.get(entityId)
        region.id = event.id
        return entityId
    }
}