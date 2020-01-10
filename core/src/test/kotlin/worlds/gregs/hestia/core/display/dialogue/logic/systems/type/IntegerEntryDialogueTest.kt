package worlds.gregs.hestia.core.display.dialogue.logic.systems.type

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueBoxSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.IntegerEntryDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.IntegerEntryDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.intEntry
import worlds.gregs.hestia.core.display.dialogue.model.events.IntegerEntered
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.TaskContinuation
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.Script
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class IntegerEntryDialogueTest : MockkGame() {

    @SpyK
    var system = IntegerEntryDialogueSystem()

    @RelaxedMockK
    private lateinit var task: TaskContinuation

    @RelaxedMockK
    private lateinit var tasks: Tasks

    @RelaxedMockK
    private lateinit var windows: Windows

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks, DialogueBoxSystem(), windows)
    }

    @Test
    fun `Int entry sets dialogue, suspends and returns value`() = runBlocking {
        //Given
        var continuation: CancellableContinuation<Int>? = null
        every { task.suspension = any() } propertyType IntegerEntryDialogue::class answers {
            continuation = arg<IntegerEntryDialogue>(0).continuation
            continuation!!.resume(4)
        }
        //When
        val result = task.intEntry("Title")
        //Then
        assertEquals(4, result)
        coVerify {
            task.suspension = IntegerEntryDialogue("Title", continuation!!)
        }
        confirmVerified(task)
    }

    @Test
    fun `Suspend process ignores other types`() {
        //Given
        val suspension: TaskType<*> = mockk()
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { es.send(entityId, any<Script>()) }
    }

    @Test
    fun `Suspend process sends script 109`() {
        //Given
        val suspension = IntegerEntryDialogue("Title", mockk(relaxed = true))
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify { es.send(entityId, Script(109, "Title")) }
    }

    @Test
    fun `Integer entered ignored if wrong type`() {
        //Given
        val entityId = 0
        val suspension: TaskType<Unit> = mockk()
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(IntegerEntered(entityId, 1))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Integer entered`() {
        //Given
        val entityId = 0
        val suspension = spyk(IntegerEntryDialogue("Title", mockk()))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(IntegerEntered(entityId, 1))
        //Then
        verifyOrder {
            tasks.resume(entityId, suspension, 1)
        }
    }
}