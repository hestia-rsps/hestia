package worlds.gregs.hestia.core.world.movement.logic.strategies

import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.world.collision.api.Collision
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
    }

    override fun exit(currentX: Int, currentY: Int, sizeX: Int, sizeY: Int, clipBaseX: Int, clipBaseY: Int, collision: Collision?): Boolean {
        return when (routeType) {
            ObjectType.NORMAL -> checkWallInteract(collision, this.sizeX, 0, destinationY, currentY, currentX, destinationX, rotation, clipBaseX, clipBaseY)
            ObjectType.WALL_DECORATION -> checkWallDecorationInteract(collision, currentX, type, destinationX, destinationY, rotation, currentY, this.sizeX, clipBaseX, clipBaseY)
            ObjectType.FLOOR_DECORATION -> EntityStrategy.checkFilledRectangularInteract(collision, sizeX, destinationY, sizeY, currentX, destinationX, accessBlockFlag, this.sizeY, currentY, this.sizeX, clipBaseX, clipBaseY)
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

        fun checkWallInteract(collision: Collision?, sizeXY: Int, targetType: Int, targetY: Int, currentY: Int, currentX: Int, targetX: Int, rotation: Int, clipBaseX: Int, clipBaseY: Int): Boolean {
            if(collision == null) {
                return false
            }
            if (sizeXY != 1) {
                if (targetX >= currentX && currentX - 1 >= targetX && targetY <= -1 + targetY + sizeXY) {
                    return true
                }
            } else if (targetX == currentX && currentY == targetY) {
                return true
            }
            val currentX = currentX - clipBaseX
            val targetY = targetY - clipBaseY
            val targetX = targetX - clipBaseX
            val currentY = currentY - clipBaseY
            if (sizeXY == 1) {
                if (targetType == 0) {
                    if (rotation == 0) {
                        if (currentX == targetX - 1 && targetY == currentY) {
                            return true
                        }
                        if (currentX == targetX && 1 + targetY == currentY && collision.collides(currentX, currentY, 0x2c0120)) {
                            return true
                        }
                        if (targetX == currentX && currentY == targetY - 1 && collision.collides(currentX, currentY, 0x2c0102)) {
                            return true
                        }
                    } else if (rotation == 1) {
                        if (currentX == targetX && targetY + 1 == currentY) {
                            return true
                        }
                        if (-1 + targetX == currentX && targetY == currentY && collision.collides(currentX, currentY, 0x2c0108)) {
                            return true
                        }
                        if (targetX + 1 == currentX && currentY == targetY && collision.collides(currentX, currentY, 0x2c0180)) {
                            return true
                        }
                    } else if (rotation == 2) {
                        if (currentX == targetX + 1 && targetY == currentY) {
                            return true
                        }
                        if (currentX == targetX && 1 + targetY == currentY && collision.collides(currentX, currentY, 0x2c0120)) {
                            return true
                        }
                        if (currentX == targetX && -1 + targetY == currentY && collision.collides(currentX, currentY, 0x2c0102)) {
                            return true
                        }
                    } else if (rotation == 3) {
                        if (targetX == currentX && currentY == -1 + targetY) {
                            return true
                        }
                        if (targetX - 1 == currentX && currentY == targetY && collision.collides(currentX, currentY, 0x2c0108)) {
                            return true
                        }
                        if (currentX == targetX + 1 && targetY == currentY && collision.collides(currentX, currentY, 0x2c0180)) {
                            return true
                        }
                    }
                }
                if (targetType == 2) {
                    if (rotation == 0) {
                        if (-1 + targetX == currentX && currentY == targetY) {
                            return true
                        }
                        if (currentX == targetX && currentY == targetY + 1) {
                            return true
                        }
                        if (1 + targetX == currentX && targetY == currentY && collision.collides(currentX, currentY, 0x2c0180)) {
                            return true
                        }
                        if (currentX == targetX && -1 + targetY == currentY && collision.collides(currentX, currentY, 0x2c0102)) {
                            return true
                        }
                    } else if (rotation == 1) {
                        if (currentX == targetX - 1 && currentY == targetY && collision.collides(currentX, currentY, 0x2c0108)) {
                            return true
                        }
                        if (currentX == targetX && targetY + 1 == currentY) {
                            return true
                        }
                        if (currentX == 1 + targetX && targetY == currentY) {
                            return true
                        }
                        if (targetX == currentX && currentY == -1 + targetY && collision.collides(currentX, currentY, 0x2c0102)) {
                            return true
                        }
                    } else if (rotation == 2) {
                        if (-1 + targetX == currentX && currentY == targetY && collision.collides(currentX, currentY, 0x2c0108)) {
                            return true
                        }
                        if (targetX == currentX && currentY == 1 + targetY && collision.collides(currentX, currentY, 0x2c0120)) {
                            return true
                        }
                        if (targetX + 1 == currentX && currentY == targetY) {
                            return true
                        }
                        if (targetX == currentX && targetY - 1 == currentY) {
                            return true
                        }
                    } else if (rotation == 3) {
                        if (currentX == targetX - 1 && currentY == targetY) {
                            return true
                        }
                        if (targetX == currentX && currentY == targetY + 1 && collision.collides(currentX, currentY, 0x2c0120)) {
                            return true
                        }
                        if (1 + targetX == currentX && targetY == currentY && collision.collides(currentX, currentY, 0x2c0180)) {
                            return true
                        }
                        if (currentX == targetX && currentY == -1 + targetY) {
                            return true
                        }
                    }
                }
                if (targetType == 9) {
                    if (targetX == currentX && 1 + targetY == currentY && collision.collides(currentX, currentY, 0x20)) {
                        return true
                    }
                    if (currentX == targetX && targetY + -1 == currentY && collision.collides(currentX, currentY, 0x2)) {
                        return true
                    }
                    return if (-1 + targetX == currentX && currentY == targetY && collision.collides(currentX, currentY, 0x8)) {
                        true
                    } else 1 + targetX == currentX && currentY == targetY && collision.collides(currentX, currentY, 0x80)
                }
            } else {
                val sizeX = -1 + (sizeXY + currentX)
                val sizeY = -1 + sizeXY + currentY
                if (targetType == 0) {
                    if (rotation == 0) {
                        if (-sizeXY + targetX == currentX && currentY <= targetY && targetY <= sizeY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && 1 + targetY == currentY && collision.collides(targetX, currentY, 0x2c0120)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + -sizeXY && collision.collides(targetX, sizeY, 0x2c0102)) {
                            return true
                        }
                    } else if (rotation == 1) {
                        if (targetX in currentX..sizeX && currentY == targetY + 1) {
                            return true
                        }
                        if (-sizeXY + targetX == currentX && targetY >= currentY && sizeY >= targetY && collision.collides(sizeX, targetY, 0x2c0108)) {
                            return true
                        }
                        if (1 + targetX == currentX && currentY <= targetY && sizeY >= targetY && collision.collides(currentX, targetY, 0x2c0180)) {
                            return true
                        }
                    } else if (rotation == 2) {
                        if (currentX == 1 + targetX && currentY <= targetY && targetY <= sizeY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + 1 && collision.collides(targetX, currentY, 0x2c0120)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY - sizeXY == currentY && collision.collides(targetX, sizeY, 0x2c0102)) {
                            return true
                        }
                    } else if (rotation == 3) {
                        if (targetX in currentX..sizeX && currentY == targetY + -sizeXY) {
                            return true
                        }
                        if (-sizeXY + targetX == currentX && targetY >= currentY && sizeY >= targetY && collision.collides(sizeX, targetY, 0x2c0108)) {
                            return true
                        }
                        if (currentX == 1 + targetX && currentY <= targetY && sizeY >= targetY && collision.collides(currentX, targetY, 0x2c0180)) {
                            return true
                        }
                    }
                }
                if (targetType == 2) {
                    if (rotation == 0) {
                        if (targetX + -sizeXY == currentX && targetY >= currentY && sizeY >= targetY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == 1 + targetY) {
                            return true
                        }
                        if (currentX == targetX + 1 && targetY >= currentY && sizeY >= targetY && collision.collides(currentX, targetY, 0x2c0180)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && -sizeXY + targetY == currentY && collision.collides(targetX, sizeY, 0x2c0102)) {
                            return true
                        }
                    } else if (rotation == 1) {
                        if (-sizeXY + targetX == currentX && currentY <= targetY && targetY <= sizeY && collision.collides(sizeX, targetY, 0x2c0108)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY + 1 == currentY) {
                            return true
                        }
                        if (1 + targetX == currentX && targetY >= currentY && sizeY >= targetY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + -sizeXY && collision.collides(targetX, sizeY, 0x2c0102)) {
                            return true
                        }
                    } else if (rotation == 2) {
                        if (currentX == targetX + -sizeXY && targetY >= currentY && targetY <= sizeY && collision.collides(sizeX, targetY, 0x2c0108)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == 1 + targetY && collision.collides(targetX, currentY, 0x2c0120)) {
                            return true
                        }
                        if (targetX + 1 == currentX && targetY >= currentY && sizeY >= targetY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + -sizeXY) {
                            return true
                        }
                    } else if (rotation == 3) {
                        if (currentX == -sizeXY + targetX && currentY <= targetY && targetY <= sizeY) {
                            return true
                        }
                        if (targetX in currentX..sizeX && 1 + targetY == currentY && collision.collides(targetX, currentY, 0x2c0120)) {
                            return true
                        }
                        if (currentX == targetX + 1 && targetY >= currentY && targetY <= sizeY && collision.collides(currentX, targetY, 0x2c0180)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && targetY - sizeXY == currentY) {
                            return true
                        }
                    }
                }
                if (targetType == 9) {
                    if (targetX in currentX..sizeX && currentY == 1 + targetY && collision.collides(targetX, currentY, 0x2c0120)) {
                        return true
                    }
                    if (targetX in currentX..sizeX && -sizeXY + targetY == currentY && collision.collides(targetX, sizeY, 0x2c0102)) {
                        return true
                    }
                    return if (-sizeXY + targetX == currentX && targetY >= currentY && targetY <= sizeY && collision.collides(sizeX, targetY, 0x2c0108)) {
                        true
                    } else targetX + 1 == currentX && targetY >= currentY && sizeY >= targetY && collision.collides(currentX, targetY, 0x2c0180)
                }
            }
            return false
        }
        
        fun checkWallDecorationInteract(collision: Collision?, currentX: Int, targetType: Int, targetX: Int, targetY: Int, targetRotation: Int, currentY: Int, sizeXY: Int, clipBaseX: Int, clipBaseY: Int): Boolean {
            if(collision == null) {
                return false
            }
            var targetRotation = targetRotation
            if (sizeXY == 1) {
                if (targetX == currentX && currentY == targetY) {
                    return true
                }
            } else if (currentX <= targetX && -1 + sizeXY + currentX >= targetX && targetY <= -1 + sizeXY + targetY) {
                return true
            }
            val currentX = currentX - clipBaseX
            val targetY = targetY - clipBaseY
            val targetX = targetX - clipBaseX
            val currentY = currentY - clipBaseY
            if (sizeXY == 1) {
                if (targetType == 6 || targetType == 7) {
                    if (targetType == 7) {
                        targetRotation = 0x3 and targetRotation + 2
                    }
                    if (targetRotation == 0) {
                        if (currentX == 1 + targetX && currentY == targetY && collision.collides(currentX, currentY, 0x80)) {
                            return true
                        }
                        if (targetX == currentX && currentY == -1 + targetY && collision.collides(currentX, currentY, 0x2)) {
                            return true
                        }
                    } else if (targetRotation == 1) {
                        if (currentX == -1 + targetX && currentY == targetY && collision.collides(currentX, currentY, 0x8)) {
                            return true
                        }
                        if (targetX == currentX && currentY == -1 + targetY && collision.collides(currentX, currentY, 0x2)) {
                            return true
                        }
                    } else if (targetRotation == 2) {
                        if (currentX == targetX - 1 && targetY == currentY && collision.collides(currentX, currentY, 0x8)) {
                            return true
                        }
                        if (targetX == currentX && currentY == 1 + targetY && collision.collides(currentX, currentY, 0x20)) {
                            return true
                        }
                    } else if (targetRotation == 3) {
                        if (1 + targetX == currentX && currentY == targetY && collision.collides(currentX, currentY, 0x80)) {
                            return true
                        }
                        if (targetX == currentX && currentY == 1 + targetY && collision.collides(currentX, currentY, 0x20)) {
                            return true
                        }
                    }
                }
                if (targetType == 8) {
                    if (targetX == currentX && currentY == 1 + targetY && collision.collides(currentX, currentY, 0x20)) {
                        return true
                    }
                    if (currentX == targetX && targetY + -1 == currentY && collision.collides(currentX, currentY, 0x2)) {
                        return true
                    }
                    return if (currentX == targetX + -1 && targetY == currentY && collision.collides(currentX, currentY, 0x8)) {
                        true
                    } else targetX + 1 == currentX && targetY == currentY && collision.collides(currentX, currentY, 0x80)
                }
            } else {
                val sizeX = sizeXY + currentX - 1
                val sizeY = -1 + currentY + sizeXY
                if (targetType == 6 || targetType == 7) {
                    if (targetType == 7) {
                        targetRotation = targetRotation + 2 and 0x3
                    }
                    if (targetRotation == 0) {
                        if (currentX == targetX + 1 && currentY <= targetY && targetY <= sizeY && collision.collides(currentX, targetY, 0x80)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + -sizeXY && collision.collides(targetX, sizeY, 0x2)) {
                            return true
                        }
                    } else if (targetRotation == 1) {
                        if (currentX == targetX - sizeXY && targetY >= currentY && targetY <= sizeY && collision.collides(sizeX, targetY, 0x8)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == -sizeXY + targetY && collision.collides(targetX, sizeY, 0x2)) {
                            return true
                        }
                    } else if (targetRotation == 2) {
                        if (-sizeXY + targetX == currentX && targetY >= currentY && targetY <= sizeY && collision.collides(sizeX, targetY, 0x8)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && 1 + targetY == currentY && collision.collides(targetX, currentY, 0x20)) {
                            return true
                        }
                    } else if (targetRotation == 3) {
                        if (currentX == targetX + 1 && currentY <= targetY && targetY <= sizeY && collision.collides(currentX, targetY, 0x80)) {
                            return true
                        }
                        if (targetX in currentX..sizeX && currentY == targetY + 1 && collision.collides(targetX, currentY, 0x20)) {
                            return true
                        }
                    }
                }
                if (targetType == 8) {
                    if (targetX in currentX..sizeX && currentY == 1 + targetY && collision.collides(targetX, currentY, 0x20)) {
                        return true
                    }
                    if (targetX in currentX..sizeX && currentY == -sizeXY + targetY && collision.collides(targetX, sizeY, 0x2)) {
                        return true
                    }
                    return if (currentX == targetX + -sizeXY && currentY <= targetY && targetY <= sizeY && collision.collides(sizeX, targetY, 0x8)) {
                        true
                    } else currentX == 1 + targetX && currentY <= targetY && targetY <= sizeY && collision.collides(currentX, targetY, 0x80)
                }
            }
            return false
        }
    }
}