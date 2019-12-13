package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class Title() : Component() {

    constructor(title: Int) : this() {
        this.title = title
    }

    var title = -1
}