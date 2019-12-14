package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class TimeBar(var full: Boolean = false, var exponentialDelay: Int = 0, var delay: Int = 0, var increment: Int = 0) : Component()