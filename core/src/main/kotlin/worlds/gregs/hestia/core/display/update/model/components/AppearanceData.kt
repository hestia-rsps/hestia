package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class AppearanceData : Component() {
    var hash: ByteArray? = null
    var data: ByteArray? = null
}