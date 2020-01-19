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
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.dialogue.model.events.StringEntered
import worlds.gregs.hestia.core.display.dialogue.model.type.StringEntry
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.network.client.encoders.messages.Script

@ExtendWith(MockKExtension::class)
internal class StringEntrySystemTest : MockkGame() {

    @SpyK
    private var system = StringEntrySystem()

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    @RelaxedMockK
    lateinit var tasks: Tasks

    override fun config(config: WorldConfigurationBuilder) {
        config.with(tasks, interfaces, system)
    }

    @Test
    fun `Send string entry`() {
        //Given
        world.createEntity().edit().add(Type().apply { id = 1 })
        //When
        es.perform(0, StringEntry("title"))
        //Then
        verify { es.send(0, Script(108, "title")) }
    }

    @Test
    fun `Handle input response`() {
        //Given
        val suspension = mockk<StringEntry>()
        every { tasks.getSuspension(0) } returns suspension
        //When
        es.perform(0, StringEntered("text"))
        //Then
        verify { tasks.resume(0, suspension, "text") }
    }
}