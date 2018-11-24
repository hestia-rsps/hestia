package worlds.gregs.hestia.game.component.update.gfx

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
open class Graphics: Component() {

    var id = -1

    var delay = 0

    var height = 0
    /*
        Value 0..7

        3   4   5
        2   P   6
        1   0   7
     */
    var rotation = 0

    /*
        Render animation 3d transform index/slot
        Client doesn't have any uses so disabled
        Value between 0 - 15
     */
//    var slot = 0

    var forceRefresh = false
}