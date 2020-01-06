package worlds.gregs.hestia.game.queue

import com.artemis.systems.DelayedIteratingSystem
import worlds.gregs.hestia.services.Aspect
import java.util.*

class QueueSystem : DelayedIteratingSystem(Aspect.all(SuspendingQueue::class)) {

    var count = 600f
    override fun processDelta(entityId: Int, accumulatedDelta: Float) {
        println("Process $entityId $accumulatedDelta")
        count -= accumulatedDelta
    }

    override fun getRemainingDelay(entityId: Int): Float {
        return count
    }

    override fun processExpired(entityId: Int) {
        println("Expired $entityId")
        count = 600f
        offerDelay(count)
    }

    override fun removed(entityId: Int) {
        super.removed(entityId)
        println("Removed $entityId")
    }

    val queue = LinkedList<SuspendingCoroutine>()
}