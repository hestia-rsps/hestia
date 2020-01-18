package worlds.gregs.hestia.core.task.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceClosed
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.InterfaceClose
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

/**
 * A [Task] suspension which waits for a interface to close before resuming
 */
class InterfaceCloseSystem : PassiveSystem() {

    private lateinit var tasks: Tasks
    private lateinit var interfaces: Interfaces

    @Subscribe
    private fun handle(event: InterfaceClosed) {
        val (id) = event
        val suspension = tasks.getSuspension(event.entity)
        if(suspension is InterfaceClose) {
            if(suspension.id == id) {
                tasks.resume(event.entity, suspension, Unit)
            }
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (entityId, suspension) = event
        if(suspension is InterfaceClose) {
            val window = suspension.id
            //If no screen open skip
            if(!interfaces.hasInterface(entityId, window)) {
                tasks.resume(entityId, suspension, Unit)
            }
            event.isCancelled = true
        }
    }

}