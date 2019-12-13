package worlds.gregs.hestia.core.display.update.logic.sync.mob

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.update.api.SyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeTest
import worlds.gregs.hestia.core.display.update.logic.sync.mob.stages.AddMobSync
import worlds.gregs.hestia.core.display.update.logic.sync.mob.stages.MobSizeStage
import worlds.gregs.hestia.core.display.update.logic.sync.mob.stages.RemoveMobSync
import worlds.gregs.hestia.core.entity.mob.api.Mob
import worlds.gregs.hestia.network.client.encoders.messages.MobUpdate

/**
 * Tests single and multiple sync stages are added for local & global players
 * [AddMobSync] and [RemoveMobSync] are used as examples, but applies to all [worlds.gregs.hestia.api.client.update.sync.SyncStage]s
 */
class MobSynchronizeTest : SynchronizeTest<MobUpdate, MobSynchronize>() {

    private var factory: SyncFactory<*>? = null

    override val sync = object : MobSynchronize() {
        override fun isAddFactory(factory: SyncFactory<*>): Boolean {
            return this@MobSynchronizeTest.factory == factory
        }
    }

    @BeforeEach
    fun setup() {
        factory = null
    }

    @Test
    fun `Add single mob`() {
        //Given
        val factory = factory<AddMobSync>(local = false, mob = true)
        this.factory = factory
        world(factory, index<Mob>())
        val mob = indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addMobs(false, mob)
        val update = sync()
        //Then
        factory.assertCreated(1, mob)
        update.apply {
            assertLocalCount(1, 0)
            assertStage<AddMobSync>(2)
        }
    }

    @Test
    fun `Can't add more than cycle limit`() {
        //Given
        val factory = factory<AddMobSync>(local = false, mob = true)
        this.factory = factory
        world(factory, index<Mob>())
        repeat(41) {
            indexEntity(world.create())
        }
        tick()
        factory.change(true)
        //When
        addMobs(false, *(1 until 41).toList().toIntArray())
        val update = sync()
        //Then
        factory.assertCreated(0, entities.last())
        update.apply {
            assertLocalCount(1, 0)
            assertStages<AddMobSync>(2 until 42)
        }
    }

    @Test
    fun `Cycle after addition has correct mob count`() {
        //Given
        world(emptyFactory(), index<Mob>())
        addMobs(true, indexEntity(world.create()))
        //When
        val update = sync()
        //Then
        update.assertLocalCount(1, 1)
    }

    @Test
    fun `Remove single mob`() {
        //Given
        val factory = factory<RemoveMobSync>(local = true, mob = true)
        world(factory, index<Mob>())
        val mob = indexEntity(world.create())
        addMobs(true, mob)
        tick()
        factory.change(true)
        //When
        removeMobs(false, mob)
        val update = sync()
        //Then
        factory.assertCreated(1, mob)
        update.apply {
            assertLocalCount(1, 1)
            assertStage<RemoveMobSync>(2)
        }
    }

    @Test
    fun `Remove lots of mobs`() {
        //Given
        val factory = factory<RemoveMobSync>(local = true, mob = true)
        world(factory, index<Mob>())
        repeat(256) {
            addMobs(true, indexEntity(world.create()))
        }
        tick()
        factory.change(true)
        //When
        removeMobs(false, *(1..256).toList().toIntArray())
        val update = sync()
        //Then
        factory.assertCreated(1, entities.last())
        update.apply {
            assertLocalCount(1, 256)
            assertStages<RemoveMobSync>(2 until 258)
        }
    }

    @Test
    fun `Global gap adds up`() {
        //Given
        val factory = factory<AddMobSync>(local = false, mob = true)
        this.factory = factory
        world(factory, index<Mob>())
        indexEntity(world.create())
        indexEntity(-1)//A removed mob gap
        indexEntity(world.create())
        tick()
        factory.change(true)
        //When
        addMobs(false, 1, 2)
        val update = sync()
        //Then
        update.apply {
            assertLocalCount(1, 0)
            assertStage<AddMobSync>(2)
            assertStage<AddMobSync>(3)
        }
    }

    private fun MobUpdate.assertLocalCount(index: Int, size: Int) {
        Assertions.assertThat(assertStage<MobSizeStage>(index).mobCount).isEqualTo(size)
    }

}