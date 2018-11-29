package worlds.gregs.hestia.game.plugins.region.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.map.GameObject

@PooledWeaver
class Objects : Component() {
    var spawnedObjects: MutableList<GameObject>? = null
    var removedObjects: MutableList<GameObject>? = null
    var objects: Array<Array<Array<Array<GameObject?>?>>>? = null
}