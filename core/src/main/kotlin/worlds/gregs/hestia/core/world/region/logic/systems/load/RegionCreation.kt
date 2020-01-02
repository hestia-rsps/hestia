package worlds.gregs.hestia.core.world.region.logic.systems.load

import com.artemis.Archetype
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.world.region.logic.RegionFactory
import worlds.gregs.hestia.core.world.region.model.components.RegionIdentifier
import worlds.gregs.hestia.core.world.region.model.events.CreateRegion

class RegionCreation : PassiveSystem() {

    private lateinit var regionMapper: ComponentMapper<RegionIdentifier>
    private lateinit var archetype: Archetype

    override fun initialize() {
        archetype = RegionFactory().getBuilder().build(world)
    }

    @Subscribe
    fun create(event: CreateRegion): Int {
        val entityId = world.create(archetype)
        val region = regionMapper.get(entityId)
        region.id = event.id
        return entityId
    }
}