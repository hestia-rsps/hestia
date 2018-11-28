package worlds.gregs.hestia.game.plugins.client.components.update.stage

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.update.DisplayFlag

@PooledWeaver
class EntityUpdates : Component() {
    val map = HashMap<Int, DisplayFlag>()
    val list = ArrayList<Int>()
}