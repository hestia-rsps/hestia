package worlds.gregs.hestia.core.task.logic.systems

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
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceClosed
import worlds.gregs.hestia.core.task.api.Tasks
import worlds.gregs.hestia.core.task.model.await.InterfaceClose
import worlds.gregs.hestia.core.task.model.events.ProcessTaskSuspension

@ExtendWith(MockKExtension::class)
internal class InterfaceCloseSystemTest : MockkGame() {

    @SpyK
    var system = InterfaceCloseSystem()

    @RelaxedMockK
    lateinit var tasks: Tasks

    @RelaxedMockK
    lateinit var interfaces: Interfaces

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, tasks, interfaces)
    }

    @Test
    fun `Resume if interface match`() {
        //Given
        val entityId = 0
        val suspension: InterfaceClose = mockk(relaxed = true)
        every { suspension.id } returns 123
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.perform(entityId, InterfaceClosed(123))
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Skip if interface don't match`() {
        //Given
        val entityId = 0
        val suspension: InterfaceClose = mockk(relaxed = true)
        every { suspension.id } returns 123
        every { tasks.getSuspension(entityId) } returns suspension
        //When
        es.perform(entityId, InterfaceClosed(456))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Resume if interface isn't open`() {
        //Given
        val suspension: InterfaceClose = mockk(relaxed = true)
        val entityId = 0
        every { interfaces.hasInterface(entityId, any<Int>()) } returns false
        //When
        es.perform(entityId, ProcessTaskSuspension(suspension))
        //Then
        verify { tasks.resume(entityId, suspension, Unit) }
    }

    @Test
    fun `Skip if interface is open`() {
        //Given
        val suspension: InterfaceClose = mockk(relaxed = true)
        val entityId = 0
        every { interfaces.hasInterface(entityId, any<Int>()) } returns true
        //When
        es.perform(entityId, ProcessTaskSuspension(suspension))
        //Then
        verify(exactly = 0) { tasks.resume(entityId, suspension, Unit) }
    }
}