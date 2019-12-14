package worlds.gregs.hestia.core.entity.item.floor.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Amount(var amount: Int = 0) : Component()