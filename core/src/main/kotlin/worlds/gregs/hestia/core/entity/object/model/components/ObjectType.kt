package worlds.gregs.hestia.core.entity.`object`.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class ObjectType(var type: Int = -1) : Component()