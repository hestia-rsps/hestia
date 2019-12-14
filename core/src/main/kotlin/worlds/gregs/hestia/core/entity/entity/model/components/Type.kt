package worlds.gregs.hestia.core.entity.entity.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Type(var id: Int = -1) : Component()