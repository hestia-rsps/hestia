package worlds.gregs.hestia.game.plugins.client.systems.update.bases.flag

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.api.client.components.Entities
import worlds.gregs.hestia.api.client.components.EntityUpdates
import worlds.gregs.hestia.api.core.components.Viewport
import worlds.gregs.hestia.game.update.UpdateEncoder
import worlds.gregs.hestia.game.update.UpdateFlag
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.one
import kotlin.reflect.KClass

abstract class BaseUpdateFlagSystem(vararg classes: KClass<out Component>) : IteratingSystem(Aspect.all(NetworkSession::class, Viewport::class).one(*classes)) {

    val updateFlags = ArrayList<UpdateFlag>()

    internal fun check(entityId: Int, entityUpdates: EntityUpdates, mapper: ComponentMapper<out Entities>) {
        if (mapper.has(entityId)) {
            val entities = mapper.get(entityId).list
            check(entityUpdates, entities)
        }
    }

    internal fun check(entityUpdates: EntityUpdates, list: List<Int>) {
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