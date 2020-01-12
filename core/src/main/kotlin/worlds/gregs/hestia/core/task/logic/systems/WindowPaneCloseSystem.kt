package worlds.gregs.hestia.core.task.logic.systems

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.model.WindowPane
import worlds.gregs.hestia.core.display.window.model.events.WindowClosed
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.WindowPaneClose
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

/**
 * A [Task] suspension which waits for any window in a given [WindowPane] to close before resuming
 */
class WindowPaneCloseSystem : PassiveSystem() {

    private lateinit var tasks: Tasks
    private lateinit var windows: Windows

    @Subscribe
    private fun handle(event: WindowClosed) {
        val ( window) = event
        val suspension = tasks.getSuspension(event.entity)
        if(suspension is WindowPaneClose) {
            if(suspension.pane == windows.getPane(window)) {
                tasks.resume(event.entity, suspension, Unit)
            }
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (entityId, suspension) = event
        if(suspension is WindowPaneClose) {
            //If no pane open skip
            if(!windows.hasWindow(entityId, suspension.pane)) {
                tasks.resume(entityId, suspension, Unit)
            }
            event.isCancelled = true
        }
    }

}