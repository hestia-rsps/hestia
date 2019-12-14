package worlds.gregs.hestia.core.entity.item.floor.logic.creation

import com.artemis.WorldConfigurationBuilder
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.Private
import worlds.gregs.hestia.core.entity.item.floor.model.components.Public
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItem

@ExtendWith(MockKExtension::class)
internal class FloorItemCreationTest : MockkGame() {

    private val creation = FloorItemCreation()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(creation)
    }

    @Test
    fun `Create default values`() {
        //Given
        val event = CreateFloorItem(4, 5, 15, 10, 2, null, null, null, null)
        //When
        val entityId = creation.create(event)
        //Then
        val entity = world.getEntity(entityId)
        val type = entity.getComponent(Type::class)
        val amount = entity.getComponent(Amount::class)
        val position = entity.getComponent(Position::class)

        assertNotNull(type)
        assertNotNull(amount)
        assertNotNull(position)
        assertEquals(4, type!!.id)
        assertEquals(5, amount!!.amount)
        assertEquals(15, position!!.x)
        assertEquals(10, position.y)
        assertEquals(2, position.plane)
    }

    @Test
    fun `Create public values`() {
        //Given
        val event = CreateFloorItem(0, 0, 0, 0,0, 2, 10)
        //When
        val entityId = creation.create(event)
        //Then
        val entity = world.getEntity(entityId)
        val public = entity.getComponent(Public::class)

        assertNotNull(public)
        assertEquals(2, public!!.owner)
        assertEquals(10, public.timeout)
    }

    @Test
    fun `Create private values`() {
        //Given
        val event = CreateFloorItem(0, 0, 0, 0,0, "name", 10)
        //When
        val entityId = creation.create(event)
        //Then
        val entity = world.getEntity(entityId)
        val private = entity.getComponent(Private::class)

        assertNotNull(private)
        assertEquals("name", private!!.id)
        assertEquals(10, private.timeout)
    }

    @Test
    fun `Create private and public values`() {
        //Given
        val event = CreateFloorItem(0, 0, 0, 0,0, "name", 10, 2, 15)
        //When
        val entityId = creation.create(event)
        //Then
        val entity = world.getEntity(entityId)
        val private = entity.getComponent(Private::class)
        val public = entity.getComponent(Public::class)

        assertNotNull(public)
        assertNotNull(private)
        assertEquals("name", private!!.id)
        assertEquals(10, private.timeout)
        assertEquals(2, public!!.owner)
        assertEquals(15, public.timeout)
    }
}