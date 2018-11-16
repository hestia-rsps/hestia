package world.gregs.hestia.game.systems.sync

import com.artemis.EntitySubscription
import com.artemis.utils.IntBag
import io.reactivex.rxkotlin.toSingle
import world.gregs.hestia.game.component.*
import world.gregs.hestia.game.systems.login.MapRegionSystem
import world.gregs.hestia.game.systems.login.locationHash18Bit
import world.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import world.gregs.hestia.game.update.UpdateStage
import world.gregs.hestia.GameConstants
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.component.update.AppearanceData
import world.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_X
import world.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_Y
import world.gregs.hestia.game.update.DirectionUtils.Companion.REGION_MOVEMENT
import world.gregs.hestia.game.update.DirectionUtils.Companion.getPlayerRunningDirection
import world.gregs.hestia.game.update.DirectionUtils.Companion.getPlayerWalkingDirection
import world.gregs.hestia.services.send
import world.gregs.hestia.game.component.map.Position

class PlayerSyncSystem : PlayerUpdateSystem(Aspect.all(NetworkSession::class, Renderable::class, Viewport::class, AppearanceData::class)) {

    private lateinit var es: EventSystem
    private var skip = -1//Counter for the number of players to skip
    private lateinit var packet: Packet.Builder
    private lateinit var data: Packet.Builder
    private var total = 0

    override fun begin(entityId: Int, locals: List<Int>) {
        //Reset
        total = locals.size
        skip = -1
        packet = Packet.Builder(69, Packet.Type.VAR_SHORT)
        data = Packet.Builder()

        //Start
        packet.startBitAccess()
    }

    /**
     * Process each local player
     */
    override fun local(entityId: Int, local: Int, type: UpdateStage, update: Boolean, iterator: MutableIterator<Int>) {
        //Update
        if (type != UpdateStage.SKIP && type != UpdateStage.REMOVE) {
            update(entityId, local, data, true, false)
        }
        //Sync
        if (type == UpdateStage.SKIP) {
            skip++
        } else {
            skipPlayers()
            updateLocalPlayer(type, entityId, local, update, iterator)
        }
    }

    override fun middle(entityId: Int, globals: List<Int>) {
        skipPlayers()

        total += globals.size

        packet.finishBitAccess()
        packet.startBitAccess()
    }

    /**
     * Process other players
     */
    override fun global(entityId: Int, global: Int, type: UpdateStage, iterator: MutableIterator<Int>) {
        //Update
        if (type == UpdateStage.ADD) {
            update(entityId, global, data, true, true)
        }
        //Sync
        if (type == UpdateStage.SKIP) {
            skip++
        } else {
            skipPlayers()
            if (world.entityManager.isActive(global)) {//TODO check
                updateGlobalPlayer(type, entityId, global, iterator)
            }
        }
    }

    override fun end(entityId: Int) {
        //Skip remaining null players up to max player count
        skip += GameConstants.PLAYERS_LIMIT - total

        skipPlayers()

        packet.finishBitAccess()

        //Write update data
        if (data.buffer.writerIndex() > 0) {
            packet.writeBytes(data.buffer)
        }

        es.send(entityId, packet)
    }

    private fun updateLocalPlayer(type: UpdateStage, player: Int, local: Int, update: Boolean, iterator: MutableIterator<Int>) {
        packet.writeBits(1, 1)//Needs update
        packet.writeBits(1, if (type == UpdateStage.REMOVE) 0 else update.int)//Is mask update needed?
        if (type != UpdateStage.WALKING && type != UpdateStage.RUNNING) {
            packet.writeBits(2, type.movementType())//Movement type (0 none, 1 walk, 2 run, 3 tele)
        }

        val position = positionMapper.get(local)
        val lastPosition = mobileMapper.get(local)?.lastPosition
        when (type) {
            UpdateStage.REMOVE -> {
                if (lastPosition != null) {
                    MapRegionSystem.updateHash(local, lastPosition)
                }
                sendMovementUpdate(player, local)
                iterator.remove()
            }
            UpdateStage.MOVE -> {
                val delta = Position.delta(position, lastPosition!!)
                val global = !Position.withinDistance(position, lastPosition)
                packet.writeBits(1, global.int)
                if (!global) {
                    //Send local position
                    val deltaX = delta.x + if (delta.x < 0) 32 else 0
                    val deltaY = delta.y + if (delta.y < 0) 32 else 0
                    packet.writeBits(12, deltaY + (deltaX shl 5) + (delta.plane shl 10))
                } else {
                    //Send global position
                    packet.writeBits(30, (delta.y and 0x3fff) + (delta.x and 0x3fff shl 14) + (delta.plane and 0x3 shl 28))
                }
            }
            UpdateStage.WALKING, UpdateStage.RUNNING -> {//Walking/Running
                val nextWalkDirection = walkMapper.get(local).direction

                //Calculate next step coordinates
                var dx = DELTA_X[nextWalkDirection]
                var dy = DELTA_Y[nextWalkDirection]

                var running = false
                var direction = -1
                //If running
                if (runMapper.has(local)) {
                    val nextRunDirection = runMapper.get(local).direction

                    //Add additional movement
                    dx += DELTA_X[nextRunDirection]
                    dy += DELTA_Y[nextRunDirection]

                    //Calculate direction
                    direction = getPlayerRunningDirection(dx, dy)
                    //Running if valid
                    if (direction != -1) {
                        running = true
                    }
                }

                //If not running calculate walking direction
                if (direction == -1) {
                    direction = getPlayerWalkingDirection(dx, dy)
                }

                //If no movement, no direction
                if (dx == 0 && dy == 0) {
                    direction = -1
                }

                if (direction == -1) {
                    packet.writeBits(2, 0)//No movement
                } else {
                    packet.writeBits(2, running.int + 1)
                    packet.writeBits(running.int + 3, direction)//Direction
                }
            }
            else -> {
            }
        }
    }

