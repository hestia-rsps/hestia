package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.display.interfaces.model.Window
import worlds.gregs.hestia.core.task.api.TaskSuspension

data class WindowClose(val window: Window = Window.MAIN_SCREEN) : TaskSuspension<Unit> {
    override lateinit var continuation: CancellableContinuation<Unit>
}