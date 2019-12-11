package worlds.gregs.hestia.core.plugins.dialogue.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.api.task.Task
import worlds.gregs.hestia.api.task.Tasks
import worlds.gregs.hestia.api.task.events.ProcessDeferral
import worlds.gregs.hestia.api.task.events.StartTask
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.artemis.events.CloseDialogue
import worlds.gregs.hestia.core.plugins.dialogue.components.Dialogue
import worlds.gregs.hestia.core.plugins.task.components.TaskQueue
import worlds.gregs.hestia.core.plugins.widget.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.game.MockkGameTest
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskPriority
import worlds.gregs.hestia.game.task.TaskScope

@ExtendWith(MockKExtension::class)
internal class DialogueSystemTest : MockkGameTest() {

    @SpyK
    var system = DialogueSystem()

    @RelaxedMockK
    lateinit var taskQueue: Tasks

    @SpyK
    var component = TaskQueue()

    @SpyK
    var task: Task = Task(TaskPriority.Weak) {}

    @SpyK
    var boxSystem = DialogueBoxSystem()

    @RelaxedMockK
    lateinit var ui: UserInterface

    @RelaxedMockK
    lateinit var scope: TaskScope

    @RelaxedMockK
    lateinit var dialogue: Dialogue

    @BeforeEach
    override fun setup() {
        super.setup()
        world.createEntity().edit().add(component)
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(ui, system, boxSystem, taskQueue)
    }

    @Test
    fun `Add dialogue`() {
        //Given
        val id = "script"
        //When
        system.addDialogue(id, task)
        //Then
        assertEquals(1, system.scripts.size)
        assertTrue(system.scripts.containsKey(id))
        assertEquals(task, system.scripts[id])
    }

    @Test
    fun `Start dialogue dispatches`() {
        //Given
        val entityId = 0
        val name = "name"
        system.scripts[name] = task
        //When
        system.startDialogue(entityId, name)
        //Then
        verify { es.dispatch(StartTask(entityId, task)) }
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

    @Test
    fun `Dialogue close called if deferral isn't a dialogue`() {
        //Given
        val entityId = 0
        val deferral: DeferralType = mockk()
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify { es.dispatch(CloseDialogue(entityId)) }
    }

    @Test
    fun `Dialogue close not called if deferral is a dialogue`() {
        //Given
        val entityId = 0
        val deferral: Dialogue = mockk()
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify(exactly = 0) { es.dispatch(CloseDialogue(entityId)) }
    }

}