package world.gregs.hestia.game.systems.sync.player

import world.gregs.hestia.game.component.*
import world.gregs.hestia.game.systems.login.locationHash18Bit
import world.gregs.hestia.services.Aspect
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.int
import world.gregs.hestia.game.update.UpdateStage
import world.gregs.hestia.GameConstants
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.component.update.appearance.AppearanceData
import world.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_X
import world.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_Y
import world.gregs.hestia.game.update.DirectionUtils.Companion.REGION_MOVEMENT
import world.gregs.hestia.game.update.DirectionUtils.Companion.getPlayerRunningDirection
import world.gregs.hestia.game.update.DirectionUtils.Companion.getPlayerWalkingDirection
import world.gregs.hestia.services.send
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.systems.sync.player.PlayerViewDistanceSystem.Companion.MAXIMUM_LOCAL_PLAYERS

class PlayerSyncSystem : PlayerUpdateSystem(Aspect.all(NetworkSession::class, Renderable::class, Viewport::class, AppearanceData::class)) {

    private lateinit var es: EventSystem
    private lateinit var packet: Packet.Builder
    private lateinit var data: Packet.Builder
    private var skip = -1//Counter for the number of players to skip
    private var total = 0//Total number of players
    private var count = 0//Base number of players (with additions & removals)
    private var added = 0//Number of new local players added

    override fun begin(entityId: Int, locals: List<Int>) {
        //Reset
        total = 0
        count = 0
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
        total++

        //Update
        if (type != UpdateStage.SKIP && type != UpdateStage.REMOVE) {
            count++
            update(entityId, local, data, true, false)
        }
        //Sync
        if (type == UpdateStage.SKIP) {
            count++
            skip++
        } else {
            skipPlayers()
            updateLocalPlayer(type, entityId, local, update, iterator)
        }
    }

    override fun middle(entityId: Int, globals: List<Int>) {
        skipPlayers()

        packet.finishBitAccess()
        packet.startBitAccess()

        added = 0
    }

    override fun globalCheck(entityId: Int, global: Int): Boolean {
        return when {
            //Viewport cap
            added + count >= MAXIMUM_LOCAL_PLAYERS -> false
            //Number of players added has to be capped due to maximum packet size
            added >= PlayerSyncSystem.NEW_PLAYERS_PER_CYCLE -> false
            else -> true
        }
    }

    /**
     * Process other players
     */
    override fun global(entityId: Int, global: Int, type: UpdateStage) {
        total++

        //Update
        if (type == UpdateStage.ADD) {
            added++
            update(entityId, global, data, true, true)
        }
        //Sync
        if (type == UpdateStage.SKIP) {
            skip++
        } else {
            skipPlayers()
            if (world.entityManager.isActive(global)) {
                updateGlobalPlayer(type, entityId, global)
            }
        }
    }

    override fun end(entityId: Int) {
        //Skip remaining null players up to max player count
        skip += PlayerIndexSystem.PLAYERS_LIMIT - 1 - total

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
        packet.writeBits(1, if(type == UpdateStage.RUNNING && player == local) 1 else if (type == UpdateStage.REMOVE) 0 else update.int)//Is mask update needed?
        if (type != UpdateStage.WALKING && type != UpdateStage.RUNNING) {
            packet.writeBits(2, type.movementType())//Movement type (0 none, 1 walk, 2 run, 3 tele)
        }

        val position = positionMapper.get(local)
        val mobile = mobileMapper.get(local)
        when (type) {
            UpdateStage.REMOVE -> {
                val viewport = viewportMapper.get(player)
                if(mobile != null && mobile.lastX != -1) {
                    viewport.updateHash(local, Position.create(mobile.lastX, mobile.lastY, mobile.lastPlane))
                }
                sendMovementUpdate(player, local)
                iterator.remove()
            }
            UpdateStage.MOVE -> {
                val lastPosition = Position.create(mobile.lastX, mobile.lastY, mobile.lastPlane)
                val delta = Position.delta(position, lastPosition)
                val global = !position.withinDistance(lastPosition, 15)
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
            UpdateStage.WALKING, UpdateStage.RUNNING -> {
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

    private fun updateGlobalPlayer(type: UpdateStage, player: Int, global: Int) {
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
            }
            UpdateStage.HEIGHT -> {
                val delta = getDelta(viewport, position, global)
                packet.writeBits(2, delta.plane)
                viewport.updateHash(global, position)
            }
            UpdateStage.REGION -> {
                val delta = getDelta(viewport, position, global)
                val direction = REGION_MOVEMENT[delta.x + 1][delta.y + 1]
                packet.writeBits(5, (delta.plane shl 3) + (direction and 0x7))
                viewport.updateHash(global, position)
            }
            UpdateStage.MOVE -> {
                val delta = getDelta(viewport, position, global)
                packet.writeBits(18, (delta.y and 0xff) + (delta.x and 0xff shl 8) + (delta.plane shl 16))
                viewport.updateHash(global, position)
            }
            else -> {
            }
        }
    }

    private fun getDelta(viewport: Viewport, position: Position, other: Int): Position {
        val newHash = position.locationHash18Bit
        val oldHash = viewport.getHash(other)

        val new = Position.from(newHash)
        val old = Position.from(oldHash)

        return Position.delta(new, old)
    }

    private fun getUpdateType(player: Int, other: Int): UpdateStage {
        if(!positionMapper.has(other)) {
            return UpdateStage.SKIP
        }
        val delta = getDelta(viewportMapper.get(player), positionMapper.get(other), other)
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
        val updateType = getUpdateType(player, other)
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

    companion object {
        private const val NEW_PLAYERS_PER_CYCLE = 20
    }
}