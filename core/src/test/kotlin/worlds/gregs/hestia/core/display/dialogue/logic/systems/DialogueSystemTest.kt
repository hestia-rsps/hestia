package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.display.dialogue.api.Dialogue
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.task.api.SuspendableQueue
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskPriority
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.ReusableTask
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.StartTask

@ExtendWith(MockKExtension::class)
internal class DialogueSystemTest : MockkGame() {

    @SpyK
    var system = DialogueSystem()

    @RelaxedMockK
    lateinit var tasks: Tasks

    @SpyK
    var component = TaskQueue()

    @SpyK
    var queue: SuspendableQueue = {}

    @SpyK
    var boxSystem = DialogueBoxSystem()

    @RelaxedMockK
    lateinit var windows: Windows

    @RelaxedMockK
    lateinit var task: Task

    @RelaxedMockK
    lateinit var dialogue: Dialogue

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(component)
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(windows, system, boxSystem, tasks)
    }

    @Test
    fun `Add dialogue`() {
        //Given
        val id = "script"
        //When
        system.addDialogue(id, ReusableTask(TaskPriority.Low, queue))
        //Then
        assertEquals(1, system.scripts.size)
        assertTrue(system.scripts.containsKey(id))
        assertEquals(queue, system.scripts[id]!!.block)
        assertEquals(TaskPriority.Low, system.scripts[id]!!.priority)
    }

    @Test
    fun `Start dialogue dispatches`() {
        //Given
        val entityId = 0
        val name = "name"
        system.scripts[name] = ReusableTask(TaskPriority.Low, queue)
        //When
        system.startDialogue(entityId, name)
        //Then
        verify { es.dispatch(any()) }
    }

    @Test
    fun `Non existent script start is ignored`() {
        //Given
        val entityId = 0
        val name = "name"
        //When
        system.startDialogue(entityId, name)
        //Then
        verify(exactly = 0) { es.dispatch(any<StartTask>()) }
    }

}