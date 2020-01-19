package worlds.gregs.hestia.core.display.dialogue.logic.systems

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.dialogue.model.events.ContinueDialogue
import worlds.gregs.hestia.core.display.dialogue.model.type.*
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ConfirmDestroy
import worlds.gregs.hestia.core.task.api.Tasks

@ExtendWith(MockKExtension::class)
internal class DialogueSystemTest : MockkGame() {

    @SpyK
    private var system = DialogueSystem()

    @RelaxedMockK
    lateinit var tasks: Tasks

    override fun config(config: WorldConfigurationBuilder) {
        config.with(tasks, system)
    }

    @Test
    fun `Destroy confirm`() {
        //Given
        val suspension = mockk<Destroy>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(ConfirmDestroy, 3, -1))
        //Then
        verify { tasks.resume(0, suspension, true) }
    }

    @Test
    fun `Destroy decline`() {
        //Given
        val suspension = mockk<Destroy>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(ConfirmDestroy, 2, -1))
        //Then
        verify { tasks.resume(0, suspension, false) }
    }

    @Test
    fun `Destroy wrong interface`() {
        //Given
        val suspension = mockk<Destroy>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(0, 3, -1))
        //Then
        verify(exactly = 0) { tasks.resume(0, suspension, true) }
    }

    @Test
    fun `Continue item box`() {
        //Given
        val suspension = mockk<ItemBox>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(0, 0, 0))
        //Then
        verify { tasks.resume(0, suspension, Unit) }
    }

    @Test
    fun `Continue mob chat`() {
        //Given
        val suspension = mockk<MobChat>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(0, 0, 0))
        //Then
        verify { tasks.resume(0, suspension, Unit) }
    }

    @Test
    fun `Continue options`() {
        //Given
        val suspension = mockk<Options>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(0, 2, 0))
        //Then
        verify { tasks.resume(0, suspension, 2) }
    }

    @Test
    fun `Continue options below minimum option`() {
        //Given
        val suspension = mockk<Options>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(0, 1, 0))
        //Then
        verify(exactly = 0) { tasks.resume(0, suspension, 1) }
    }

    @Test
    fun `Continue options above maximum option`() {
        //Given
        val suspension = mockk<Options>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(0, 7, 0))
        //Then
        verify(exactly = 0) { tasks.resume(0, suspension, 7) }
    }

    @Test
    fun `Continue player chat`() {
        //Given
        val suspension = mockk<PlayerChat>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(0, 0, 0))
        //Then
        verify { tasks.resume(0, suspension, Unit) }
    }

    @Test
    fun `Continue statement`() {
        //Given
        val suspension = mockk<Statement>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, ContinueDialogue(0, 0, 0))
        //Then
        verify { tasks.resume(0, suspension, Unit) }
    }
}