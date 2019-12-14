package worlds.gregs.hestia.core.entity.item.floor.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

/**
 * A public entity, seen by everyone, potentially owned by someone.
 * @param owner The owner of the item, -1 for nobody
 * @param timeout The timeout before the item stops being public (deleted)
 */
@PooledWeaver
class Public(var owner: Int = -1, var timeout: Int = 0) : Component()