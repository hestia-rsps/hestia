package worlds.gregs.hestia.core.entity.player.model.components.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class HeadIcon() : Component() {

    constructor(headIcon: Int) : this() {
        this.headIcon = headIcon
    }

    var headIcon = -1
}