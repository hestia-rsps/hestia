package worlds.gregs.hestia.core.entity.entity.model.components

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.annotations.PooledWeaver

@PooledWeaver
data class Size(var sizeX: Int = 1, var sizeY: Int = 1) : Component()

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