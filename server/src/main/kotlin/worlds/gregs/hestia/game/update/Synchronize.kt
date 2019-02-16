package worlds.gregs.hestia.game.update

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.entity.Created
import worlds.gregs.hestia.game.client.Viewport
import worlds.gregs.hestia.game.client.NetworkSession
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.sync.SyncFactory
import worlds.gregs.hestia.network.update.sync.UpdateBlockStage
import worlds.gregs.hestia.services.Aspect

abstract class Synchronize(private val mob: Boolean) : IteratingSystem(Aspect.all(NetworkSession::class, Viewport::class)) {

    private lateinit var blocks: List<BlockFactory<*>>
    private lateinit var localChanges: List<SyncFactory<*>>
    private lateinit var globalChanges: List<SyncFactory<*>>

    private lateinit var createdMapper: ComponentMapper<Created>

    override fun initialize() {
        super.initialize()
        val sync = world.systems.filterIsInstance<SyncFactory<*>>()
        val block = world.systems.filterIsInstance<BlockFactory<*>>()
        if(mob) {
            blocks = block.filter { it.mob }
            localChanges = sync.filter { it.local && it.mob }
            globalChanges = sync.filter { !it.local && it.mob }
        } else {
            blocks = block.filter { !it.mob }
            localChanges = sync.filter { it.local && !it.mob }
            globalChanges = sync.filter { !it.local && !it.mob }
        }
    }

    fun hasUpdateBlocks(entity: Int): Boolean {
        return blocks.any { it.subscription.entities.contains(entity) }
    }

    fun getLocalSync(entity: Int, local: Int): SyncFactory<*>? {
        return localChanges.firstOrNull { it.change(this, entity, local) }
    }

    fun getGlobalSync(entity: Int, global: Int?): SyncFactory<*>? {
        return if(global == null) null else globalChanges.firstOrNull { it.change(this, entity, global) }
    }

    private fun getUpdateBlocks(created: Boolean, entity: Int): List<BlockFactory<*>> {
        return blocks.filter { t -> (created && t.added) || t.subscription.entities.contains(entity) }
    }

    fun updateBlocks(blocks: ArrayList<UpdateBlockStage>, entity: Int, local: Int, added: Boolean) {
        //Is it the entities initial update?
        val created = createdMapper.has(entity) || added
        //Does the entity have any blocks that need updating?
        val types = getUpdateBlocks(created, local)
        //Return if not
        if (types.isEmpty()) {
            return
        }
        //Add sync
        blocks.add(UpdateBlockStage(types.map { it.create(entity, local) }, mob))
        //Remove created component
        createdMapper.remove(entity)
    }

    internal fun withinLimits(added: Int, count: Int): Boolean {
        return when {
            //Viewport cap
            added + count >= maximumEntities -> false
            //Number of entities added has to be capped due to maximum packet size
            added >= newEntitiesPerCycle -> false
            else -> true
        }
    }

    companion object {
        private const val newEntitiesPerCycle = 15
        private const val maximumEntities = 255
    }
}