    private fun updateGlobalPlayer(type: UpdateStage, player: Int, global: Int, iterator: MutableIterator<Int>? = null) {
        val viewport = viewportMapper.get(player)!!
        packet.writeBits(1, 1)//Needs update
        packet.writeBits(2, type.movementType())//Movement type
        //Position
        val position = positionMapper.get(global) ?: return
        when (type) {
            UpdateStage.ADD -> {
                sendMovementUpdate(player, global)
                packet.writeBits(6, position.xInRegion)
                packet.writeBits(6, position.yInRegion)
                packet.writeBits(1, 1)//Update finished?
                viewport.addLocalPlayer(global)
                iterator?.remove()
            }
            UpdateStage.HEIGHT, UpdateStage.REGION, UpdateStage.MOVE -> {
                val oldHash = MapRegionSystem.getHash(global)
                val newHash = position.locationHash18Bit//Equivalent to regionX/Y
                val old = Position.from(oldHash)
                val new = Position.from(newHash)
                val delta = Position.delta(new, old)
                when (type) {
                    UpdateStage.HEIGHT -> {
                        packet.writeBits(2, delta.plane)
                    }
                    UpdateStage.REGION -> {
                        val direction = REGION_MOVEMENT[delta.x + 1][delta.y + 1]
                        packet.writeBits(5, (delta.plane shl 3) + (direction and 0x7))
                    }
                    UpdateStage.MOVE -> {
                        packet.writeBits(18, (delta.y and 0xff) + (delta.x and 0xff shl 8) + (delta.plane shl 16))
                    }
                    else -> {
                    }
                }
                MapRegionSystem.updateHash(global, position)
            }
            else -> {
            }
        }
    }

    private fun getUpdateType(player: Int): UpdateStage {
        val newHash = positionMapper.get(player)?.locationHash18Bit
                ?: return UpdateStage.SKIP
        val oldHash = MapRegionSystem.getHash(player)

        val new = Position.from(newHash)
        val old = Position.from(oldHash)

        val delta = Position.delta(old, new)

        return if (delta.x == 0 && delta.y == 0 && delta.plane == 0) {
            UpdateStage.SKIP//No update needed
        } else if (delta.x == 0 && delta.y == 0 && delta.plane != 0) {
            UpdateStage.HEIGHT//Change player plane
        } else if (delta.x == -1 || delta.y == -1 || delta.x == 1 || delta.y == 1) {
            UpdateStage.REGION//Move to adjacent region
        } else {
            UpdateStage.MOVE//Move to a non-adjacent region
        }
    }

    private fun sendMovementUpdate(player: Int, other: Int) {
        val updateType = getUpdateType(other)
        if (updateType != UpdateStage.SKIP) {
            //Move to global players
            updateGlobalPlayer(updateType, player, other)
        } else {
            //Remove
            packet.writeBits(1, 0)
        }
    }

    private fun skipPlayers() {
        if (skip > -1) {
            packet.writeBits(1, 0)//No update needed
            when {
                skip == 0 -> {
                    packet.writeBits(2, 0)
                }
                skip < 32 -> {
                    packet.writeBits(2, 1)
                    packet.writeBits(5, skip)
                }
                skip < 256 -> {
                    packet.writeBits(2, 2)
                    packet.writeBits(8, skip)
                }
                skip < 2048 -> {
                    packet.writeBits(2, 3)
                    packet.writeBits(11, skip)
                }
            }
        }
        skip = -1
    }
}