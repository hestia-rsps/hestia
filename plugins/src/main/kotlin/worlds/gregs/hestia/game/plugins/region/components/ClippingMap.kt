package worlds.gregs.hestia.game.plugins.region.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
abstract class ClippingMap : Component() {
    val masks by lazy { Array(4) { Array(64) { IntArray(64) } } }
}