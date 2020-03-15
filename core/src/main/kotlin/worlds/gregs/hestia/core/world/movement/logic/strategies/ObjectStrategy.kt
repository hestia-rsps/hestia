package worlds.gregs.hestia.core.world.movement.logic.strategies

import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag.flag
import worlds.gregs.hestia.core.world.collision.model.CollisionFlag.wall
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import java.util.*

class ObjectStrategy(val type: Int, position: Position, val rotation: Int, sizeX: Int, sizeY: Int, var accessBlockFlag: Int) : RouteStrategy {

    internal enum class ObjectType {
        NORMAL,
        WALL_DECORATION,
        FLOOR_DECORATION,
        OTHER;
    }

    internal val routeType = getType(type) ?: throw UnsupportedOperationException("Unknown Object Type $position $type")

    init {
        if (rotation != 0) {
            accessBlockFlag = (accessBlockFlag shl rotation and 0xF) + (accessBlockFlag shr 4 - rotation)
        }
        println("Object strategy $routeType")
    }

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, collision: Collision?): Boolean {
        return when (routeType) {
            ObjectType.NORMAL -> checkWallInteract(collision, this.sizeX, 0, destinationY, currentY, currentX, destinationX, rotation)
            ObjectType.WALL_DECORATION -> checkWallDecorationInteract(collision, currentX, type, destinationX, destinationY, rotation, currentY, this.sizeX)
            ObjectType.FLOOR_DECORATION -> EntityStrategy.checkFilledRectangularInteract(collision, sizeX, destinationY, sizeY, currentX, destinationX, accessBlockFlag, this.sizeY, currentY, this.sizeX)
            ObjectType.OTHER -> currentX == destinationX && currentY == destinationY
        }
    }

    override val destinationX = position.x

    override val destinationY = position.y

    override val sizeX = if (rotation == 0 || rotation == 2) sizeX else sizeY

    override val sizeY = if (rotation == 0 || rotation == 2) sizeY else sizeX

    override fun equals(other: Any?): Boolean {
        val strategy = other as? ObjectStrategy ?: return false
        return destinationX == strategy.destinationX && destinationY == strategy.destinationY
    }

    override fun hashCode(): Int {
        return Objects.hash(destinationX, destinationY)
    }

    companion object {

        private fun getType(type: Int) = when(type) {
            4 -> ObjectType.WALL_DECORATION
            0, 10, 11, 22 -> ObjectType.NORMAL
            else -> null
            /*type in 0..3 || type == 9 -> ObjectType.NORMAL
            type < 9 -> ObjectType.WALL_DECORATION
            type == 10 || type == 11 || type == 22 -> ObjectType.FLOOR_DECORATION
            else -> ObjectType.OTHER*/
        }

        fun checkWallInteract(collision: Collision?, sizeXY: Int, targetType: Int, targetY: Int, currentY: Int, currentX: Int, targetX: Int, rotation: Int): Boolean {
            if(collision == null) {
                return false
            }
            if (sizeXY != 1) {
                if (targetX >= currentX && currentX - 1 >= targetX && targetY <= targetY + sizeXY - 1) {
                    return true
                }
            } else if (targetX == currentX && currentY == targetY) {
                return true
            }
            if (sizeXY == 1) {
                if (targetType == 0) {
                    val direction = Direction.cardinal[rotation + 3 and 0x3]
                    if(currentX == targetX + direction.deltaX && currentY == targetY + direction.deltaY) {
                        return true
                    }
                    val perpendicular = Direction.cardinal[rotation + 4 and 0x3]
                    if (currentX == targetX - perpendicular.deltaX && currentY == targetY - perpendicular.deltaY && !collision.collides(currentX, currentY, perpendicular.wall())) {
                        return true
                    }
                    val inverse = perpendicular.inverse()
                    if (currentX == targetX - inverse.deltaX && currentY == targetY - inverse.deltaY && !collision.collides(currentX, currentY, inverse.wall())) {
                        return true
                    }
                }
                if (targetType == 2) {
                    val direction = Direction.ordinal[rotation and 0x3]
                    val horizontal = direction.horizontal()
                    if(currentX == targetX + horizontal.deltaX && currentY == targetY) {
                        return true
                    }
                    val vertical = direction.vertical()
                    if(currentX == targetX && currentY == targetY + vertical.deltaY) {
                        return true
                    }
                    if(currentX == targetX - horizontal.deltaX && currentY == targetY && !collision.collides(currentX, currentY, horizontal.wall())) {
                        return true
                    }
                    if(currentX == targetX && currentY == targetY - vertical.deltaY && !collision.collides(currentX, currentY, vertical.wall())) {
                        return true
                    }
                }
                if (targetType == 9) {
                    Direction.ordinal.forEach { direction ->
                        if (currentX == targetX - direction.deltaX && currentY == targetY - direction.deltaY && !collision.collides(currentX, currentY, direction.flag())) {
                            return true
                        }
                    }
                    return false
                }
            } else {
                val sizeX = sizeXY + currentX - 1
                val sizeY = sizeXY + currentY - 1
                if (targetType == 0) {
                    if (rotation == 0) {
                        // Vertical
                        if (currentX == targetX - sizeXY && currentY <= targetY && sizeY >= targetY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + 1 && !collision.collides(targetX, currentY, Direction.SOUTH.wall())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY - sizeXY && !collision.collides(targetX, sizeY, Direction.NORTH.wall())) {
                            return true
                        }
                    } else if (rotation == 1) {
                        // Horizontal
                        if (currentY == targetY + 1 && currentX <= targetX && sizeX >= targetX) {
                            return true
                        }
                        if (currentX == targetX - sizeXY && targetY >= currentY && sizeY >= targetY && !collision.collides(sizeX, targetY, Direction.EAST.wall())) {
                            return true
                        }
                        if (currentX == targetX + 1 && currentY <= targetY && sizeY >= targetY && !collision.collides(currentX, targetY, Direction.WEST.wall())) {
                            return true
                        }
                    } else if (rotation == 2) {
                        // Vertical
                        if (currentX == targetX + 1 && currentY <= targetY && sizeY >= targetY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + 1 && !collision.collides(targetX, currentY, Direction.SOUTH.wall())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY - sizeXY == currentY && !collision.collides(targetX, sizeY, Direction.NORTH.wall())) {
                            return true
                        }
                    } else if (rotation == 3) {
                        // Horizontal
                        if (currentY == targetY - sizeXY && currentX <= targetX && sizeX >= targetX) {
                            return true
                        }
                        if (targetX - sizeXY == currentX && targetY >= currentY && sizeY >= targetY && !collision.collides(sizeX, targetY, Direction.EAST.wall())) {
                            return true
                        }
                        if (currentX == targetX + 1 && currentY <= targetY && sizeY >= targetY && !collision.collides(currentX, targetY, Direction.WEST.wall())) {
                            return true
                        }
                    }
                }
                if (targetType == 2) {
                    if (rotation == 0) {
                        if (targetX - sizeXY == currentX && targetY >= currentY && sizeY >= targetY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + 1) {
                            return true
                        }
                        if (currentX == targetX + 1 && targetY >= currentY && sizeY >= targetY && !collision.collides(currentX, targetY, Direction.WEST.wall())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY - sizeXY == currentY && !collision.collides(targetX, sizeY, Direction.NORTH.wall())) {
                            return true
                        }
                    } else if (rotation == 1) {
                        if (targetX - sizeXY == currentX && currentY <= targetY && targetY <= sizeY && !collision.collides(sizeX, targetY, Direction.EAST.wall())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY + 1 == currentY) {
                            return true
                        }
                        if (targetX + 1 == currentX && targetY >= currentY && sizeY >= targetY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY - sizeXY && !collision.collides(targetX, sizeY, Direction.NORTH.wall())) {
                            return true
                        }
                    } else if (rotation == 2) {
                        if (currentX == targetX - sizeXY && targetY >= currentY && targetY <= sizeY && !collision.collides(sizeX, targetY, Direction.EAST.wall())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + 1 && !collision.collides(targetX, currentY, Direction.SOUTH.wall())) {
                            return true
                        }
                        if (targetX + 1 == currentX && targetY >= currentY && sizeY >= targetY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY - sizeXY) {
                            return true
                        }
                    } else if (rotation == 3) {
                        if (currentX == targetX - sizeXY && currentY <= targetY && targetY <= sizeY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY + 1 == currentY && !collision.collides(targetX, currentY, Direction.SOUTH.wall())) {
                            return true
                        }
                        if (currentX == targetX + 1 && targetY >= currentY && targetY <= sizeY && !collision.collides(currentX, targetY, Direction.WEST.wall())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY - sizeXY == currentY) {
                            return true
                        }
                    }
                }
                if (targetType == 9) {
                    if (targetX in currentX..sizeX && currentY == targetY + 1 && !collision.collides(targetX, currentY, Direction.SOUTH.wall())) {
                        return true
                    }
                    if (targetX in currentX..sizeX && targetY - sizeXY == currentY && !collision.collides(targetX, sizeY, Direction.NORTH.wall())) {
                        return true
                    }
                    return if (targetX - sizeXY == currentX && targetY >= currentY && targetY <= sizeY && !collision.collides(sizeX, targetY, Direction.EAST.wall())) {
                        true
                    } else targetX + 1 == currentX && targetY >= currentY && sizeY >= targetY && !collision.collides(currentX, targetY, Direction.WEST.wall())
                }
            }
            return false
        }
        
        fun checkWallDecorationInteract(collision: Collision?, currentX: Int, targetType: Int, targetX: Int, targetY: Int, targetRotation: Int, currentY: Int, sizeXY: Int): Boolean {
            if(collision == null) {
                return false
            }
            var targetRotation = targetRotation
            if (sizeXY == 1) {
                if (targetX == currentX && currentY == targetY) {
                    return true
                }
            } else if (currentX <= targetX && sizeXY + currentX - 1 >= targetX && targetY <= sizeXY + targetY - 1) {
                return true
            }
            if (sizeXY == 1) {
                if (targetType == 6 || targetType == 7) {
                    if (targetType == 7) {
                        targetRotation = targetRotation + 2 and 0x3
                    }
                    if (targetRotation == 0) {
                        if (currentX == targetX + 1 && currentY == targetY && !collision.collides(currentX, currentY, Direction.WEST.flag())) {
                            return true
                        }
                        if (targetX == currentX && currentY == targetY - 1 && !collision.collides(currentX, currentY, Direction.NORTH.flag())) {
                            return true
                        }
                    } else if (targetRotation == 1) {
                        if (currentX == targetX - 1 && currentY == targetY && !collision.collides(currentX, currentY, Direction.EAST.flag())) {
                            return true
                        }
                        if (targetX == currentX && currentY == targetY - 1 && !collision.collides(currentX, currentY, Direction.NORTH.flag())) {
                            return true
                        }
                    } else if (targetRotation == 2) {
                        if (currentX == targetX - 1 && targetY == currentY && !collision.collides(currentX, currentY, Direction.EAST.flag())) {
                            return true
                        }
                        if (targetX == currentX && currentY == targetY + 1 && !collision.collides(currentX, currentY, Direction.SOUTH.flag())) {
                            return true
                        }
                    } else if (targetRotation == 3) {
                        if (targetX + 1 == currentX && currentY == targetY && !collision.collides(currentX, currentY, Direction.WEST.flag())) {
                            return true
                        }
                        if (targetX == currentX && currentY == targetY + 1 && !collision.collides(currentX, currentY, Direction.SOUTH.flag())) {
                            return true
                        }
                    }
                }
                if (targetType == 8) {
                    if (targetX == currentX && currentY == targetY + 1 && !collision.collides(currentX, currentY, Direction.SOUTH.flag())) {
                        return true
                    }
                    if (currentX == targetX && targetY - 1 == currentY && !collision.collides(currentX, currentY, Direction.NORTH.flag())) {
                        return true
                    }
                    return if (currentX == targetX - 1 && targetY == currentY && !collision.collides(currentX, currentY, Direction.EAST.flag())) {
                        true
                    } else targetX + 1 == currentX && targetY == currentY && !collision.collides(currentX, currentY, Direction.WEST.flag())
                }
            } else {
                val sizeX = sizeXY + currentX - 1
                val sizeY = currentY + sizeXY - 1
                if (targetType == 6 || targetType == 7) {
                    if (targetType == 7) {
                        targetRotation = targetRotation + 2 and 0x3
                    }
                    if (targetRotation == 0) {
                        if (currentX == targetX + 1 && currentY <= targetY && targetY <= sizeY && !collision.collides(currentX, targetY, Direction.WEST.flag())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY - sizeXY && !collision.collides(targetX, sizeY, Direction.NORTH.flag())) {
                            return true
                        }
                    } else if (targetRotation == 1) {
                        if (currentX == targetX - sizeXY && targetY >= currentY && targetY <= sizeY && !collision.collides(sizeX, targetY, Direction.EAST.flag())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY - sizeXY && !collision.collides(targetX, sizeY, Direction.NORTH.flag())) {
                            return true
                        }
                    } else if (targetRotation == 2) {
                        if (targetX - sizeXY == currentX && targetY >= currentY && targetY <= sizeY && !collision.collides(sizeX, targetY, Direction.EAST.flag())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY + 1 == currentY && !collision.collides(targetX, currentY, Direction.SOUTH.flag())) {
                            return true
                        }
                    } else if (targetRotation == 3) {
                        if (currentX == targetX + 1 && currentY <= targetY && targetY <= sizeY && !collision.collides(currentX, targetY, Direction.WEST.flag())) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + 1 && !collision.collides(targetX, currentY, Direction.SOUTH.flag())) {
                            return true
                        }
                    }
                }
                if (targetType == 8) {
                    if (targetX in currentX..sizeX && currentY == targetY + 1 && !collision.collides(targetX, currentY, Direction.SOUTH.flag())) {
                        return true
                    }
                    if (targetX in currentX..sizeX && currentY == targetY - sizeXY && !collision.collides(targetX, sizeY, Direction.NORTH.flag())) {
                        return true
                    }
                    return if (currentX == targetX - sizeXY && currentY <= targetY && targetY <= sizeY && !collision.collides(sizeX, targetY, Direction.EAST.flag())) {
                        true
                    } else currentX == targetX + 1 && currentY <= targetY && targetY <= sizeY && !collision.collides(currentX, targetY, Direction.WEST.flag())
                }
            }
            return false
        }
    }
}