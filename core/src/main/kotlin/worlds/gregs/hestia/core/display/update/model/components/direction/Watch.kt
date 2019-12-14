package worlds.gregs.hestia.core.display.update.model.components.direction

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Watch(var entity: Int = -1) : Component()