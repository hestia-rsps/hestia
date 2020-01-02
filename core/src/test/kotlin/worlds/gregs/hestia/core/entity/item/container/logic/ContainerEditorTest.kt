package worlds.gregs.hestia.core.entity.item.container.logic

import arrow.core.left
import arrow.core.right
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import worlds.gregs.hestia.core.entity.item.container.api.Composition
import worlds.gregs.hestia.core.entity.item.container.api.Container
import worlds.gregs.hestia.core.entity.item.container.api.ContainerModificationDetails
import worlds.gregs.hestia.core.entity.item.container.api.ItemResult
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerEditor.addToStack
import worlds.gregs.hestia.core.entity.item.container.logic.ContainerEditor.subtractFromStack
import worlds.gregs.hestia.core.entity.item.container.model.Item
import worlds.gregs.hestia.core.entity.item.container.model.ItemContainer
import worlds.gregs.hestia.core.entity.item.container.model.StackType
import worlds.gregs.hestia.service.cache.definition.readers.ItemDefinitionReader

@ExtendWith(MockKExtension::class)
internal class ContainerEditorTest {

    @RelaxedMockK
    lateinit var definitions: ItemDefinitionReader

    @Test
    fun `Add not stackable all full`() {
        //Given
        val container = items(TYPE_1, TYPE_1)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, AMOUNT_2, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Full, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add not stackable index full`() {
        //Given
        val container = items(null, TYPE_1)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, 1, 1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Full, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add not stackable empty slots`() {
        //Given
        val container = items(TYPE_1, null, null)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, 2, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            var item = it[1]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(1, item.amount)
            item = it[2]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(1, item.amount)
            assertTrue(overflows.isEmpty())
        })
    }

    @Test
    fun `Add not stackable index empty slots`() {
        //Given
        val container = items(TYPE_1, null, null)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, 2, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Invalid, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add stackable not existing full`() {
        //Given
        val container = items(TYPE_1, TYPE_1)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, AMOUNT_2, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Full, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add stackable not existing index full`() {
        //Given
        val container = items(TYPE_1, TYPE_1)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, AMOUNT_2, 1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Full, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add stackable not existing empty`() {
        //Given
        val container = items(TYPE_1, null)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, AMOUNT_2, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[1]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(AMOUNT_2, item.amount)
            assertTrue(overflows.isEmpty())
        })
    }

    @Test
    fun `Add stackable not existing index empty`() {
        //Given
        val container = items(TYPE_1, null, null)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, AMOUNT_2, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[2]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(AMOUNT_2, item.amount)
            assertTrue(overflows.isEmpty())
        })
    }

    @Test
    fun `Add stackable existing negative`() {
        //Given
        val container = items(TYPE_1, TYPE_2)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, -1, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Invalid, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add stackable existing index negative`() {
        //Given
        val container = items(TYPE_1, TYPE_2, TYPE_2)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, -1, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Invalid, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add stackable existing zero`() {
        //Given
        val container = items(TYPE_1, TYPE_2)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, 0, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Invalid, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add stackable existing index zero`() {
        //Given
        val container = items(TYPE_1, TYPE_2, TYPE_2)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, 0, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Invalid, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add stackable existing exceed maximum`() {
        //Given
        val container = items(TYPE_1 to AMOUNT_1, TYPE_2 to 100, null)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, Int.MAX_VALUE - 2, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[1]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(Int.MAX_VALUE, item.amount)
            //Check overflow correct amount
            assertFalse(overflows.isEmpty())
            val overflow = overflows.first()
            assertEquals(TYPE_2, overflow.type)
            assertEquals(98, overflow.amount)
        })
    }

    @Test
    fun `Add stackable existing index exceed maximum`() {
        //Given
        val container = items(TYPE_1 to AMOUNT_1, TYPE_2 to 50, TYPE_2 to 100)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, Int.MAX_VALUE - 2, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[2]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(Int.MAX_VALUE, item.amount)
            //Check overflow correct amount
            assertFalse(overflows.isEmpty())
            val overflow = overflows.first()
            assertEquals(TYPE_2, overflow.type)
            assertEquals(98, overflow.amount)
        })
    }

    @Test
    fun `Add stackable existing doesn't exceed maximum`() {
        //Given
        val container = items(TYPE_1 to AMOUNT_1, TYPE_2 to 100, null)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, 100, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[1]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(200, item.amount)
            assertTrue(overflows.isEmpty())
        })
    }

    @Test
    fun `Add stackable existing index doesn't exceed maximum`() {
        //Given
        val container = items(TYPE_1 to AMOUNT_1, TYPE_2 to 50, TYPE_2 to 100)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = add(TYPE_2, 100, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[2]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(200, item.amount)
            assertTrue(overflows.isEmpty())
        })
    }

    @Test
    fun `Add empty slot full`() {
        //Given
        val container = items(TYPE_1, TYPE_1)
        //When
        val either = ContainerEditor.addEmptySlot(container, TYPE_2, AMOUNT_2, 0)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Full, issue)
        }, {
            fail("Expected full result.")
        })
    }

    @Test
    fun `Add empty slot empty`() {
        //Given
        val container = items(null, TYPE_1)
        //When
        val either = ContainerEditor.addEmptySlot(container, TYPE_2, AMOUNT_2, 0)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected successful result.")
        }, {
            val item = it[0]!!
            assertEquals(TYPE_2, item.type)
            assertEquals(AMOUNT_2, item.amount)
        })
    }

    @Test
    fun `Check empty all full`() {
        //Given
        val container = items(TYPE_1, TYPE_1, TYPE_1)
        //When
        val index = ContainerEditor.indexOfEmpty(container, -1)
        //Then
        assertEquals(-1, index)
    }

    @Test
    fun `Check empty all empty`() {
        //Given
        val container = items(TYPE_1, null, null)
        //When
        val index = ContainerEditor.indexOfEmpty(container, -1)
        //Then
        assertEquals(1, index)
    }

    @Test
    fun `Check empty index full`() {
        //Given
        val container = items(null, TYPE_1, null)
        //When
        val index = ContainerEditor.indexOfEmpty(container, 1)
        //Then
        assertEquals(-1, index)
    }

    @Test
    fun `Check empty index empty`() {
        //Given
        val container = items(TYPE_1, null, null)
        //When
        val index = ContainerEditor.indexOfEmpty(container, 1)
        //Then
        assertEquals(1, index)
    }

    @Test
    fun `Check type all different`() {
        //Given
        val container = items(null, TYPE_1, TYPE_1)
        //When
        val index = ContainerEditor.indexOf(container, TYPE_2, -1)
        //Then
        assertEquals(-1, index)
    }

    @Test
    fun `Check type all matches`() {
        //Given
        val container = items(null, TYPE_1, null)
        //When
        val index = ContainerEditor.indexOf(container, TYPE_1, -1)
        //Then
        assertEquals(1, index)
    }

    @Test
    fun `Check type index different`() {
        //Given
        val container = items(null, TYPE_1, null)
        //When
        val index = ContainerEditor.indexOf(container, TYPE_2, 1)
        //Then
        assertEquals(-1, index)
    }

    @Test
    fun `Check type index matches`() {
        //Given
        val container = items(null, TYPE_1, null)
        //When
        val index = ContainerEditor.indexOf(container, TYPE_1, 1)
        //Then
        assertEquals(1, index)
    }

    @Test
    fun `Unfold success overflow`() {
        //Given
        val container = items(TYPE_1, null)
        val overflows = mutableListOf<Item>()
        val modification = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        //When
        val result = ContainerEditor.unfold(modification) {
            it[1] = Item(TYPE_2, AMOUNT_2)
            overflows.add(Item(TYPE_3, AMOUNT_3))
            it.right()
        }
        //Then
        val either = result.either
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[1]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(AMOUNT_2, item.amount)
        })
        val overflow = result.overflows.first()
        assertEquals(TYPE_3, overflow.type)
        assertEquals(AMOUNT_3, overflow.amount)
    }

    @Test
    fun `Unfold failure overflow`() {
        //Given
        val container = items(TYPE_1, null)
        val overflows = mutableListOf<Item>()
        val modification = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        //When
        val result = ContainerEditor.unfold(modification) {
            it[1] = Item(TYPE_2, AMOUNT_2)
            overflows.add(Item(TYPE_3, AMOUNT_3))
            ItemResult.Issue.Full.left()
        }
        //Then
        val either = result.either
        assertTrue(either.isLeft())
        either.fold({
            assertEquals(ItemResult.Issue.Full, it)
        }, {
            fail("Expected failure.")
        })
        //Overflow is added
        val overflow = result.overflows.first()
        assertEquals(TYPE_3, overflow.type)
        assertEquals(AMOUNT_3, overflow.amount)
        //Shouldn't have modified existing container
        val item = container[1]
        assertNull(item)
    }


    @Test
    fun `Transform doesn't modify existing items`() {
        //Given
        val container = mockk<ItemContainer>(relaxed = true)
        val items = items(TYPE_1, null)
        every { container.items } returns items
        every { container.setItems(any()) } returns true
        val function: Composition = {
            val changed = it.either.fold({ issue -> issue.left() }, { container ->
                container[1] = Item(TYPE_2, AMOUNT_2)
                container.right()
            })
            ContainerModificationDetails(changed, it.definitions, it.overflows, it.stackType)
        }
        //When
        val result = container.transform(definitions, StackType.Normal, function)
        //Then
        assertNull(items[1])
        assertEquals(ItemResult.Success.Success, result)
    }

    @Test
    fun `Transform failure`() {
        //Given
        val container = mockk<ItemContainer>(relaxed = true)
        val items = items(TYPE_1, null)
        every { container.items } returns items
        val function: Composition = {
            ContainerModificationDetails(ItemResult.Issue.Full.left(), it.definitions, it.overflows, it.stackType)
        }
        //When
        val result = container.transform(definitions, StackType.Normal, function)
        //Then
        assertEquals(ItemResult.Issue.Full, result)
    }

    @Test
    fun `Transform set failure`() {
        //Given
        val container = mockk<ItemContainer>(relaxed = true)
        val items = items(TYPE_1, null)
        every { container.items } returns items
        every { container.setItems(any()) } returns false
        val function: Composition = {
            ContainerModificationDetails(it.either, it.definitions, it.overflows, it.stackType)
        }
        //When
        val result = container.transform(definitions, StackType.Normal, function)
        //Then
        assertEquals(ItemResult.Issue.Invalid, result)
    }

    @Test
    fun `Transform with overflow`() {
        //Given
        val container = mockk<ItemContainer>(relaxed = true)
        val overflows = mutableListOf(Item(TYPE_2, AMOUNT_2))
        val items = items(TYPE_1, null)
        every { container.items } returns items
        every { container.setItems(any()) } returns true
        val function: Composition = {
            ContainerModificationDetails(it.either, it.definitions, overflows, it.stackType)
        }
        //When
        val result = container.transform(definitions, StackType.Normal, function, true)
        //Then
        result as ItemResult.Success.Overflow
        val overflow = result.list.first()
        assertEquals(TYPE_2, overflow.type)
        assertEquals(AMOUNT_2, overflow.amount)
    }

    @Test
    fun `Transform without overflow`() {
        //Given
        val container = mockk<ItemContainer>(relaxed = true)
        val overflows = mutableListOf(Item(TYPE_2, AMOUNT_2))
        val items = items(TYPE_1, null)
        every { container.items } returns items
        every { container.setItems(any()) } returns true
        val function: Composition = {
            ContainerModificationDetails(it.either, it.definitions, overflows, it.stackType)
        }
        //When
        val result = container.transform(definitions, StackType.Normal, function, false)
        //Then
        assertFalse(result is ItemResult.Success.Overflow)
        assertEquals(ItemResult.Issue.Full, result)
    }

    @Test
    fun `Add to stack`() {
        //Given
        val item = Item(TYPE_1, AMOUNT_1)
        //When
        val result = addToStack(item, AMOUNT_2)
        //Then
        assertTrue(result.isRight())
        result.fold({
            fail("Expected success.")
        }, { modified ->
            assertEquals(TYPE_1, modified.type)
            assertEquals(AMOUNT_1 + AMOUNT_2, modified.amount)
        })
    }

    @Test
    fun `Add to stack current under maximum`() {
        //Given
        val item = Item(TYPE_1, Int.MAX_VALUE - 10)
        //When
        val result = addToStack(item, 100)
        //Then
        assertTrue(result.isLeft())
        result.fold({ overflow ->
            assertEquals(TYPE_1, overflow.type)
            assertEquals(90, overflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add to stack current is maximum`() {
        //Given
        val item = Item(TYPE_1, Int.MAX_VALUE)
        //When
        val result = addToStack(item, 100)
        //Then
        assertTrue(result.isLeft())
        result.fold({ overflow ->
            assertEquals(TYPE_1, overflow.type)
            assertEquals(100, overflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add to stack addition under maximum`() {
        //Given
        val item = Item(TYPE_1, 100)
        //When
        val result = addToStack(item, Int.MAX_VALUE - 10)
        //Then
        assertTrue(result.isLeft())
        result.fold({ overflow ->
            assertEquals(TYPE_1, overflow.type)
            assertEquals(90, overflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add to stack addition is maximum`() {
        //Given
        val item = Item(TYPE_1, 100)
        //When
        val result = addToStack(item, Int.MAX_VALUE)
        //Then
        assertTrue(result.isLeft())
        result.fold({ overflow ->
            assertEquals(TYPE_1, overflow.type)
            assertEquals(100, overflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add to stack both are under maximum`() {
        //Given
        val item = Item(TYPE_1, Int.MAX_VALUE - 10)
        //When
        val result = addToStack(item, Int.MAX_VALUE - 10)
        //Then
        assertTrue(result.isLeft())
        result.fold({ overflow ->
            assertEquals(TYPE_1, overflow.type)
            assertEquals(Int.MAX_VALUE - 20, overflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Add to stack both are maximum`() {
        //Given
        val item = Item(TYPE_1, Int.MAX_VALUE)
        //When
        val result = addToStack(item, Int.MAX_VALUE)
        //Then
        assertTrue(result.isLeft())
        result.fold({ overflow ->
            assertEquals(TYPE_1, overflow.type)
            assertEquals(Int.MAX_VALUE, overflow.amount)
        }, {
            fail("Expected failure.")
        })
    }


    @Test
    fun `Subtract from stack`() {
        //Given
        val item = Item(TYPE_1, AMOUNT_2)
        //When
        val result = subtractFromStack(item, AMOUNT_1)
        //Then
        assertTrue(result.isRight())
        result.fold({
            fail("Expected success.")
        }, {
            assertNotNull(it)
            assertEquals(TYPE_1, it!!.type)
            assertEquals(AMOUNT_2 - AMOUNT_1, it.amount)
        })
    }

    @Test
    fun `Subtract from stack subtraction more than current`() {
        //Given
        val item = Item(TYPE_1, AMOUNT_1)
        //When
        val result = subtractFromStack(item, AMOUNT_2)
        //Then
        assertTrue(result.isLeft())
        result.fold({ issue ->
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(AMOUNT_2 - AMOUNT_1, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Subtract from stack subtraction with exact amount`() {
        //Given
        val item = Item(TYPE_1, AMOUNT_1)
        //When
        val result = subtractFromStack(item, AMOUNT_1)
        //Then
        assertTrue(result.isRight())
        result.fold({
            fail("Expected success.")
        }, {
            assertNull(it)
        })
    }

    @Test
    fun `Subtract from stack current is negative`() {
        //Given
        val item = Item(TYPE_1, -10)
        //When
        val result = subtractFromStack(item, 100)
        //Then
        assertTrue(result.isLeft())
        result.fold({ issue ->
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(110, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }


    @Test
    fun `Subtract from stack subtraction is over minimum`() {
        //Given
        val item = Item(TYPE_1, Int.MIN_VALUE + 10)
        //When
        val result = subtractFromStack(item, 100)
        //Then
        assertTrue(result.isLeft())
        result.fold({ issue ->
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(90, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Subtract from stack subtraction is minimum`() {
        //Given
        val item = Item(TYPE_1, Int.MIN_VALUE)
        //When
        val result = subtractFromStack(item, 100)
        //Then
        assertTrue(result.isLeft())
        result.fold({ issue ->
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(100, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Subtract from stack both are over maximum`() {
        //Given
        val item = Item(TYPE_1, Int.MIN_VALUE + 10)
        //When
        val result = subtractFromStack(item, Int.MAX_VALUE - 10)
        //Then
        assertTrue(result.isLeft())
        result.fold({ issue ->
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(Int.MAX_VALUE - 20, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Subtract from stack both are maximum`() {
        //Given
        val item = Item(TYPE_1, Int.MIN_VALUE)
        //When
        val result = subtractFromStack(item, Int.MAX_VALUE)
        //Then
        assertTrue(result.isLeft())
        result.fold({ issue ->
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(Int.MAX_VALUE, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove negative`() {
        //Given
        val container = items(TYPE_1)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, -1, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Invalid, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove zero`() {
        //Given
        val container = items(TYPE_1)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 0, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertEquals(ItemResult.Issue.Invalid, issue)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove stackable not existing`() {
        //Given
        val container = items(TYPE_1 to 1)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_2, AMOUNT_2, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            issue as ItemResult.Issue.Underflow
            val underflow = issue.item
            assertEquals(TYPE_2, underflow.type)
            assertEquals(AMOUNT_2, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove stackable not existing index`() {
        //Given
        val container = items(TYPE_2 to 1, null)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_2, AMOUNT_2, 1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            issue as ItemResult.Issue.Underflow
            val underflow = issue.item
            assertEquals(TYPE_2, underflow.type)
            assertEquals(AMOUNT_2, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove stackable existing negative`() {
        //Given
        val container = items(null, TYPE_1 to 100)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 101, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            issue as ItemResult.Issue.Underflow
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(1, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove stackable existing index negative`() {
        //Given
        val container = items(TYPE_1 to 50, TYPE_1 to 100)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 101, 1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            issue as ItemResult.Issue.Underflow
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(1, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove stackable existing zero`() {
        //Given
        val container = items(TYPE_1 to AMOUNT_1)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, AMOUNT_1, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            assertNull(it[0])
        })
    }

    @Test
    fun `Remove stackable existing index zero`() {
        //Given
        val container = items(TYPE_1 to 1, TYPE_1 to AMOUNT_1)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, AMOUNT_1, 1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            assertNull(it[1])
        })
    }

    @Test
    fun `Remove stackable existing positive`() {
        //Given
        val container = items(TYPE_1 to 100)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 98, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[0]
            assertNotNull(item)
            assertEquals(TYPE_1, item!!.type)
            assertEquals(2, item.amount)
        })
    }

    @Test
    fun `Remove stackable existing index positive`() {
        //Given
        val container = items(TYPE_1 to 50, TYPE_1 to 100)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 98, 1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[1]
            assertNotNull(item)
            assertEquals(TYPE_1, item!!.type)
            assertEquals(2, item.amount)
        })
    }


    @Test
    fun `Remove not stackable one exists`() {
        //Given
        val container = items(null, TYPE_1, TYPE_1)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 1, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            assertNull(it[1])
            assertNotNull(it[2])
        })
    }

    @Test
    fun `Remove not stackable one exists index`() {
        //Given
        val container = items(null, TYPE_1, TYPE_1)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 1, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            assertNull(it[2])
            assertNotNull(it[1])
        })
    }

    @Test
    fun `Remove not stackable one not existing`() {
        //Given
        val container = items(TYPE_2)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 1, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            issue as ItemResult.Issue.Underflow
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(1, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove not stackable one not existing index`() {
        //Given
        val container = items(TYPE_1, null)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 1, 1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            issue as ItemResult.Issue.Underflow
            val underflow = issue.item
            assertEquals(TYPE_1, underflow.type)
            assertEquals(1, underflow.amount)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Remove not stackable multiple exists`() {
        //Given
        val container = items(TYPE_2, TYPE_1, TYPE_1, TYPE_1)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 2, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            assertNotNull(it[0])
            assertNull(it[1])
            assertNull(it[2])
            assertNotNull(it[3])
        })
    }

    @Test
    fun `Remove not stackable multiple exists index`() {
        //Given
        val container = items(TYPE_1, TYPE_1, TYPE_1)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Normal)
        val modification = remove(TYPE_1, 2, 1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isLeft())
        either.fold({ issue ->
            assertTrue(issue is ItemResult.Issue.Invalid)
        }, {
            fail("Expected failure.")
        })
    }

    @Test
    fun `Always stack non-stackable item`() {
        //Given
        val container = items(TYPE_1, null)
        every { definitions.get(any()).stackable } returns 0
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Always)
        val modification = add(TYPE_1, 1, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            val item = it[0]
            assertNotNull(item)
            assertEquals(TYPE_1, item!!.type)
            assertEquals(2, item.amount)
        })
    }

    @Test
    fun `Never stack stackable item`() {
        //Given
        val container = items(TYPE_1, null, null)
        every { definitions.get(any()).stackable } returns 1
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Never)
        val modification = add(TYPE_1, 2, -1)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            var item = it[1]
            assertNotNull(item)
            assertEquals(TYPE_1, item!!.type)
            assertEquals(1, item.amount)
            item = it[2]
            assertNotNull(item)
            assertEquals(TYPE_1, item!!.type)
            assertEquals(1, item.amount)
        })
    }

    @Test
    fun `Switch items`() {
        //Given
        val container = items(TYPE_1 to AMOUNT_1, null, TYPE_2 to AMOUNT_2)
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Never)
        val modification = switch(0, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            var item = it[0]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(AMOUNT_2, item.amount)
            item = it[2]
            assertNotNull(item)
            assertEquals(TYPE_1, item!!.type)
            assertEquals(AMOUNT_1, item.amount)
        })
    }

    @Test
    fun `Switch item with null`() {
        //Given
        val container = items(TYPE_1 to AMOUNT_1, null, TYPE_2 to AMOUNT_2)
        val overflows = mutableListOf<Item>()
        val details = ContainerModificationDetails(container.clone().right(), definitions, overflows, StackType.Never)
        val modification = switch(1, 2)
        //When
        val (either, _, _) = modification.invoke(details)
        //Then
        assertTrue(either.isRight())
        either.fold({
            fail("Expected success.")
        }, {
            var item = it[1]
            assertNotNull(item)
            assertEquals(TYPE_2, item!!.type)
            assertEquals(AMOUNT_2, item.amount)
            item = it[2]
            assertNull(item)
        })
    }

    companion object {

        private fun items(vararg ids: Int?): Container {
            return ids.map { if (it != null) Item(it, 1) else null }.toTypedArray()
        }

        private fun items(vararg ids: Pair<Int, Int>?): Container {
            return ids.map { if (it != null) Item(it.first, it.second) else null }.toTypedArray()
        }

        const val TYPE_1 = 115
        const val TYPE_2 = 215
        const val TYPE_3 = 315
        const val AMOUNT_1 = 100
        const val AMOUNT_2 = 1000
        const val AMOUNT_3 = 2000
    }
}