package worlds.gregs.hestia.core.world.region.logic.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.core.world.region.api.RegionPriority
import worlds.gregs.hestia.core.world.region.api.Regions
import worlds.gregs.hestia.core.world.region.model.components.RegionPriorities

@Wire(failOnNull = false)
class RegionPrioritySystem : RegionPriority() {

    private var regions: Regions? = null
    private lateinit var prioritiesMapper: ComponentMapper<RegionPriorities>

    override fun add(regionId: Int) {
        val entityId = regions?.getEntityId(regionId) ?: return
        val priorities = prioritiesMapper.create(entityId)
        priorities.priority++
    }

    override fun remove(regionId: Int) {
        val entityId = regions?.getEntityId(regionId) ?: return
        if(prioritiesMapper.has(entityId)) {
            val priorities = prioritiesMapper.get(entityId)
            if(priorities.priority == 1) {
                //Remove priority
                prioritiesMapper.remove(entityId)
            } else {
                priorities.priority--
            }
        }
    }
}