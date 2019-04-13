package worlds.gregs.hestia.api.client.update.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.client.update.block.Marker
import java.util.concurrent.ConcurrentLinkedQueue

@PooledWeaver
class Damage : Component() {
    val hits = ConcurrentLinkedQueue<Marker>()
}