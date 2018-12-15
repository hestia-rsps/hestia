package worlds.gregs.hestia.game.plugins.movement.systems.update

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.plugins.client.components.update.stage.EntityUpdates
import worlds.gregs.hestia.game.plugins.client.systems.update.sync.MobSyncSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Position.Companion.delta
import worlds.gregs.hestia.game.plugins.core.components.map.Position.Companion.regionDelta
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.types.Moving
import worlds.gregs.hestia.game.plugins.movement.components.types.RunStep
import worlds.gregs.hestia.game.plugins.movement.components.types.WalkStep
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_X
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.DELTA_Y
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.REGION_MOVEMENT
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getMobMoveDirection
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getPlayerRunningDirection
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getPlayerWalkingDirection
import worlds.gregs.hestia.game.update.DisplayFlag

@Wire(failOnNull = false)
class MovementUpdateHandlers : PassiveSystem() {
    private var playerSyncSystem: PlayerSyncSystem? = null
    private var mobSyncSystem: MobSyncSystem? = null
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var walkMapper: ComponentMapper<WalkStep>
    private lateinit var mobileMapper: ComponentMapper<Mobile>
    private lateinit var entityUpdatesMapper: ComponentMapper<EntityUpdates>
    private lateinit var movingMapper: ComponentMapper<Moving>

    override fun initialize() {
        super.initialize()

        playerSyncSystem?.addLocal(DisplayFlag.MOVE) { _, local ->
            val position = positionMapper.get(local)
            val mobile = mobileMapper.get(local)
            val global = !position.withinDistance(mobile, 15)
            delta(position, mobile) { deltaX, deltaY, deltaPlane ->
                writeBits(1, global.int)
                if (global) {
                    //Send global position
                    writeBits(30, (deltaY and 0x3fff) + (deltaX and 0x3fff shl 14) + (deltaPlane and 0x3 shl 28))
                } else {
                    //Send local position
                    val x = deltaX + if (deltaX < 0) 32 else 0
                    val y = deltaY + if (deltaY < 0) 32 else 0
                    writeBits(12, y + (x shl 5) + (deltaPlane shl 10))
                }
            }
        }

        playerSyncSystem?.addLocal(DisplayFlag.WALKING, DisplayFlag.RUNNING) { _, local ->
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
                writeBits(2, 0)//No movement
            } else {
                writeBits(2, running.int + 1)
                writeBits(running.int + 3, direction)//Direction
            }
        }

        playerSyncSystem?.addGlobal(DisplayFlag.HEIGHT) { player, global ->
            deltaUpdate(player, global) { _, _, plane ->
                writeBits(2, plane)
            }
        }
        playerSyncSystem?.addGlobal(DisplayFlag.REGION) { player, global ->
            deltaUpdate(player, global) { deltaX, deltaY, deltaPlane ->
                val direction = REGION_MOVEMENT[deltaX + 1][deltaY + 1]
                writeBits(5, (deltaPlane shl 3) + (direction and 0x7))
            }
        }
        playerSyncSystem?.addGlobal(DisplayFlag.MOVE) { player, global ->
            deltaUpdate(player, global) { deltaX, deltaY, deltaPlane ->
                writeBits(18, (deltaY and 0xff) + (deltaX and 0xff shl 8) + (deltaPlane shl 16))
            }
        }

        mobSyncSystem?.addMoving { global -> movingMapper.has(global) }

        mobSyncSystem?.addLocal(DisplayFlag.WALKING, DisplayFlag.RUNNING) { player, local ->
            val running = runMapper.has(player)
            val update = entityUpdatesMapper.get(player).list.contains(local)

            if (running) {
                writeBits(1, 1)
            }

            val nextWalkDirection = walkMapper.get(local).direction
            writeBits(3, getMobMoveDirection(nextWalkDirection))

            if (running) {
                val nextRunDirection = runMapper.get(local).direction
                writeBits(3, getMobMoveDirection(nextRunDirection))
            }

            writeBits(1, update.int)
        }
    }

    private fun deltaUpdate(player: Int, global: Int, action: (Int, Int, Int) -> Unit) {
        val viewport = viewportMapper.get(player)
        val position = positionMapper.get(global)
        val lastPosition = viewport.getPosition(global)

        //Calculate movement since last seen position
        regionDelta(position, lastPosition) { deltaX, deltaY, deltaPlane ->
            //Change
            action(deltaX, deltaY, deltaPlane)

            //Update
            viewport.updatePosition(global, position)
        }
    }
}