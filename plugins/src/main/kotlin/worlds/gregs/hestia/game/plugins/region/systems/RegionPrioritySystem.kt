package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.api.region.RegionPriority
import worlds.gregs.hestia.api.region.Regions
import worlds.gregs.hestia.game.plugins.region.components.RegionPriorities

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