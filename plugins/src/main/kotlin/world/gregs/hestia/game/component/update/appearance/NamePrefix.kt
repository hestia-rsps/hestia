package world.gregs.hestia.game.component.update.appearance

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class NamePrefix : Component() {
    var prefix: String? = ""
}