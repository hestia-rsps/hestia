package worlds.gregs.hestia.core.display.update.logic.sync.npc

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeTest
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.AddNpcSync
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.NpcSizeStage
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.RemoveNpcSync
import worlds.gregs.hestia.core.entity.npc.api.Npc
import worlds.gregs.hestia.network.client.encoders.messages.NpcUpdate

/**
 * Tests single and multiple sync stages are added for local & global players
 * [AddNpcSync] and [RemoveNpcSync] are used as examples, but applies to all [worlds.gregs.hestia.api.client.update.sync.SyncStage]s
 */
class NpcSynchronizeTest : SynchronizeTest<NpcUpdate, NpcSynchronize>() {

    private var factory: SyncFactory<*>? = null

    override val sync = object : NpcSynchronize() {
        override fun isAddFactory(factory: SyncFactory<*>): Boolean {
            return this@NpcSynchronizeTest.factory == factory
        }
    }

    @BeforeEach
    fun setup() {
        factory = null
    }

    @Test
    fun `Add single npc`() {
        //Given
        val factory = factory<AddNpcSync>(local = false, npc = true)
        this.factory = factory
        world(factory, index<Npc>())
        val npc = indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addNpcs(false, npc)
        val update = sync()
        //Then
        factory.assertCreated(1, npc)
        update.apply {
            assertLocalCount(1, 0)
            assertStage<AddNpcSync>(2)
        }
    }

    @Test
    fun `Can't add more than cycle limit`() {
        //Given
        val factory = factory<AddNpcSync>(local = false, npc = true)
        this.factory = factory
        world(factory, index<Npc>())
        repeat(41) {
            indexEntity(world.create())
        }
        tick()
        factory.change(true)
        //When
        addNpcs(false, *(1 until 41).toList().toIntArray())
        val update = sync()
        //Then
        factory.assertCreated(0, entities.last())
        update.apply {
            assertLocalCount(1, 0)
            assertStages<AddNpcSync>(2 until 42)
        }
    }

    @Test
    fun `Cycle after addition has correct npc count`() {
        //Given
        world(emptyFactory(), index<Npc>())
        addNpcs(true, indexEntity(world.create()))
        //When
        val update = sync()
        //Then
        update.assertLocalCount(1, 1)
    }

    @Test
    fun `Remove single npc`() {
        //Given
        val factory = factory<RemoveNpcSync>(local = true, npc = true)
        world(factory, index<Npc>())
        val npc = indexEntity(world.create())
        addNpcs(true, npc)
        tick()
        factory.change(true)
        //When
        removeNpcs(false, npc)
        val update = sync()
        //Then
        factory.assertCreated(1, npc)
        update.apply {
            assertLocalCount(1, 1)
            assertStage<RemoveNpcSync>(2)
        }
    }

    @Test
    fun `Remove lots of npcs`() {
        //Given
        val factory = factory<RemoveNpcSync>(local = true, npc = true)
        world(factory, index<Npc>())
        repeat(256) {
            addNpcs(true, indexEntity(world.create()))
        }
        tick()
        factory.change(true)
        //When
        removeNpcs(false, *(1..256).toList().toIntArray())
        val update = sync()
        //Then
        factory.assertCreated(1, entities.last())
        update.apply {
            assertLocalCount(1, 256)
            assertStages<RemoveNpcSync>(2 until 258)
        }
    }

    @Test
    fun `Global gap adds up`() {
        //Given
        val factory = factory<AddNpcSync>(local = false, npc = true)
        this.factory = factory
        world(factory, index<Npc>())
        indexEntity(world.create())
        indexEntity(-1)//A removed npc gap
        indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addNpcs(false, 1, 2)
        val update = sync()
        //Then
        update.apply {
            assertLocalCount(1, 0)
            assertStage<AddNpcSync>(2)
            assertStage<AddNpcSync>(3)
        }
    }

    private fun NpcUpdate.assertLocalCount(index: Int, size: Int) {
        Assertions.assertThat(assertStage<NpcSizeStage>(index).npcCount).isEqualTo(size)
    }

}