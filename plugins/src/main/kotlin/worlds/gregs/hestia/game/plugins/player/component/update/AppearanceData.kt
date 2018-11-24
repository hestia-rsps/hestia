package worlds.gregs.hestia.game.plugins.player.component.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class AppearanceData : Component() {
    var hash: ByteArray? = null
    var data: ByteArray? = null
}