package worlds.gregs.hestia.game.plugins.region.systems.load

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.RegionFactory
import worlds.gregs.hestia.artemis.events.CreateRegion
import worlds.gregs.hestia.game.plugins.region.components.RegionIdentifier

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