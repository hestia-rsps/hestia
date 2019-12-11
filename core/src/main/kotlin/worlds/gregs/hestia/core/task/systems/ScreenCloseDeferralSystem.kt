package worlds.gregs.hestia.core.task.systems

import com.artemis.Component
import com.artemis.ComponentMapper
import com.artemis.utils.Bag
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.widget.components.ScreenWidget
import worlds.gregs.hestia.core.display.widget.model.events.ScreenClosed
import worlds.gregs.hestia.core.getComponent
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.components.getDeferral
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope
import kotlin.reflect.KClass

data class ScreenClose(val screen: KClass<out ScreenWidget>?) : DeferralType

suspend fun TaskScope.waitForScreen(screen: KClass<ScreenWidget>? = null) {
    deferral = ScreenClose(screen)
    defer()
}

/**
 * A defer which waits for a screen to close before resuming
 * Note: If screen is null they it waits for any screen to close
 *       If no screen is open when called the deferral is skipped.
 */
class ScreenCloseDeferralSystem : PassiveSystem() {

    private lateinit var taskQueueMapper: ComponentMapper<TaskQueue>

    private lateinit var taskQueue: Tasks

    @Subscribe
    private fun handle(event: ScreenClosed) {
        val (entityId, screen) = event
        val deferral = taskQueueMapper.getDeferral(entityId)
        if(deferral is ScreenClose) {
            if(deferral.screen == null || deferral.screen.isInstance(screen)) {
                taskQueue.resume(entityId)
            }
        }
    }

    @Subscribe(ignoreCancelledEvents = true)
    private fun handleDefer(event: ProcessDeferral) {
        val (entityId, deferral) = event
        if(deferral is ScreenClose) {
            val screen = deferral.screen
            //If no screen open skip
            if(screen == null && !hasScreenOpen(entityId) || screen != null && !hasScreenOpen(entityId, screen)) {
                taskQueue.resume(entityId)
            }
            event.isCancelled = true
        }
    }

    private val bag = Bag<Component>()
    private fun hasScreenOpen(entityId: Int) = world.componentManager.getComponentsFor(entityId, bag).any { it is ScreenWidget }
    private fun hasScreenOpen(entityId: Int, screen: KClass<out ScreenWidget>) = world.getEntity(entityId).getComponent(screen) != null

}