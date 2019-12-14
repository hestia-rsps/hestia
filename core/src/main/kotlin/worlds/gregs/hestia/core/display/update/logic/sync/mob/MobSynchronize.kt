package worlds.gregs.hestia.core.display.update.logic.sync.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.bag.map.EntitySyncBag
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.logic.sync.SynchronizeSystem
import worlds.gregs.hestia.core.display.update.logic.sync.mob.factories.local.RemoveMobSyncFactory
import worlds.gregs.hestia.core.display.update.logic.sync.mob.stages.IdleMobSync
import worlds.gregs.hestia.core.display.update.logic.sync.mob.stages.MobFinishStage
import worlds.gregs.hestia.core.display.update.logic.sync.mob.stages.MobSizeStage
import worlds.gregs.hestia.core.display.update.model.sync.AccessStage
import worlds.gregs.hestia.network.client.encoders.messages.MobUpdate

/**
 * Graphical Mob (NPC) information (GNI) update packet creation
 */
open class MobSynchronize : SynchronizeSystem<MobUpdate>(true) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun sync(entity: Int): MobUpdate {
        //We collect the data into two parts; update & insertion
        val update = MobUpdate.create()

        val viewport = viewportMapper.get(entity)
        val mobs = viewport.localMobs()

        //Process mobs
        processLocals(update, mobs, entity)
        processAdditions(update, mobs, entity)

        return update
    }

    private fun processLocals(update: MobUpdate, locals: EntitySyncBag, entity: Int) {
        update.addStage(AccessStage.START)//Begin

        //Write local mob count
        update.addStage(MobSizeStage.create(locals.size))
        locals.forEach { local ->
            //Calculate the view change type
            val change = getLocalSync(entity, locals, local)
            //Add sync stage to be encoded
            update.addStage(change?.create(entity, local, hasUpdateBlocks(local)) ?: IdleMobSync)
            if (change != null && change !is RemoveMobSyncFactory) {
                //Block update
                updateBlocks(update, entity, local, false)
            }
        }
    }

    private fun processAdditions(update: MobUpdate, mobs: EntitySyncBag, entity: Int) {
        //Get all mobs to be added to viewport
        mobs.sync { global ->
            //Add add stage to be encoded
            update.addStage(globalChange!!.create(entity, global, hasUpdateBlocks(global)))
            //Additions require an update
            updateBlocks(update, entity, global, true)
        }

        update.addStage(MobFinishStage)
        update.addStage(AccessStage.END)//End
    }
}