package worlds.gregs.hestia.core.display.dialogue.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.events.ProcessDeferral
import worlds.gregs.hestia.artemis.events.ContinueDialogue
import worlds.gregs.hestia.core.display.dialogue.components.Dialogue
import worlds.gregs.hestia.core.display.dialogue.systems.types.ItemDialogue
import worlds.gregs.hestia.core.display.dialogue.systems.types.MobDialogue
import worlds.gregs.hestia.core.display.dialogue.systems.types.PlayerDialogue
import worlds.gregs.hestia.core.display.widget.systems.frame.chat.DialogueBoxSystem
import worlds.gregs.hestia.game.MockkGameTest
import worlds.gregs.hestia.game.task.DeferralType
import worlds.gregs.hestia.game.task.TaskScope
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentAnimation
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadMob
import worlds.gregs.hestia.network.client.encoders.messages.WidgetHeadPlayer
import worlds.gregs.hestia.network.client.encoders.messages.WidgetItem
import worlds.gregs.hestia.services.send

@ExtendWith(MockKExtension::class)
internal class EntityDialogueTest : MockkGameTest() {

    @SpyK
    var system = EntityDialogueSystem()

    @RelaxedMockK
    private lateinit var scope: TaskScope

    @RelaxedMockK
    private lateinit var queue: Tasks

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, queue, DialogueBoxSystem())
    }

    @Test
    fun `Deferral process ignores other types`() {
        //Given
        val deferral: DeferralType = mockk()
        val entityId = 0
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verify(exactly = 0) { system.send(entityId, any(), 3, any(), any()) }
    }

    @Test
    fun `Deferral process`() {
        //Given
        val deferral: EntityDialogue = mockk()
        val entityId = 0
        every { deferral.lines } returns emptyList()
        every { system.getTitle(deferral) } returns null
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verifyOrder {
            system.getTitle(deferral)
            system.send(entityId, any(), 3, any(), any())
        }
    }

    @Test
    fun `Item deferral`() {
        //Given
        val item = 4
        val deferral = ItemDialogue(listOf("Lines"), null, item)
        val entityId = 0
        every { system.getTitle(deferral) } returns "Title"
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        mockkStatic("worlds.gregs.hestia.services.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verifyOrder {
            system.getTitle(deferral)
            system.send(entityId,241, 3, "Title", listOf("Lines"))
            es.send(entityId, WidgetItem(241, 2, item, -1))
        }
    }

    @Test
    fun `Mob deferral`() {
        //Given
        val mob = 5
        val animation = 6
        val deferral = MobDialogue(listOf("Line one", "Line two"), null, mob, animation)
        val entityId = 0
        every { system.getTitle(deferral) } returns "Title"
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        mockkStatic("worlds.gregs.hestia.services.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verifyOrder {
            system.getTitle(deferral)
            system.send(entityId,242, 3, "Title", listOf("Line one", "Line two"))
            es.send(entityId, WidgetHeadMob(242, 2, mob))
            es.send(entityId, WidgetComponentAnimation(242, 2, animation))
        }
    }

    @Test
    fun `Player deferral`() {
        //Given
        val animation = 7
        val deferral = PlayerDialogue(listOf("Line one", "Line two", "Line three"), null, animation)
        val entityId = 0
        every { system.getTitle(deferral) } returns "Title"
        every { system.send(entityId, any(), any(), any(), any()) } answers {}
        mockkStatic("worlds.gregs.hestia.services.GameExtensionsKt")
        //When
        es.dispatch(ProcessDeferral(entityId, deferral))
        //Then
        verifyOrder {
            system.getTitle(deferral)
            system.send(entityId,243, 3, "Title", listOf("Line one", "Line two", "Line three"))
            es.send(entityId, WidgetHeadPlayer(243, 2))
            es.send(entityId, WidgetComponentAnimation(243, 2, animation))
        }
    }

    @Test
    fun `Item title`() {
        //Given
        val item = 995
        val dialogue = ItemDialogue(listOf("Lines"), null, item)
        //When
        val title = system.getTitle(dialogue)//TODO implement ItemDefinitions
        //Then
//        verify { cache.getItemDefinitions(mob).name }
//        assertEquals("Coins", title)
    }

    @Test
    fun `Mob title`() {
        //Given
        val mob = 1
        val dialogue = MobDialogue(listOf("Lines"), null, mob, -1)
        //When
        val title = system.getTitle(dialogue)//TODO implement MobDefinitions
        //Then
//        verify { cache.getMobDefinitions(mob).name }
//        assertEquals("Man", title)
    }

    @Test
    fun `Continue ignored if not entity dialogue`() {
        //Given
        val dialogue: Dialogue = mockk()
        val entityId = 0
        every { system.getDeferral(entityId) } returns dialogue
        //When
        es.dispatch(ContinueDialogue(entityId, 0, 0, 0))
        //Then
        verify(exactly = 0) { queue.resume(entityId) }
    }

    @Test
    fun `Continue invalid button press`() {
        //Given
        val dialogue: EntityDialogue = mockk()
        val entityId = 0
        every { dialogue.lines } returns listOf("One line")
        every { system.getDeferral(entityId) } returns dialogue
        //When
        es.dispatch(ContinueDialogue(entityId, 0, 25, 0))
        //Then
        verify(exactly = 0) { queue.resume(entityId) }
    }

    @Test
    fun `Continue resumes with correct button press`() {
        //Given
        val dialogue: EntityDialogue = mockk()
        val entityId = 0
        every { dialogue.lines } returns listOf("One line")
        every { system.getDeferral(entityId) } returns dialogue
        //When
        es.dispatch(ContinueDialogue(entityId, 0, 5, 0))
        //Then
        verify { queue.resume(entityId) }
    }
}