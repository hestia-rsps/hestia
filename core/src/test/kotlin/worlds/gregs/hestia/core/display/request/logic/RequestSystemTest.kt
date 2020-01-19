package worlds.gregs.hestia.core.display.request.logic

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import world.gregs.hestia.core.network.protocol.encoders.messages.Chat
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.action.model.perform
import worlds.gregs.hestia.core.display.request.model.Request
import worlds.gregs.hestia.core.display.request.model.components.RequestList
import worlds.gregs.hestia.core.display.request.model.events.AcceptedRequest
import worlds.gregs.hestia.core.display.request.model.events.RequestResponse
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.game.Engine

@ExtendWith(MockKExtension::class)
internal class RequestSystemTest : MockkGame() {

    @SpyK
    private var system = RequestSystem()

    @SpyK
    private var component = RequestList()
    @SpyK
    private var targetComponent = RequestList()

    lateinit var entity: Entity
    lateinit var target: Entity

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system)
    }

    override fun start() {
        entity = world.createEntity()
        entity.edit().add(component).add(DisplayName().apply { name = "name" })
        target = world.createEntity()
        target.edit().add(targetComponent).add(DisplayName().apply { name = "name" })
    }

    @Test
    fun `Send request`() {
        //Given
        val request = Request.TRADE
        every { component.requests } returns mutableMapOf()
        every { system.hasRequest(any(), any(), any()) } returns false
        //When
        system.sendRequest(entity.id, target.id, request)
        //Then
        verify {
            es.send(entity.id, Chat(request.otherType, 0, null, request.sendRequest))
            es.send(target.id, Chat(request.reqType, 0, "name", request.receiveRequest))
        }
    }

    @Test
    fun `Accept immediately if target requested entity`() {
        //Given
        val request = Request.TRADE
        every { component.requests } returns mutableMapOf()
        every { system.acceptRequest(any(), any(), any()) } answers {}
        every { system.hasRequest(target.id, entity.id, any()) } returns true
        //When
        system.sendRequest(entity.id, target.id, request)
        //Then
        verify { system.acceptRequest(entity.id, target.id, request) }
    }

    @Test
    fun `Accept request`() {
        //Given
        val request = Request.TRADE
        every { component.requests } returns mutableMapOf()
        every { system.purgeRequests(any(), any(), any()) } answers {}
        //When
        system.acceptRequest(entity.id, target.id, request)
        //Then
        verify {
            es.perform(target.id, RequestResponse(entity.id, request))
            es.perform(entity.id, AcceptedRequest(target.id, request))
            system.purgeRequests(entity.id, target.id, request)
        }
    }

    @Test
    fun `Purge requests`() {
        //Given
        val request = Request.TRADE
        val targetList = mutableMapOf("name" to mutableMapOf(request to 0))
        val list = mutableMapOf("name" to mutableMapOf(request to 0))
        every { targetComponent.requests } returns targetList
        every { component.requests } returns list
        //When
        system.purgeRequests(entity.id, target.id, request)
        //Then
        assertTrue(targetList["name"]!!.isEmpty())
        assertTrue(list["name"]!!.isEmpty())
    }

    @Test
    fun `Purge doesn't remove other requests`() {
        //Given
        val request = Request.TRADE
        val targetList = mutableMapOf("other" to mutableMapOf(request to 0))
        val list = mutableMapOf("other" to mutableMapOf(request to 0))
        every { targetComponent.requests } returns targetList
        every { component.requests } returns list
        //When
        system.purgeRequests(entity.id, target.id, request)
        //Then
        assertFalse(targetList.isEmpty())
        assertFalse(list.isEmpty())
    }

    @Test
    fun `Has request`() {
        //Given
        val request = Request.TRADE
        val list = mutableMapOf("name" to mutableMapOf(request to 0))
        every { component.requests } returns list
        //When
        val result = system.hasRequest(entity.id, target.id, request)
        //Then
        assertTrue(result)
    }

    @Test
    fun `Doesn't have request`() {
        //Given
        val request = Request.TRADE
        val list = mutableMapOf("other" to mutableMapOf(request to 0))
        every { component.requests } returns list
        //When
        val result = system.hasRequest(entity.id, target.id, request)
        //Then
        assertFalse(result)
    }

    @Test
    fun `Doesn't have timed out request`() {
        //Given
        val request = Request.TRADE
        val list = mutableMapOf("name" to mutableMapOf(request to 0))
        every { component.requests } returns list
        Engine.ticks = 100
        //When
        val result = system.hasRequest(entity.id, target.id, request)
        //Then
        assertFalse(result)
    }
}