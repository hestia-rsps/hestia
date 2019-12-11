package worlds.gregs.hestia.core.plugins.player.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Title() : Component() {

    constructor(title: Int) : this() {
        this.title = title
    }

    var title = -1
}