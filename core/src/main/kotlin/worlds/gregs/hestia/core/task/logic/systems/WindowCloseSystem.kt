package worlds.gregs.hestia.core.task.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.model.events.WindowClosed
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.WindowClose
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

/**
 * A [Task] suspension which waits for a window to close before resuming
 */
class WindowCloseSystem : PassiveSystem() {

    private lateinit var tasks: Tasks
    private lateinit var windows: Windows

    @Subscribe
    private fun handle(event: WindowClosed) {
        val (window) = event
        val suspension = tasks.getSuspension(event.entity)
        if(suspension is WindowClose) {
            if(suspension.window == window) {
                tasks.resume(event.entity, suspension, Unit)
            }
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (entityId, suspension) = event
        if(suspension is WindowClose) {
            val window = suspension.window
            //If no screen open skip
            if(!windows.hasWindow(entityId, window)) {
                tasks.resume(entityId, suspension, Unit)
            }
            event.isCancelled = true
        }
    }

}