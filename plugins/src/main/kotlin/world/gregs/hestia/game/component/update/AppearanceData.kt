package world.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class AppearanceData : Component() {
    var hash: ByteArray? = null
    var data: ByteArray? = null
}