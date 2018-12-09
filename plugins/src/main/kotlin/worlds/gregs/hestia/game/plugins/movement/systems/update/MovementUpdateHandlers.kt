package worlds.gregs.hestia.game.plugins.movement.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.game.plugins.client.components.update.stage.EntityUpdates
import worlds.gregs.hestia.game.plugins.client.systems.update.sync.MobSyncSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.types.Moving
import worlds.gregs.hestia.game.plugins.movement.components.types.RunStep
import worlds.gregs.hestia.game.plugins.movement.components.types.WalkStep
import worlds.gregs.hestia.game.update.DirectionUtils
import worlds.gregs.hestia.game.update.DirectionUtils.Companion.getMobMoveDirection
import worlds.gregs.hestia.game.update.DisplayFlag

class MovementUpdateHandlers : PassiveSystem() {
    private lateinit var playerSyncSystem: PlayerSyncSystem
    private lateinit var mobSyncSystem: MobSyncSystem
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var walkMapper: ComponentMapper<WalkStep>
    private lateinit var mobileMapper: ComponentMapper<Mobile>
    private lateinit var entityUpdatesMapper: ComponentMapper<EntityUpdates>
    private lateinit var movingMapper: ComponentMapper<Moving>

    override fun initialize() {
        super.initialize()

        playerSyncSystem.addLocal(DisplayFlag.MOVE) { _, local ->
            val position = positionMapper.get(local)
            val mobile = mobileMapper.get(local)
            val lastPosition = Position.create(mobile.lastX, mobile.lastY, mobile.lastPlane)
            val delta = Position.delta(position, lastPosition)
            val global = !position.withinDistance(lastPosition, 15)
            writeBits(1, global.int)
            if (!global) {
                //Send local position
                val deltaX = delta.x + if (delta.x < 0) 32 else 0
                val deltaY = delta.y + if (delta.y < 0) 32 else 0
                writeBits(12, deltaY + (deltaX shl 5) + (delta.plane shl 10))
            } else {
                //Send global position
                writeBits(30, (delta.y and 0x3fff) + (delta.x and 0x3fff shl 14) + (delta.plane and 0x3 shl 28))
            }
        }

        playerSyncSystem.addLocal(DisplayFlag.WALKING, DisplayFlag.RUNNING) { _, local ->
            val nextWalkDirection = walkMapper.get(local).direction

            //Calculate next step coordinates
            var dx = DirectionUtils.DELTA_X[nextWalkDirection]
            var dy = DirectionUtils.DELTA_Y[nextWalkDirection]

            var running = false
            var direction = -1
            //If running
            if (runMapper.has(local)) {
                val nextRunDirection = runMapper.get(local).direction

                //Add additional movement
                dx += DirectionUtils.DELTA_X[nextRunDirection]
                dy += DirectionUtils.DELTA_Y[nextRunDirection]

                //Calculate direction
                direction = DirectionUtils.getPlayerRunningDirection(dx, dy)
                //Running if valid
                if (direction != -1) {
                    running = true
                }
            }

            //If not running calculate walking direction
            if (direction == -1) {
                direction = DirectionUtils.getPlayerWalkingDirection(dx, dy)
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

        playerSyncSystem.addGlobal(DisplayFlag.HEIGHT) { player, global ->
            deltaUpdate(player, global) { delta ->
                writeBits(2, delta.plane)
            }
        }
        playerSyncSystem.addGlobal(DisplayFlag.REGION) { player, global ->
            deltaUpdate(player, global) { delta ->
                val direction = DirectionUtils.REGION_MOVEMENT[delta.x + 1][delta.y + 1]
                writeBits(5, (delta.plane shl 3) + (direction and 0x7))
            }
        }
        playerSyncSystem.addGlobal(DisplayFlag.MOVE) { player, global ->
            deltaUpdate(player, global) { delta ->
                writeBits(18, (delta.y and 0xff) + (delta.x and 0xff shl 8) + (delta.plane shl 16))
            }
        }

        mobSyncSystem.addMoving { global -> movingMapper.has(global) }

        mobSyncSystem.addLocal(DisplayFlag.WALKING, DisplayFlag.RUNNING) { player, local ->
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

    private fun deltaUpdate(player: Int, global: Int, action: (Position) -> Unit) {
        val viewport = viewportMapper.get(player)
        val position = positionMapper.get(global)
        val delta = getDelta(viewport, position, global)
        action(delta)
        viewport.updateHash(global, position)
    }

    private fun getDelta(viewport: Viewport, position: Position, other: Int): Position {
        val newHash = position.locationHash18Bit
        val oldHash = viewport.getHash(other)

        val new = Position.from(newHash)
        val old = Position.from(oldHash)

        return Position.delta(new, old)
    }
}