package worlds.gregs.hestia.core.display.dialogue.logic.systems.type

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.dialogue.logic.systems.DialogueBoxSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.StringEntryDialogue
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.StringEntryDialogueSystem
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.stringEntry
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.Script
import kotlin.coroutines.resume

@ExtendWith(MockKExtension::class)
internal class StringEntryDialogueTest : MockkGame() {

    @SpyK
    var system = StringEntryDialogueSystem()
    @SpyK
    var component = TaskQueue()

    @RelaxedMockK
    private lateinit var task: Task

    @RelaxedMockK
    private lateinit var tasks: Tasks
    private lateinit var continuation: CancellableContinuation<String>

    @BeforeEach
    override fun setup() {
        super.setup()
        every { task.suspension = any() } propertyType StringEntryDialogue::class answers {
            continuation = arg<StringEntryDialogue>(0).continuation
            continuation.resume("A string")
        }
    }

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks, DialogueBoxSystem())
    }

    @Test
    fun `String entry sets dialogue, suspends and returns value`() = runBlocking {
        //Given
        val entity = world.createEntity()
        entity.edit().add(component)
        mockkStatic("worlds.gregs.hestia.core.display.dialogue.logic.systems.types.StringEntryDialogueKt")
        //When
        val result = task.stringEntry("Title")
        //Then
        assertEquals("A string", result)
        verify {
            task.suspension = StringEntryDialogue("Title", continuation!!)
        }
        confirmVerified(task)
    }

    @Test
    fun `Suspend process ignores other types`() {
        //Given
        val suspension: TaskType<*> = mockk(relaxed = true)
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { es.send(entityId, any<Script>()) }
    }

    @Test
    fun `Suspend process sends script 108`() {
        //Given
        val suspension = StringEntryDialogue("Title", mockk(relaxed = true))
        val entityId = 0
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify { es.send(entityId, Script(108, "Title")) }
    }

    @Test
    fun `String entered ignored if wrong type`() {
        //Given
        val entityId = 0
        val suspension: TaskType<String> = mockk()
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(StringEntered("text").apply { entity = entityId })
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, "text") }
    }

    @Test
    fun `String entered`() {
        //Given
        val entityId = 0
        val suspension = spyk(StringEntryDialogue("Title", mockk(relaxed = true)))
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.dispatch(StringEntered( "text").apply { entity = entityId })
        //Then
        verifyOrder {
            tasks.resume(entityId, suspension, "text")
        }
    }
}