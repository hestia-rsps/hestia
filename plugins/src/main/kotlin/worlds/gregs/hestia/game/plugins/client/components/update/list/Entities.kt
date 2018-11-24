package worlds.gregs.hestia.game.plugins.client.components.update.list

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
abstract class Entities : Component() {
    var list = ArrayList<Int>()
}