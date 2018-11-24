package worlds.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.update.Marker
import java.util.concurrent.ConcurrentLinkedQueue

@PooledWeaver
class Damage : Component() {
    val hits = ConcurrentLinkedQueue<Marker>()
}