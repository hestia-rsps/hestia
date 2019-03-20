package worlds.gregs.hestia.game.entity

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Size() : Component() {

    constructor(sizeX: Int, sizeY: Int) : this() {
        this.sizeX = sizeX
        this.sizeY = sizeY
    }

    var sizeX = 1
    var sizeY = 1
}

fun ComponentMapper<Size>.width(entityId: Int): Int {
    return if(has(entityId)) {
        get(entityId).sizeX
    } else {
        1
    }
}

fun ComponentMapper<Size>.height(entityId: Int): Int {
    return if(has(entityId)) {
        get(entityId).sizeY
    } else {
        1
    }
}