package worlds.gregs.hestia.core.display.update.logic.block

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeTest
import worlds.gregs.hestia.core.display.update.logic.sync.player.PlayerSynchronize
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.AddPlayerSync
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.MoveGlobalPlayerSync
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.MovementPlayerSync
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.RemovePlayerSync
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.game.update.UpdateBlock
import worlds.gregs.hestia.network.client.encoders.messages.PlayerUpdate

/**
 * Tests local & global [UpdateBlock] are added during the correct [worlds.gregs.hestia.api.client.update.sync.SyncStage]s
 */
class PlayerBlockUpdateTest : SynchronizeTest<PlayerUpdate, PlayerSynchronize>() {

    private var factory: SyncFactory<*>? = null

    override val sync = object : PlayerSynchronize() {
        override fun isAddFactory(factory: SyncFactory<*>): Boolean {
            return this@PlayerBlockUpdateTest.factory == factory
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
        val factory = factory<AddPlayerSync>(local = false, mob = false)
        this.factory = factory
        val block = block<TestBlock>(true, false, 0x200)
        block.active(true)
        world(factory, block, index<Player>())
        val player = indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addPlayers(false, player)
        val update = sync()
        //Then
        assertThat(update.blocks).isNotEmpty
        assertThat(update.blocks.first().blocks).isNotEmpty
        assertThat(update.blocks.first().blocks.first()).isInstanceOf(TestBlock::class.java)
    }

    @Test
    fun `Global moving player doesn't update blocks`() {
        //Given
        val factory = factory<MoveGlobalPlayerSync>(local = false, mob = false)
        val block = block<TestBlock>(true, false, 0x200)
        block.active(true)
        world(factory, block, index<Player>())
        val player = indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addPlayers(false, player)
        val update = sync()
        //Then
        assertThat(update.blocks).isEmpty()
    }

    @Test
    fun `Move local does update blocks`() {
        //Given
        val factory = factory<MovementPlayerSync>(local = true, mob = false)
        val block = block<TestBlock>(true, false, 0x200)
        block.active(true)
        world(factory, block, index<Player>())
        val player = indexEntity(world.create())
        world.getEntity(player).edit().add(Player())
        addPlayers(true, player)
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
        val factory = factory<RemovePlayerSync>(local = true, mob = false)
        val block = block<TestBlock>(true, false, 0x200)
        block.active(true)
        world(factory, block, index<Player>())
        val player = indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addPlayers(false, player)
        val update = sync()
        //Then
        assertThat(update.blocks).isEmpty()
    }

}