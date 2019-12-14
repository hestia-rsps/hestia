package worlds.gregs.hestia.core.display.client.logic.systems

import com.artemis.BaseSystem
import net.mostlyoriginal.api.event.common.Subscribe
import worlds.gregs.hestia.artemis.events.Disconnect
import java.util.*

/**
 * Groups deletions to a specific time during the cycle
 */
class DisconnectSystem : BaseSystem() {

    private val queue = LinkedList<Int>()

    override fun processSystem() {
        while(queue.size > 0) {
            world.delete(queue.poll())
        }
    }

    @Subscribe
    fun event(event: Disconnect) {
        queue.add(event.entityId)
    }

}