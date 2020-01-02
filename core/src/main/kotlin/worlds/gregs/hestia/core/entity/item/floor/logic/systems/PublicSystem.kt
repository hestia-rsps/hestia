package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.exclude
import worlds.gregs.hestia.core.entity.item.floor.model.components.Private
import worlds.gregs.hestia.core.entity.item.floor.model.components.Public

class PublicSystem : IteratingSystem(Aspect.all(Public::class).exclude(Private::class)) {

    private lateinit var publicMapper: ComponentMapper<Public>

    override fun process(entityId: Int) {
        val public = publicMapper.get(entityId)
        when {
            public.timeout > 0 -> public.timeout--//Decrement count
            //On timeout
            public.timeout == 0 -> {
                //Delete floor item
                world.delete(entityId)
            }
        }
    }
}