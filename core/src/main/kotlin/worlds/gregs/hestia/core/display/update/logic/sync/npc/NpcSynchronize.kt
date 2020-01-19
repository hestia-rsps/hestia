package worlds.gregs.hestia.core.display.update.logic.sync.npc

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.npc.factories.local.RemoveNpcSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.IdleNpcSync
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.NpcFinishStage
import worlds.gregs.hestia.core.display.update.logic.sync.npc.stages.NpcSizeStage
import worlds.gregs.hestia.core.display.update.model.sync.AccessStage
import worlds.gregs.hestia.network.client.encoders.messages.NpcUpdate

/**
 * Graphical Npc information (GNI) update packet creation
 */
open class NpcSynchronize : SynchronizeSystem<NpcUpdate>(true) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun sync(entity: Int): NpcUpdate {
        //We collect the data into two parts; update & insertion
        val update = NpcUpdate.create()

        val viewport = viewportMapper.get(entity)
        val npcs = viewport.localNpcs()

        //Process npcs
        processLocals(update, npcs, entity)
        processAdditions(update, npcs, entity)

        return update
    }

    private fun processLocals(update: NpcUpdate, locals: EntitySyncBag, entity: Int) {
        update.addStage(AccessStage.START)//Begin

        //Write local npc count
        update.addStage(NpcSizeStage.create(locals.size))
        locals.forEach { local ->
            //Calculate the view change type
            val change = getLocalSync(entity, locals, local)
            //Add sync stage to be encoded
            update.addStage(change?.create(entity, local, hasUpdateBlocks(local)) ?: IdleNpcSync)
            if (change != null && change !is RemoveNpcSyncFactory) {
                //Block update
                updateBlocks(update, entity, local, false)
            }
        }
    }

    private fun processAdditions(update: NpcUpdate, npcs: EntitySyncBag, entity: Int) {
        //Get all npcs to be added to viewport
        npcs.sync { global ->
            //Add add stage to be encoded
            update.addStage(globalChange!!.create(entity, global, hasUpdateBlocks(global)))
            //Additions require an update
            updateBlocks(update, entity, global, true)
        }

        update.addStage(NpcFinishStage)
        update.addStage(AccessStage.END)//End
    }
}