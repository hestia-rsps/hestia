package worlds.gregs.hestia.core.plugins.queue.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.queue.SuspendingCoroutine
import java.util.*

@PooledWeaver
class SuspendingQueue : Component() {

    val queue = LinkedList<SuspendingCoroutine<*>>()

}