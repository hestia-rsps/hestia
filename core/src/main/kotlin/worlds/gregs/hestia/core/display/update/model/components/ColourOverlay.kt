package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class ColourOverlay(var delay: Int = 0, var duration: Int = 0, var colour: Int = 0) : Component()