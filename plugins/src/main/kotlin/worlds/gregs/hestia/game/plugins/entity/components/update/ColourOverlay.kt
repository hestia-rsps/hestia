package worlds.gregs.hestia.game.plugins.entity.components.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ColourOverlay : Component() {
    var delay = 0
    var duration = 0
    var colour = 0
}

fun Entity.colour(hue: Int, sat: Int, lum: Int, multi: Int, duration: Int, delay: Int = 0) {
    val overlay = ColourOverlay()
    overlay.apply {
        this.colour = hue or (sat shl 8) or (lum shl 16) or (multi shl 24)
        this.delay = delay
        this.duration = duration
    }
    edit().add(overlay)
}