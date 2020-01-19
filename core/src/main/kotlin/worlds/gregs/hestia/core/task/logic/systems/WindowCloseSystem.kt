package worlds.gregs.hestia.core.task.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceClosed
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.WindowClose
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

/**
 * A [Task] suspension which waits for any window in a given [Window] to close before resuming
 */
class WindowCloseSystem : PassiveSystem() {

    private lateinit var tasks: Tasks
    private lateinit var interfaces: Interfaces

    @Subscribe
    private fun handle(event: InterfaceClosed) {
        val suspension = tasks.getSuspension(event.entity)
        if(suspension is WindowClose) {
            if(suspension.window == interfaces.getWindow(event.id)) {
                tasks.resume(event.entity, suspension, Unit)
            }
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (suspension) = event
        if(suspension is WindowClose) {
            //If no pane open skip
            if(!interfaces.hasInterface(event.entity, suspension.window)) {
                tasks.resume(event.entity, suspension, Unit)
            }
            event.isCancelled = true
        }
    }

}