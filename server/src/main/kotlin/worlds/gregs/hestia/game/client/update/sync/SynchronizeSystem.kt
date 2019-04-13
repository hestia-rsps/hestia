package worlds.gregs.hestia.game.client.update.sync

import com.artemis.ComponentMapper
import com.artemis.utils.BitVector
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.codec.message.Message
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.api.client.update.sync.SyncFactory
import worlds.gregs.hestia.api.client.update.sync.Synchronize
import worlds.gregs.hestia.artemis.bag.EntitySyncBag
import worlds.gregs.hestia.game.client.update.sync.mob.global.AddMobSyncFactory
import worlds.gregs.hestia.game.client.update.sync.player.global.AddPlayerSyncFactory
import worlds.gregs.hestia.game.entity.components.Created
import worlds.gregs.hestia.network.client.encoders.messages.Update
import worlds.gregs.hestia.network.update.sync.UpdateBlockStage
import worlds.gregs.hestia.services.forEach
import worlds.gregs.hestia.services.mobs
import worlds.gregs.hestia.services.players
import worlds.gregs.hestia.services.send

abstract class SynchronizeSystem<T : Message>(private val mob: Boolean) : Synchronize<T>() {

    private lateinit var blocks: List<BlockFactory<*>>
    private lateinit var localChanges: List<SyncFactory<*>>
    internal var globalChange: SyncFactory<*>? = null

    private lateinit var createdMapper: ComponentMapper<Created>
    private val activeUpdates = BitVector()
    private val updateBlocks = BitVector()
    private val entityUpdates = HashMap<Int, BitVector>()

    private lateinit var es: EventSystem

    override fun initialize() {
        super.initialize()
        //Add all the correct sync & block factories for this type of sync
        val sync = world.systems.filterIsInstance<SyncFactory<*>>().filter { it.active }
        val block = world.systems.filterIsInstance<BlockFactory<*>>()
        if (mob) {
            blocks = block.filter { it.mob }
            localChanges = sync.filter { it.local && it.mob }
            globalChange = sync.firstOrNull { !it.local && it.mob }
        } else {
            blocks = block.filter { !it.mob }
            localChanges = sync.filter { it.local && !it.mob }
            globalChange = sync.firstOrNull { !it.local && !it.mob }
        }

        //Create list of `added` update blocks
        updateBlocks.ensureCapacity(blocks.size)
        for (i in blocks.indices) {
            if (blocks[i].added) {
                updateBlocks.unsafeSet(i)
            }
        }
    }

    open fun isAddFactory(factory: SyncFactory<*>): Boolean {
        return if (mob) factory is AddMobSyncFactory else factory is AddPlayerSyncFactory
    }

    override fun begin() {
        //Clear last ticks updates
        //Note: we do at beginning rather than end so it's left open for tests
        entityUpdates.clear()
        activeUpdates.clear()
        //Check all entities for block updates
        val all = if (mob) world.mobs() else world.players()
        all.forEach { id ->
            var vector: BitVector? = null
            for ((index, block) in blocks.withIndex()) {
                if (block.subscription.activeEntityIds.get(id)) {
                    if (vector == null) {
                        vector = BitVector()
                    }
                    vector.set(index)
                }
            }
            //Mark as active if they have any block updates this tick
            if (vector != null) {
                entityUpdates[id] = vector
                activeUpdates.set(id)
            }
        }
    }

    override fun processAsync(entityId: Int) = GlobalScope.async {
        es.send(entityId, sync(entityId))
    }

    override fun hasUpdateBlocks(entity: Int): Boolean {
        return activeUpdates.get(entity)
    }

    override fun getLocalSync(entity: Int, bag: EntitySyncBag, local: Int): SyncFactory<*>? {
        //Check each local sync factory if it requires a change
        for (sync in localChanges) {
            if (sync.change(this, bag, local)) {
                return sync
            }
        }
        return null
    }

    override fun getGlobalSync(entity: Int, bag: EntitySyncBag, global: Int?): SyncFactory<*>? {
        //Check each global sync factory if it requires a change
        return if (global != null && globalChange?.change(this, bag, global) == true) {
            globalChange
        } else {
            null
        }
    }

    private fun applyBlocks(stage: UpdateBlockStage, updates: BitVector?, entity: Int, local: Int, added: Boolean) {
        for ((index, block) in blocks.withIndex()) {
            if (added && block.added || updates?.unsafeGet(index) == true) {
                stage.blocks.add(block.create(entity, local))
            }
        }
    }

    override fun updateBlocks(update: Update, entity: Int, local: Int, added: Boolean) {
        //Is it the entities initial update?
        val created = added || createdMapper.has(entity)
        //Stop if doesn't need an update update
        val updates = entityUpdates[local]
        if (!created && updates == null) {
            return
        }
        //Create the stage
        val stage = UpdateBlockStage.create(mob)
        //Add block updates
        applyBlocks(stage, updates, entity, local, added)
        //Add to update
        update.addBlock(stage)
        //Remove created component
        createdMapper.remove(entity)
    }

}