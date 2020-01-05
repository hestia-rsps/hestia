package worlds.gregs.hestia.core.task.logic.systems

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.model.WindowPane
import worlds.gregs.hestia.core.display.window.model.events.WindowClosed
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

data class ScreenCloseSuspension(val screen: Int?, override val continuation: CancellableContinuation<Unit>) : TaskType<Unit>

suspend fun Task.awaitScreen(screen: Int? = null) = suspendCancellableCoroutine<Unit> {
    suspension = ScreenCloseSuspension(screen, it)
}

/**
 * A [Task] suspension which waits for a screen to close before resuming
 * Note: If screen is null they it waits for any screen to close
 *       If no screen is open when called the suspension is skipped.
 */
class ScreenCloseSuspensionSystem : PassiveSystem() {

    private lateinit var tasks: Tasks
    private lateinit var windows: Windows

    @Subscribe
    private fun handle(event: WindowClosed) {
        val (entityId, screen) = event
        val suspension = tasks.getSuspension(entityId)
        if(suspension is ScreenCloseSuspension) {
            if(suspension.screen == null || suspension.screen == screen) {
                tasks.resume(entityId, suspension, Unit)
            }
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleSuspend(event: ProcessTaskSuspension) {
        val (entityId, suspension) = event
        if(suspension is ScreenCloseSuspension) {
            val screen = suspension.screen
            //If no screen open skip
            if(screen == null && !windows.hasWindow(entityId, WindowPane.MAIN_SCREEN) || screen != null && !windows.hasWindow(entityId, screen)) {
                tasks.resume(entityId, suspension, Unit)
            }
            event.isCancelled = true
        }
    }

}