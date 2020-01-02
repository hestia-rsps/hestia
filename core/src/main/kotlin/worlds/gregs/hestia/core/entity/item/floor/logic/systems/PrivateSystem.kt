package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.entity.item.floor.model.components.Private
import worlds.gregs.hestia.core.entity.item.floor.model.components.Public

class PrivateSystem : IteratingSystem(Aspect.all(Private::class)) {

    private lateinit var publicMapper: ComponentMapper<Public>
    private lateinit var privateMapper: ComponentMapper<Private>
    private lateinit var floorItems: FloorItems

    override fun process(entityId: Int) {
        val private = privateMapper.get(entityId)
        when {
            private.timeout > 0 -> private.timeout--//Decrement count
            //On timeout
            private.timeout == 0 -> {
                //If turn public
                if(publicMapper.has(entityId)) {
                    //Don't delete; just make public
                    privateMapper.get(entityId).id = null
                    floorItems.sendFloorItem(entityId)
                    privateMapper.remove(entityId)
                } else {
                    //Delete floor item
                    world.delete(entityId)
                }
            }
        }
    }
}