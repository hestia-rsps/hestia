package worlds.gregs.hestia.core.entity.item.floor.logic.systems

import com.artemis.Entity
import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import world.gregs.hestia.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.artemis.remove
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.components.LastLoadedRegion
import worlds.gregs.hestia.core.display.client.model.components.NetworkSession
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.api.FloorItems
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.Private
import worlds.gregs.hestia.core.entity.item.floor.model.components.Public
import worlds.gregs.hestia.network.client.encoders.messages.*
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

@ExtendWith(MockKExtension::class)
internal class FloorItemSystemTest : MockkGame() {

    @SpyK
    private var system = FloorItemSystem()

    @RelaxedMockK
    lateinit var floorItems: FloorItems

    @SpyK
    var definitions = ItemDefinitionSystem(mockk())

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system, floorItems, definitions)
    }

    override fun start() {
    }

    @Test
    fun `Insert calls send`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 0, 0, 0)
        //When
        tick()
        //Then
        verify { system.sendFloorItem(item.id)  }
    }

    @Test
    fun `Removed calls remove if exists`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        every { system.removeFloorItem(any()) } answers {}
        val item = createItem(5, 10, 0, 0, 0)
        tick()
        item.deleteFromWorld()
        //When
        tick()
        //Then
        verify { system.removeFloorItem(item.id)  }
    }

    @Test
    fun `Removed doesn't call remove if non-existent`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        every { system.removeFloorItem(any()) } answers {}
        val item = createItem(5, 10, 0, 0, 0)
        tick()
        item.getComponent(Position::class)!!.x += 10//Illegally move out of chunk
        item.deleteFromWorld()
        //When
        tick()
        //Then
        verify(exactly = 0) { system.removeFloorItem(any())  }
    }

    @Test
    fun `Send doesn't send if combined`() {
        //Given
        every { system.combinedItems(any()) } returns true
        //When
        system.sendFloorItem(1)
        //Then
        verify(exactly = 0) { system.sendFloorItem(any(), any(), any(), any(), any(), any())  }
    }

    @Test
    fun `Send sends to locals`() {
        //Given
        every { system.sendFloorItem(any(), any(), any(), any(), any(), any()) } answers {}
        every { system.combinedItems(any()) } returns false
        createView(0, 0, 0, "name", 0)
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(0)
        }
        //When
        system.sendFloorItem(1)
        //Then
        verify {
            system.forAllLocals(any(), any())
            system.sendFloorItem(0, any(), "name", 0, 1, true)
        }
    }

    @Test
    fun `Update amount`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 0)
        val view = createView(17, 17, 0, "name", 0)
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        tick()
        //When
        system.updateFloorItem(item.id, 15)
        //Then
        val amount = item.getComponent(Amount::class)
        assertNotNull(amount)
        assertEquals(15, amount!!.amount)
        verify {
            es.send(view.id, FloorItemUpdate(34, 5, 10, 15))
        }
    }

    @Test
    fun `Remove sends chunk & remove packets`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 0)
        val view = createView(17, 17, 0, "name", 0)
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        tick()
        //When
        system.removeFloorItem(item.id)
        //Then
        verify {
            es.send(view.id, ChunkSend(8,8, 0))
            es.send(view.id, FloorItemRemove(5, 34))
        }
    }

    @Test
    fun `For all locals sends to locals`() {
        //Given
        val action: (Int) -> Unit = mockk(relaxed = true)
        val item = world.createEntity()
        item.edit().add(Position(20, 20, 0))
        val view = world.createEntity()
        view.edit().add(NetworkSession()).add(Position(22, 22, 0))
        tick()
        //When
        system.forAllLocals(0, action)
        //Then
        verify {
            action.invoke(1)
        }
    }

    @Test
    fun `For all locals doesn't sends to entities outside of view`() {
        //Given
        val action: (Int) -> Unit = mockk(relaxed = true)
        val item = world.createEntity()
        item.edit().add(Position(33, 33, 0))
        val view = world.createEntity()
        view.edit().add(Position(7, 7, 0))
        tick()
        //When
        system.forAllLocals(0, action)
        //Then
        verify(exactly = 0) {
            action.invoke(any())
        }
    }

    @Test
    fun `For all locals doesn't sends to entities on different plane`() {
        //Given
        val action: (Int) -> Unit = mockk(relaxed = true)
        val item = world.createEntity()
        item.edit().add(Position(20, 20, 0))
        val view = world.createEntity()
        view.edit().add(Position(2, 20, 1))
        tick()
        //When
        system.forAllLocals(0, action)
        //Then
        verify(exactly = 0) {
            action.invoke(any())
        }
    }

    @Test
    fun `Send items chunk clear`() {
        //Given
        every { system.sendFloorItem(any(), any(), any(), any(), any(), any()) } answers {}
        every { system.clearFloorItems(any(), any()) } answers {}
        val view = createView(17, 17, 1, "name", 0)
        tick()
        //When
        system.sendFloorItems(view.id, 4, true)
        //Then
        verify {
            system.clearFloorItems(view.id, 4)
        }
    }

    @Test
    fun `Don't send chunk clear`() {
        //Given
        every { system.sendFloorItem(any(), any(), any(), any(), any(), any()) } answers {}
        val view = createView(17, 17, 1, "name", 0)
        tick()
        //When
        system.sendFloorItems(view.id, 4, false)
        //Then
        verify(exactly = 0) {
            system.clearFloorItems(view.id, 4)
        }
    }

    @Test
    fun `Send items sends each item`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any(), any(), any(), any(), any(), any()) } answers {}
        every { system.sendFloorItem(any()) } answers {}
        val item1 = createItem(5, 10, 18, 18, 1)
        val item2 = createItem(5, 10, 17, 17, 1)
        val view = createView(17, 17, 1, "name", 0)
        tick()
        //When
        system.sendFloorItems(view.id, 268468226, false)
        //Then
        verifyOrder {
            system.sendFloorItem(view.id, any(), "name", 0, item1.id, true)
            system.sendFloorItem(view.id, any(), "name", 0, item2.id, false)
        }
    }

    @Test
    fun `Internal send updates chunk`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 1)
        tick()
        //When
        system.sendFloorItem(1, LastLoadedRegion(), null, null, item.id, true)
        //Then
        verify { es.send(1, ChunkSend(8, 8, 1)) }
    }

    @Test
    fun `Internal send doesn't update chunk`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 1)
        tick()
        //When
        system.sendFloorItem(1, LastLoadedRegion(), null, null, item.id, false)
        //Then
        verify(exactly = 0) { es.send(1, ChunkSend(8, 8, 1)) }
    }

    @Test
    fun `Internal sends private if item private & owner`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 1)
        item.edit().remove(Public::class).add(Private("name", 10))
        tick()
        //When
        system.sendFloorItem(1, LastLoadedRegion(), "name", null, item.id, false)
        //Then
        verify { es.send(1, FloorItemPrivate(34,5, 10)) }
    }

    @Test
    fun `Internal doesn't send if item private & not owner`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 1)
        item.edit().add(Private("name", 10))
        tick()
        //When
        system.sendFloorItem(1, LastLoadedRegion(), null, null, item.id, false)
        //Then
        verify(exactly = 0) {
            es.send(1, FloorItemPrivate(34,5, 10))
            es.send(1, FloorItemPublic(-1,34, 5, 10))
        }
    }

    @Test
    fun `Internal sends private if public & owner`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 1, 4)
        tick()
        //When
        system.sendFloorItem(1, LastLoadedRegion(), null, 4, item.id, false)
        //Then
        verify {
            es.send(1, FloorItemPrivate(34,5, 10))
        }
    }

    @Test
    fun `Internal sends public if public & not owner`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 1, 4)
        tick()
        //When
        system.sendFloorItem(1, LastLoadedRegion(), null, 2, item.id, false)
        //Then
        verify {
            es.send(1, FloorItemPublic(4,34, 5, 10))
        }
    }

    @Test
    fun `Internal sends public if private but no owner`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 1, 4)
        item.edit().add(Private(null, 0))
        tick()
        //When
        system.sendFloorItem(1, LastLoadedRegion(), null, 2, item.id, false)
        //Then
        verify {
            es.send(1, FloorItemPublic(4,34, 5, 10))
        }
    }

    @Test
    fun `Internal sends nothing if not public or private`() {
        //Given
        every { system.combinedItems(any()) } returns false
        every { system.sendFloorItem(any()) } answers {}
        val item = createItem(5, 10, 18, 18, 1)
        item.edit().remove(Public::class)
        tick()
        //When
        system.sendFloorItem(1, LastLoadedRegion(), null, null, item.id, false)
        //Then
        verify(exactly = 0) {
            es.send(1, FloorItemPublic(4,34, 5, 10))
            es.send(1, FloorItemPrivate(34,5, 10))
        }
    }

    @Test
    fun `Internal combines`() {
        //Given
        every { definitions.get(5) } returns ItemDefinition()
        every { system.sendFloorItem(any()) } answers {}
        val view = createView(17, 17, 1, "name", 0)
        val currentItem = createItem(5, 5, 18, 18, 1, -1, 5)
        val item = createItem(5, 10, 18, 18, 1, -1, 10)
        tick()
        every { definitions.get(5) } returns ItemDefinition().apply { stackable = 1 }
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        //When
        system.combinedItems(item.id)
        //Given
        val amount = currentItem.getComponent(Amount::class)
        val public = currentItem.getComponent(Public::class)
        assertNotNull(amount)
        assertNotNull(public)
        assertEquals(15, amount!!.amount)
        assertEquals(10, public!!.timeout)
        verify { es.send(view.id, FloorItemUpdate(34, 5, 5, 15)) }
    }

    @Test
    fun `Internal combines if public with owner`() {
        //Given
        every { definitions.get(5) } returns ItemDefinition()
        every { system.sendFloorItem(any()) } answers {}
        val view = createView(17, 17, 1, "name", 0)
        val currentItem = createItem(5, 5, 18, 18, 1, view.id, 5)
        val item = createItem(5, 10, 18, 18, 1, -1, 10)
        tick()
        every { definitions.get(5) } returns ItemDefinition().apply { stackable = 1 }
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        //When
        system.combinedItems(item.id)
        //Given
        val amount = currentItem.getComponent(Amount::class)
        val public = currentItem.getComponent(Public::class)
        assertNotNull(amount)
        assertNotNull(public)
        assertEquals(15, amount!!.amount)
        assertEquals(10, public!!.timeout)
        verify { es.send(view.id, FloorItemUpdate(34, 5, 5, 15)) }
    }

    @Test
    fun `Internal doesn't combine if self`() {
        //Given
        every { definitions.get(5) } returns ItemDefinition()
        every { system.sendFloorItem(any()) } answers {}
        val view = createView(17, 17, 1, "name", 0)
        val item = createItem(5, 5, 18, 18, 1, view.id, 5)
        tick()
        every { definitions.get(5) } returns ItemDefinition().apply { stackable = 1 }
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        //When
        system.combinedItems(item.id)
        //Given
        verify(exactly = 0) { es.send(view.id, FloorItemUpdate(34, 5, 5, 15)) }
    }

    @Test
    fun `Internal doesn't combine if different type`() {
        //Given
        every { system.sendFloorItem(any()) } answers {}
        every { definitions.get(any(), any()) } returns ItemDefinition()
        val view = createView(17, 17, 1, "name", 0)
        createItem(4, 5, 18, 18, 1, view.id, 5)
        val item = createItem(5, 10, 18, 18, 1, -1, 10)
        tick()
        every { definitions.get(5) } returns ItemDefinition().apply { stackable = 1 }
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        //When
        system.combinedItems(item.id)
        //Given
        verify(exactly = 0) { es.send(view.id, FloorItemUpdate(34, 4, 5, 15)) }
    }

    @Test
    fun `Internal doesn't combine if different position`() {
        //Given
        every { definitions.get(5) } returns ItemDefinition()
        every { system.sendFloorItem(any()) } answers {}
        val view = createView(17, 17, 1, "name", 0)
        createItem(5, 5, 19, 18, 1, view.id, 5)
        val item = createItem(5, 10, 18, 18, 1, -1, 10)
        tick()
        every { definitions.get(any()) } returns ItemDefinition().apply { stackable = 1 }
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        //When
        system.combinedItems(item.id)
        //Given
        verify(exactly = 0) { es.send(view.id, FloorItemUpdate(34, 5, 5, 15)) }
    }

    @Test
    fun `Internal doesn't combine if different private owners`() {
        //Given
        every { definitions.get(5) } returns ItemDefinition()
        every { system.sendFloorItem(any()) } answers {}
        val view = createView(17, 17, 1, "name", 0)
        val currentItem = createItem(5, 5, 18, 18, 1, view.id, 5)
        currentItem.edit().remove(Public::class).add(Private(null, 10))
        val item = createItem(5, 10, 18, 18, 1, -1, 10)
        tick()
        every { definitions.get(5) } returns ItemDefinition().apply { stackable = 1 }
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        //When
        system.combinedItems(item.id)
        //Given
        verify(exactly = 0) { es.send(view.id, FloorItemUpdate(34, 5, 5, 15)) }
    }

    @Test
    fun `Internal combines if matching private owners`() {
        //Given
        every { definitions.get(5) } returns ItemDefinition()
        every { system.sendFloorItem(any()) } answers {}
        val view = createView(17, 17, 1, "name", 0)
        val currentItem = createItem(5, 5, 18, 18, 1, view.id, 5)
        currentItem.edit().remove(Public::class).add(Private("name", 10))
        val item = createItem(5, 10, 18, 18, 1, -1, 10)
        item.edit().remove(Public::class).add(Private("name", 10))
        tick()
        every { definitions.get(5) } returns ItemDefinition().apply { stackable = 1 }
        every { system.forAllLocals(any(), any()) } answers {
            val action = args.last() as (Int) -> Unit
            action.invoke(view.id)
        }
        //When
        system.combinedItems(item.id)
        //Given
        verify { es.send(view.id, FloorItemUpdate(34, 5, 5, 15)) }
    }

    private fun createView(x: Int, y: Int, plane: Int, name: String, index: Int): Entity {
        val entity = world.createEntity()
        entity.edit().add(NetworkSession()).add(Position(x, y, plane)).add(DisplayName(name)).add(ClientIndex(index)).add(LastLoadedRegion())
        return entity
    }

    private fun createItem(type: Int, amount: Int, x: Int, y: Int, plane: Int, owner: Int = -1, time: Int = 10): Entity {
        val entity = world.createEntity()
        entity.edit().add(Type(type)).add(Amount(amount)).add(Position(x, y, plane)).add(Public(owner, time))
        return entity
    }
}