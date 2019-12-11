package worlds.gregs.hestia.core.plugins.task.components

import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.task.TaskScope

internal class TaskQueueTest {

    @Test
    fun `Clear ends before removing`() {
        //Given
        val queue = TaskQueue()
        val task: TaskScope = mockk(relaxed = true)
        queue.queue.add(task)
        //When
        queue.clear()
        //Then
        coVerify { task.stop(false) }
    }
}