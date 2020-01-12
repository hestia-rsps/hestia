package worlds.gregs.hestia.core.task.model.await

import kotlinx.coroutines.CancellableContinuation
import worlds.gregs.hestia.core.display.window.model.WindowPane
import worlds.gregs.hestia.core.task.api.TaskType

data class WindowPaneClose(val pane: WindowPane = WindowPane.MAIN_SCREEN) : TaskType<Unit> {
    override lateinit var continuation: CancellableContinuation<Unit>
}