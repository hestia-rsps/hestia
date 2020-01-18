package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.dialogue.logic.systems.types.*
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.task.api.TaskType
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceAnimation
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadMob
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceHeadPlayer
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceItem
import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.service.cache.definition.definitions.MobDefinition
import worlds.gregs.hestia.service.cache.definition.readers.ItemDefinitionReader
import worlds.gregs.hestia.service.cache.definition.readers.MobDefinitionReader
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem
import worlds.gregs.hestia.service.cache.definition.systems.MobDefinitionSystem

@ExtendWith(MockKExtension::class)
internal class EntityDialogueTest : MockkGame() {

    @SpyK
    var system = EntityDialogueSystem()

    @SpyK
    var boxSystem = DialogueBoxSystem()

    @RelaxedMockK
    private lateinit var tasks: Tasks

    @RelaxedMockK
    private lateinit var interfaces: Interfaces

    @RelaxedMockK
    private lateinit var itemReader: ItemDefinitionReader
    @RelaxedMockK
    private lateinit var mobReader: MobDefinitionReader

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks, boxSystem, interfaces, ItemDefinitionSystem(itemReader), MobDefinitionSystem(mobReader))
    }

    @Test
    fun `Suspension process ignores other types`() {
        //Given
        val suspension: TaskType<*> = mockk()
        val entityId = 0
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verify(exactly = 0) { system.send(entityId, any(), 3, any(), any()) }
    }

    @Test
    fun `Suspension process`() {
        //Given
        val suspension: EntityDialogue = mockk()
        val entityId = 0
        every { suspension.lines } returns emptyList()
        every { system.getTitle(entityId, suspension) } returns null
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verifyOrder {
            system.getTitle(entityId, suspension)
            system.send(entityId, any(), 3, any(), any())
        }
    }

    @Test
    fun `Item suspension`() {
        //Given
        val item = 4
        val suspension = ItemDialogue(listOf("Lines"), null, item, mockk(relaxed = true))
        val entityId = 0
        every { system.getTitle(entityId, suspension) } returns "Title"
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verifyOrder {
            system.getTitle(entityId, suspension)
            system.send(entityId,241, 3, "Title", listOf("Lines"))
            es.send(entityId, InterfaceItem(241, 2, item, -1))
        }
    }

    @Test
    fun `Mob suspension`() {
        //Given
        val mob = 5
        val animation = 6
        val suspension = MobDialogue(listOf("Line one", "Line two"), null, mob, animation, mockk(relaxed = true))
        val entityId = 0
        every { system.getTitle(entityId, suspension) } returns "Title"
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verifyOrder {
            system.getTitle(entityId, suspension)
            system.send(entityId,242, 3, "Title", listOf("Line one", "Line two"))
            es.send(entityId, InterfaceHeadMob(242, 2, mob))
            es.send(entityId, InterfaceAnimation(242, 2, animation))
        }
    }

    @Test
    fun `Player suspension`() {
        //Given
        val animation = 7
        val suspension = PlayerDialogue(listOf("Line one", "Line two", "Line three"), null, animation, mockk(relaxed = true))
        val entityId = 0
        every { system.getTitle(entityId, suspension) } returns "Title"
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        mockkStatic("worlds.gregs.hestia.artemis.ExtensionFunctionsKt")
        //When
        es.dispatch(ProcessTaskSuspension(entityId, suspension))
        //Then
        verifyOrder {
            system.getTitle(entityId, suspension)
            system.send(entityId,243, 3, "Title", listOf("Line one", "Line two", "Line three"))
            es.send(entityId, InterfaceHeadPlayer(243, 2))
            es.send(entityId, InterfaceAnimation(243, 2, animation))
        }
    }

    @Test
    fun `Item title`() {
        //Given
        val item = 995
        val dialogue = ItemDialogue(listOf("Lines"), null, item, mockk(relaxed = true))
        every { itemReader.get(item) } returns ItemDefinition().apply { name = "Coins" }
        //When
        val title = system.getTitle(0, dialogue)
        //Then
        assertEquals("Coins", title)
    }

    @Test
    fun `Mob title`() {
        //Given
        val mob = 1
        val dialogue = MobDialogue(listOf("Lines"), null, mob, -1, mockk(relaxed = true))
        every { mobReader.get(mob) } returns MobDefinition().apply { name = "Man" }
        //When
        val title = system.getTitle(0, dialogue)
        //Then
        assertEquals("Man", title)
    }

    @Test
    fun `Continue ignored if not entity dialogue`() {
        //Given
        val dialogue: TaskType<*> = mockk()
        val entityId = 0
        every { tasks.getSuspension(entityId) } returns dialogue
        //When
        es.perform(entityId, ContinueDialogue(0, 0, 0))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, dialogue, any()) }
    }

    @Test
    fun `Continue invalid button press`() {
        //Given
        val dialogue: EntityDialogue = mockk()
        val entityId = 0
        every { dialogue.lines } returns listOf("One line")
        every { tasks.getSuspension(entityId) } returns dialogue
        //When
        es.perform(entityId, ContinueDialogue(0, 25, 0))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, dialogue, Unit) }
    }

    @Test
    fun `Continue resumes with correct button press`() {
        //Given
        val dialogue: EntityDialogue = mockk()
        val entityId = 0
        every { dialogue.lines } returns listOf("One line")
        every { tasks.getSuspension(entityId) } returns dialogue
        every { boxSystem.handleContinue(any()) } answers {}
        //When
        es.perform(entityId, ContinueDialogue( 0, 5, 0))
        //Then
        verify { tasks.resume(entityId, dialogue, Unit) }
    }
}