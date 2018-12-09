package worlds.gregs.hestia.game.plugins.region.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class RegionIdentifier : Component() {
    var id: Int = -1

    val x: Int
        get() = regionX * 64

    val y: Int
        get() = regionY * 64

    val chunkX: Int
        get() = regionX * 8

    val chunkY: Int
        get() = regionY * 8

    val regionX: Int
        get() = id shr 8

    val regionY: Int
        get() = id and 0xff

}