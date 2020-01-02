package worlds.gregs.hestia.core.task.logic.systems

import com.artemis.Component
import com.artemis.utils.Bag
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.core.display.widget.model.components.ScreenWidget
import worlds.gregs.hestia.core.display.widget.model.events.ScreenClosed
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import kotlin.reflect.KClass

data class ScreenCloseSuspension(val screen: KClass<out ScreenWidget>?, override val continuation: CancellableContinuation<Unit>) : TaskType<Unit>

suspend fun Task.awaitScreen(screen: KClass<ScreenWidget>? = null) = suspendCancellableCoroutine<Unit> {
    suspension = ScreenCloseSuspension(screen, it)
}

/**
 * A [Task] suspension which waits for a screen to close before resuming
 * Note: If screen is null they it waits for any screen to close
 *       If no screen is open when called the suspension is skipped.
 */
class ScreenCloseSuspensionSystem : PassiveSystem() {

    private lateinit var tasks: Tasks

    @Subscribe
    private fun handle(event: ScreenClosed) {
        val (entityId, screen) = event
        val suspension = tasks.getSuspension(entityId)
        if(suspension is ScreenCloseSuspension) {
            if(suspension.screen == null || suspension.screen.isInstance(screen)) {
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
            if(screen == null && !hasScreenOpen(entityId) || screen != null && !hasScreenOpen(entityId, screen)) {
                tasks.resume(entityId, suspension, Unit)
            }
            event.isCancelled = true
        }
    }

    private val bag = Bag<Component>()
    private fun hasScreenOpen(entityId: Int) = world.componentManager.getComponentsFor(entityId, bag).any { it is ScreenWidget }
    private fun hasScreenOpen(entityId: Int, screen: KClass<out ScreenWidget>) = world.getEntity(entityId).getComponent(screen) != null

}