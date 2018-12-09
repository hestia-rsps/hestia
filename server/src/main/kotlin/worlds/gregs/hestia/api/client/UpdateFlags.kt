package worlds.gregs.hestia.api.client

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.client.components.Entities
import worlds.gregs.hestia.api.client.components.EntityUpdates
import worlds.gregs.hestia.game.update.UpdateEncoder
import worlds.gregs.hestia.game.update.UpdateFlag

abstract class UpdateFlags(builder: Aspect.Builder) : IteratingSystem(builder) {
    val updateFlags = ArrayList<UpdateFlag>()

    protected fun check(entityId: Int, entityUpdates: EntityUpdates, mapper: ComponentMapper<out Entities>) {
        if (mapper.has(entityId)) {
            val entities = mapper.get(entityId).list
            check(entityUpdates, entities)
        }
    }

    protected fun check(entityUpdates: EntityUpdates, list: List<Int>) {
        list.forEach { other ->
            if (updateFlags.any { t -> t.subscription.entities.contains(other) }) {
                entityUpdates.list.add(other)
            }
        }
    }

    /**
     * Flags have to be in a very specific order to work
     */
    fun insertAfter(mask: Int, flag: UpdateFlag) {
        var index = updateFlags.indexOfFirst { it.mask == mask }
        if(index != -1) {
            index += 1
        }
        updateFlags.add(index, flag)
    }

    fun insert(vararg flags: UpdateFlag) {
        updateFlags.addAll(flags)
    }

    fun create(mask: Int, aspect: com.artemis.Aspect.Builder, unit: UpdateEncoder, added: Boolean = false): UpdateFlag {
        return UpdateFlag(mask, world.aspectSubscriptionManager.get(aspect), unit, added)
    }
}