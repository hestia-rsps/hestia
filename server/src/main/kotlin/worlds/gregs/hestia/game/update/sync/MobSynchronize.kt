package worlds.gregs.hestia.game.update.sync

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.client.Viewport
import worlds.gregs.hestia.api.mob.MobChunk
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.sync.mob.global.AddMobSyncFactory
import worlds.gregs.hestia.game.update.sync.mob.local.RemoveMobSyncFactory
import worlds.gregs.hestia.network.client.encoders.messages.MobUpdate
import worlds.gregs.hestia.network.update.sync.AccessStage
import worlds.gregs.hestia.network.update.sync.UpdateBlockStage
import worlds.gregs.hestia.network.update.sync.mob.IdleMobSync
import worlds.gregs.hestia.network.update.sync.mob.MobFinishStage
import worlds.gregs.hestia.network.update.sync.mob.MobSizeStage
import worlds.gregs.hestia.services.send

/**
 * Graphical NPC (mob) information (GNI) update packet creation
 */
class MobSynchronize : Synchronize(true) {

    private lateinit var es: EventSystem
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var chunkMapSystem: MobChunk

    override fun process(entityId: Int) {
        //We collect the data into two parts; sync & update blocks
        val stages = ArrayList<SyncStage>()
        val blocks = ArrayList<UpdateBlockStage>()

        val viewport = viewportMapper.get(entityId)
        //Process mobs
        processLocals(stages, blocks, entityId, viewport)
        processGlobals(stages, blocks, entityId, viewport)

        es.send(entityId, MobUpdate(stages, blocks))
    }

    private fun processLocals(stages: ArrayList<SyncStage>, blocks: ArrayList<UpdateBlockStage>, entity: Int, viewport: Viewport) {
        stages.add(AccessStage(true))//Begin

        val iterator = viewport.localMobs().iterator()
        //Write local mob count
        stages.add(MobSizeStage(viewport.localMobs().size))

        while (iterator.hasNext()) {
            val local = iterator.next()

            //Calculate the view change type
            val change = getLocalSync(entity, local)
            //Add sync stage to be encoded
            stages.add(change?.create(entity, local, hasUpdateBlocks(local)) ?: IdleMobSync())

            if (change is RemoveMobSyncFactory) {
                //Remove local mob
                iterator.remove()
            } else if (change != null) {
                //Block update
                updateBlocks(blocks, entity, local, false)
            }
        }
    }

    private fun processGlobals(stages: ArrayList<SyncStage>, blocks: ArrayList<UpdateBlockStage>, entity: Int, viewport: Viewport) {

        //Get all mobs outside of viewport
        val mobs = chunkMapSystem
                .get(positionMapper.get(entity))
                .filterNot { viewport.localMobs().contains(it) }
                .sorted()//Sort not required but makes loading nicer than square chunks
        var added = 0//Number of new local entities added
        val count = viewport.localMobs().size
        val iterator = mobs.iterator()

        while (iterator.hasNext()) {
            val global = iterator.next()

            //Stop if too many mobs
            if(!withinLimits(added, count)) {
                break
            }

            //Get graphical sync or skip
            val change = getGlobalSync(entity, global) ?: continue
            //Add sync stage to be encoded
            stages.add(change.create(entity, global, hasUpdateBlocks(global)))

            if (change is AddMobSyncFactory) {
                //Add as a local mob & update
                updateBlocks(blocks, entity, global, true)
                viewport.addLocalMob(global)
                added++
            }
        }

        stages.add(MobFinishStage())
        stages.add(AccessStage(false))//End
    }
}