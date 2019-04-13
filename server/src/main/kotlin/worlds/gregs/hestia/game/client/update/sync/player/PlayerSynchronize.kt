package worlds.gregs.hestia.game.client.update.sync.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.GameConstants
import worlds.gregs.hestia.api.client.components.Viewport
import worlds.gregs.hestia.api.client.update.IndexSystem
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.game.client.update.sync.SynchronizeSystem
import worlds.gregs.hestia.network.client.encoders.messages.PlayerUpdate
import worlds.gregs.hestia.network.update.sync.AccessStage
import worlds.gregs.hestia.network.update.sync.SkipStage
import worlds.gregs.hestia.network.update.sync.player.AddPlayerSync
import worlds.gregs.hestia.network.update.sync.player.RemovePlayerSync

/**
 * Graphical player information (GPI) update packet creation
 */
open class PlayerSynchronize : SynchronizeSystem<PlayerUpdate>(false) {

    private lateinit var indexSystem: IndexSystem<Player>
    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun sync(entity: Int): PlayerUpdate {
        //We collect the data into two parts; sync & update blocks
        val update = PlayerUpdate.create()

        val viewport = viewportMapper.get(entity)
        val locals = viewport.localPlayers()

        //Process locals
        processLocals(update, entity, viewport, true)
        processLocals(update, entity, viewport, false)

        //Process globals
        processGlobals(update, entity, viewport, true)
        processGlobals(update, entity, viewport, false)

        //Apply additions & removals
        locals.sync()

        //Shift idle values
        viewport.shiftSlots()

        return update
    }

    private fun processLocals(update: PlayerUpdate, entity: Int, viewport: Viewport, nsn0: Boolean) {
        update.addStage(AccessStage.START)//Begin

        var skip = -1//Counter for the number of idle players skipped
        val locals = viewport.localPlayers()
        locals.forEachIndexed { index, local ->
            //Skip if has idle flag
            if (local == null || nsn0 == viewport.isSlotBlocked(index)) {
                return@forEachIndexed
            }

            //Calculate the view change type
            val change = getLocalSync(entity, locals, local)
            if (change == null) {
                //If no graphical change then skip & mark as idle
                skip++
                viewport.setIdle(index)
            } else {
                //Write number of idle players skipped
                if (skip > -1) {
                    update.addStage(SkipStage.create(skip))
                    skip = -1
                }

                //Add sync stage to be encoded
                val stage = change.create(entity, local, hasUpdateBlocks(local))
                update.addStage(stage)

                if (stage !is RemovePlayerSync) {
                    //Block update
                    updateBlocks(update, entity, local, false)
                }
            }
        }

        //Write number of idle players skipped
        if (skip > -1) {
            update.addStage(SkipStage.create(skip))
        }
        //End
        update.addStage(AccessStage.END)
    }

    private fun processGlobals(update: PlayerUpdate, entity: Int, viewport: Viewport, nsn2: Boolean) {
        update.addStage(AccessStage.START)//Begin

        var skip = -1//Counter for the number of idle players skipped
        val locals = viewport.localPlayers()
        for (index in 1 until GameConstants.PLAYERS_LIMIT) {
            //Ignore if local player
            if (locals.containsIndex(index)) {
                continue
            }

            val global = indexSystem.getId(index)

            //Check if idle and can be skipped
            if (nsn2 == viewport.isSlotFree(index)) {
                continue
            }

            //Skip if index has no player
            if (global == null) {
                skip++
                viewport.setIdle(index)
                continue
            }

            //Get graphical sync
            val change = getGlobalSync(entity, locals, global)
            //If no graphical change then skip & mark as idle
            if (change == null) {
                skip++
                viewport.setIdle(index)
                continue
            } else {
                //Write number of idle players skipped
                if (skip > -1) {
                    update.addStage(SkipStage.create(skip))
                    skip = -1
                }

                //Add sync stage to be encoded
                val stage = change.create(entity, global, false)//TODO shouldn't this be true?
                update.addStage(stage)

                if (stage is AddPlayerSync) {
                    //Add requires an update
                    updateBlocks(update, entity, global, true)
                    viewport.setIdle(index)
                }
            }
        }

        //Write number of idle players skipped
        if (skip > -1) {
            update.addStage(SkipStage.create(skip))
        }
        //End
        update.addStage(AccessStage.END)
    }

}