package worlds.gregs.hestia.game.update.sync

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.EventSystem
import worlds.gregs.hestia.GameConstants
import worlds.gregs.hestia.game.client.ClientIndex
import worlds.gregs.hestia.game.client.Viewport
import worlds.gregs.hestia.game.update.Synchronize
import worlds.gregs.hestia.game.update.sync.player.global.AddPlayerSyncFactory
import worlds.gregs.hestia.game.update.sync.player.local.RemovePlayerSyncFactory
import worlds.gregs.hestia.network.client.encoders.messages.PlayerUpdate
import worlds.gregs.hestia.network.update.sync.AccessStage
import worlds.gregs.hestia.network.update.sync.SkipStage
import worlds.gregs.hestia.network.update.sync.UpdateBlockStage
import worlds.gregs.hestia.services.players
import worlds.gregs.hestia.services.send

/**
 * Graphical player information (GPI) update packet creation
 */
class PlayerSynchronize : Synchronize(false) {

    private lateinit var es: EventSystem
    private lateinit var clientIndexMapper: ComponentMapper<ClientIndex>
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var playerIndices: Map<Int, Int>//Map of all entity id's & client indexes

    override fun begin() {
        //Indices only change at the end of a tick so we don't need to update for every entity
        playerIndices = world.players().map { Pair(clientIndexMapper.get(it).index, it) }.toMap()
    }

    override fun process(entityId: Int) {
        //We collect the data into two parts; sync & update blocks
        val stages = ArrayList<SyncStage>()
        val blocks = ArrayList<UpdateBlockStage>()

        val viewport = viewportMapper.get(entityId)
        //Keep track of all indices as we must update or skip all of them
        val indices = (1 until GameConstants.PLAYERS_LIMIT).toMutableList()

        processLocals(stages, blocks, indices, entityId, viewport, true)//Regular locals
        processLocals(stages, blocks, indices, entityId, viewport, false)//Added locals
        processGlobals(stages, blocks, indices, entityId, viewport, true)//Regular globals
        processGlobals(stages, blocks, indices, entityId, viewport, false)//Added globals

        es.send(entityId, PlayerUpdate(stages, blocks))

        viewport.shiftSlots()
    }

    private fun processLocals(stages: ArrayList<SyncStage>, blocks: ArrayList<UpdateBlockStage>, indices: MutableList<Int>, entityId: Int, viewport: Viewport, nsn0: Boolean) {
        stages.add(AccessStage(true))//Begin

        var skip = -1//Counter for the number of idle players skipped
        val iterator = viewport.localPlayers().iterator()

        while (iterator.hasNext()) {
            val local = iterator.next()
            val index = clientIndexMapper.get(local).index

            //Remove from list to keep track of processed indices
            indices.remove(index)

            //Skip if has idle flag
            if (nsn0 == viewport.isSlotBlocked(index)) {
                continue
            }

            //Calculate the view change type
            val change = getLocalSync(entityId, local)
            if (change == null) {
                //If no graphical change then skip & mark as idle
                skip++
                viewport.setIdle(index)
            } else {
                //Write number of idle players skipped
                skip = skip(stages, skip)

                //Add sync stage to be encoded
                stages.add(change.create(entityId, local, hasUpdateBlocks(local)))

                if (change is RemovePlayerSyncFactory) {
                    //Remove local player
                    iterator.remove()
                } else {
                    //Block update
                    updateBlocks(blocks, entityId, local, false)
                }
            }
        }

        //Write number of idle players skipped
        skip(stages, skip)
        //End
        stages.add(AccessStage(false))
    }

    private fun processGlobals(stages: ArrayList<SyncStage>, blocks: ArrayList<UpdateBlockStage>, indices: MutableList<Int>, entityId: Int, viewport: Viewport, nsn2: Boolean) {
        stages.add(AccessStage(true))//Begin

        var added = 0//Number of new local entities added
        var skip = -1//Counter for the number of idle players skipped
        val count = viewport.localPlayers().size
        val iterator = indices.iterator()

        while (iterator.hasNext()) {
            val index = iterator.next()
            val global = playerIndices[index]

            //Check if idle and can be skipped
            if (nsn2 == viewport.isSlotFree(index)) {
                continue
            }

            //Skip if too many players
            if(!withinLimits(added, count)) {
                skip++
                viewport.setIdle(index)
                continue
            }

            //Get graphical sync
            val change = getGlobalSync(entityId, global)
            //If no graphical change then skip & mark as idle
            if(change == null || global == null) {
                skip++
                viewport.setIdle(index)
                continue
            } else {
                //Write number of idle players skipped
                skip = skip(stages, skip)

                //Add sync stage to be encoded
                stages.add(change.create(entityId, global, false))

                if (change is AddPlayerSyncFactory) {
                    //Add as a local player & update
                    updateBlocks(blocks, entityId, global, true)
                    viewport.addLocalPlayer(global)
                    viewport.setIdle(index)
                    added++
                }
            }
        }

        //Write number of idle players skipped
        skip(stages, skip)
        //End
        stages.add(AccessStage(false))
    }

    private fun skip(stages: MutableList<SyncStage>, skip: Int): Int {
        //Write number of idle players skipped
        if (skip > -1) {
            stages.add(SkipStage(skip))
            return -1
        }
        return skip
    }

}