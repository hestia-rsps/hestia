package worlds.gregs.hestia.script

import com.artemis.Aspect
import com.artemis.World
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.action.model.Action
import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.script.ScriptBase
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskCancellation
import worlds.gregs.hestia.core.task.model.context.EntityContext
import kotlin.coroutines.Continuation
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.resume

/**
 * Class for testing scripts by mocking injections
 */
@ExtendWith(MockKExtension::class)
abstract class ScriptTester<T : ScriptBase>(aspect: Aspect.Builder? = null) : ScriptMock<T>(aspect) {

    lateinit var continuation: Continuation<Unit>
    lateinit var task: Task
    val entityId = -1

    inline fun <reified T : Action> mockkAction(): T {
        val action = mockk<T>(relaxed = true)
        if(action is EntityAction) {
            every { action.entity } returns entityId
            with(action as EntityAction) {
                every { any<Int>().perform(any()) } answers {}
            }
        }
        every { action.world } returns accessWorld
        with(action) {
            every { dispatch(any()) } answers {}
            every { any<Int>().send(any()) } answers {}
            every { perform(any()) } answers {}
        }
        return action
    }

    fun EntityAction.mockTask() {
        task = mockk(relaxed = true)
        every { task.context } returns EntityContext(world, entity)
        coEvery { task.cancel(any<TaskCancellation>()) } answers {}
        with(task) {
            every { any<Int>().send(any()) } answers {}
        }
        every { queue(any(), any()) } answers {
            val queue = arg<SuspendableQueue>(1)
            continuation = queue.createCoroutine(task, task)
            continuation.resume(Unit)
        }
    }

    fun send(action: Action) {
        action.world = world
        val listeners = script.listeners.filter { it.event == action::class }
        listeners.forEach { listener ->
            if(listener.conditional == null || listener.conditional!!.invoke(action)) {
                listener.action.invoke(action)
            }
        }
    }

    @PublishedApi
    internal var accessWorld: World
        get() = world
        set(value) {
            world = value
        }
}