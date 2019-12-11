package worlds.gregs.hestia.game.client.update.block

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.api.client.update.block.UpdateBlock
import worlds.gregs.hestia.api.client.update.sync.SyncFactory
import worlds.gregs.hestia.api.mob.Mob
import worlds.gregs.hestia.game.client.update.SynchronizeTest
import worlds.gregs.hestia.game.client.update.sync.mob.MobSynchronize
import worlds.gregs.hestia.network.client.encoders.messages.MobUpdate
import worlds.gregs.hestia.network.update.sync.mob.AddMobSync
import worlds.gregs.hestia.network.update.sync.mob.MovementMobSync
import worlds.gregs.hestia.network.update.sync.mob.RemoveMobSync

/**
 * Tests local & global [UpdateBlock] are added during the correct [worlds.gregs.hestia.api.client.update.sync.SyncStage]s
 */
class MobBlockUpdateTest : SynchronizeTest<MobUpdate, MobSynchronize>() {

    private var factory: SyncFactory<*>? = null

    override val sync = object : MobSynchronize() {
        override fun isAddFactory(factory: SyncFactory<*>): Boolean {
            return this@MobBlockUpdateTest.factory == factory
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
        val factory = factory<AddMobSync>(local = false, mob = true)
        this.factory = factory
        val block = block<TestBlock>(true, true, 0x200)
        block.active(true)
        world(factory, block, index<Mob>())
        val mob = indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addMobs(false, mob)
        val update = sync()
        //Then
        assertThat(update.blocks).isNotEmpty
        assertThat(update.blocks.first().blocks).isNotEmpty
        assertThat(update.blocks.first().blocks.first()).isInstanceOf(TestBlock::class.java)
    }

    @Test
    fun `Move local does update blocks`() {
        //Given
        val factory = factory<MovementMobSync>(local = true, mob = true)
        val block = block<TestBlock>(true, true, 0x200)
        block.active(true)
        world(factory, block, index<Mob>())
        val mob = indexEntity(world.create())
        world.getEntity(mob).edit().add(Mob())
        addMobs(true, mob)
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
        val factory = factory<RemoveMobSync>(local = true, mob = true)
        val block = block<TestBlock>(true, true, 0x200)
        block.active(true)
        world(factory, block, index<Mob>())
        addMobs(true, indexEntity(world.create()))
        tick()
        factory.change(true)
        //When
        val update = sync()
        //Then
        assertThat(update.blocks).isEmpty()
    }

}