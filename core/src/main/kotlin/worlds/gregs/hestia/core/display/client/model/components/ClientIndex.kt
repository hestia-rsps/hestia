package worlds.gregs.hestia.core.display.client.model.components

import com.artemis.Component
import com.artemis.annotations.DelayedComponentRemoval
import com.artemis.annotations.PooledWeaver

@PooledWeaver
@DelayedComponentRemoval
data class ClientIndex(var index: Int = 0, var remove: Boolean = false) : Component()