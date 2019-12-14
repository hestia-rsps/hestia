package worlds.gregs.hestia.core.entity.item.floor.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

/**
 * A private entity, seen only by the player with the name [id]
 * @param id The id of the entity who owns this entity
 * @param timeout The timeout before the entity stops being private (-1 for infinite)
 */
@PooledWeaver
data class Private(var id: String? = null, var timeout: Int = 0) : Component()