package worlds.gregs.hestia.core.display.update.logic.block

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeTest
import worlds.gregs.hestia.core.display.update.logic.sync.npc.NpcSynchronize
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.AddNpcSync
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.MovementNpcSync
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.RemoveNpcSync
import worlds.gregs.hestia.core.entity.npc.api.Npc
import worlds.gregs.hestia.game.update.UpdateBlock
import worlds.gregs.hestia.network.client.encoders.messages.NpcUpdate

/**
 * Tests local & global [UpdateBlock] are added during the correct [worlds.gregs.hestia.api.client.update.sync.SyncStage]s
 */
class NpcBlockUpdateTest : SynchronizeTest<NpcUpdate, NpcSynchronize>() {

    private var factory: SyncFactory<*>? = null

    override val sync = object : NpcSynchronize() {
        override fun isAddFactory(factory: SyncFactory<*>): Boolean {
            return this@NpcBlockUpdateTest.factory == factory
        }
    }

    @BeforeEach
    fun setup() {
        factory = null
    }

    private class TestBlock : UpdateBlock {
        override val flag = 0x200
    }

    @Test
    fun `Add player updates blocks`() {
        //Given
        val factory = factory<AddNpcSync>(local = false, npc = true)
        this.factory = factory
        val block = block<TestBlock>(true, true, 0x200)
        block.active(true)
        world(factory, block, index<Npc>())
        val npc = indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addNpcs(false, npc)
        val update = sync()
        //Then
        assertThat(update.blocks).isNotEmpty
        assertThat(update.blocks.first().blocks).isNotEmpty
        assertThat(update.blocks.first().blocks.first()).isInstanceOf(TestBlock::class.java)
    }

    @Test
    fun `Move local does update blocks`() {
        //Given
        val factory = factory<MovementNpcSync>(local = true, npc = true)
        val block = block<TestBlock>(true, true, 0x200)
        block.active(true)
        world(factory, block, index<Npc>())
        val npc = indexEntity(world.create())
        world.getEntity(npc).edit().add(Npc())
        addNpcs(true, npc)
        tick()
        factory.change(true)
        //When
        val update = sync()
        //Then
        assertThat(update.blocks).isNotEmpty
    }

    @Test
    fun `Remove local doesn't update blocks`() {
        //Given
        val factory = factory<RemoveNpcSync>(local = true, npc = true)
        val block = block<TestBlock>(true, true, 0x200)
        block.active(true)
        world(factory, block, index<Npc>())
        addNpcs(true, indexEntity(world.create()))
        tick()
        factory.change(true)
        //When
        val update = sync()
        //Then
        assertThat(update.blocks).isEmpty()
    }

}