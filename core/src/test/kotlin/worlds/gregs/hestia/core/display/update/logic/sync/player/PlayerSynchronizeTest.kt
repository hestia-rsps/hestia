package worlds.gregs.hestia.core.display.update.logic.sync.player

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeTest
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.AddPlayerSync
import worlds.gregs.hestia.core.display.update.logic.sync.player.stages.RemovePlayerSync
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.network.client.encoders.messages.PlayerUpdate
import worlds.gregs.hestia.core.display.update.model.sync.SkipStage

/**
 * Tests single and multiple sync stages are added for local & global players
 * [AddPlayerSync] and [RemovePlayerSync] are used as examples, but applies to all [worlds.gregs.hestia.api.client.update.sync.SyncStage]s
 */
class PlayerSynchronizeTest : SynchronizeTest<PlayerUpdate, PlayerSynchronize>() {

    private var factory: SyncFactory<*>? = null

    override val sync = object : PlayerSynchronize() {
        override fun isAddFactory(factory: SyncFactory<*>): Boolean {
            return this@PlayerSynchronizeTest.factory == factory
        }
    }

    @BeforeEach
    fun setup() {
        factory = null
    }

    @Test
    fun `Add single player`() {
        //Given
        val factory = factory<AddPlayerSync>(local = false, mob = false)
        this.factory = factory
        world(factory, index<Player>())
        val player = indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addPlayers(false, player)
        val update = sync()
        //Then
        factory.assertCreated(1, player)
        update.apply {
            assertStage<AddPlayerSync>(5)
            assertSkip(6, 2045)
        }
    }

    @Test
    fun `Can add more than cycle limit`() {//Cycle limit now handled by viewport
        //Given
        val factory = factory<AddPlayerSync>(local = false, mob = false)
        this.factory = factory
        world(factory, index<Player>())
        repeat(41) {
            indexEntity(world.create())
        }
        tick()
        factory.change(true)
        //When
        addPlayers(false, *(1 until 41).toList().toIntArray())
        val update = sync()
        //Then
        factory.assertCreated(1, entities.last())
        update.apply {
            assertStages<AddPlayerSync>(5 until 46)
            assertSkip(46, 2005)
        }
    }

    @Test
    fun `Remove single player`() {
        //Given
        val factory = factory<RemovePlayerSync>(local = true, mob = false)
        world(factory, index<Player>())
        val player = indexEntity(world.create())
        addPlayers(true, player)
        tick()
        factory.change(true)
        //When
        removePlayers(false, player)
        val update = sync()
        //Then
        factory.assertCreated(1, player)
        update.apply {
            assertStage<RemovePlayerSync>(3)
            assertSkip(6, 2045)
        }
    }

    @Test
    fun `Remove lots of players`() {
        //Given
        val factory = factory<RemovePlayerSync>(local = true, mob = false)
        world(factory, index<Player>())
        repeat(256) {
            addPlayers(true, indexEntity(world.create()))
        }
        tick()
        factory.change(true)
        //When
        removePlayers(false, *(1..256).toList().toIntArray())
        val update = sync()
        //Then
        factory.assertCreated(1, entities.last())
        update.apply {
            assertStages<RemovePlayerSync>(5 until 261)
            assertSkip(261, 1790)
        }
    }

    @Test
    fun `Global skip adds up`() {
        //Given
        val factory = factory<AddPlayerSync>(local = false, mob = false)
        this.factory = factory
        world(factory, index<Player>())
        indexEntity(world.create())
        indexEntity(-1)//A removed player gap
        indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addPlayers(false, 1, 2)
        val update = sync()
        //Then
        update.apply {
            assertStage<AddPlayerSync>(5)
            assertSkip(6, 0)
            assertStage<AddPlayerSync>(7)
            assertSkip(8, 2043)
        }
    }

    private fun PlayerUpdate.assertSkip(index: Int, skip: Int) {
        assertThat(assertStage<SkipStage>(index).skip).isEqualTo(skip)
    }
}