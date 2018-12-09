package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.annotations.Wire
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.api.map.Map
import worlds.gregs.hestia.game.api.region.Regions
import worlds.gregs.hestia.game.map.Flags
import worlds.gregs.hestia.game.path.RouteStrategy
import worlds.gregs.hestia.services.nearby

@Wire(failOnNull = false)
class RouteFinderSystem : PassiveSystem() {

    private var map: Map? = null
    private var regions: Regions? = null

    companion object {
        private const val GRAPH_SIZE = 128
        private const val QUEUE_SIZE = GRAPH_SIZE * GRAPH_SIZE / 4
        private const val ALTERNATIVE_ROUTE_MAX_DISTANCE = 100
        private const val ALTERNATIVE_ROUTE_RANGE = 10

        private const val DIR_NORTH = 0x1
        private const val DIR_EAST = 0x2
        private const val DIR_SOUTH = 0x4
        private const val DIR_WEST = 0x8
    }

    private val directions = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }
    private val distances = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }
    private val clip = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }
    /**
     * Get's last path buffer x. Modifying the buffer in any way is prohibited.
     */
    val lastPathBufferX = IntArray(QUEUE_SIZE)
    /**
     * Get's last path buffer y. Modifying the buffer in any way is prohibited.
     */
    val lastPathBufferY = IntArray(QUEUE_SIZE)
    private var exitX: Int = -1
    private var exitY: Int = -1
    private var isAlternative: Boolean = false

    /**
     * Find's route using given strategy. Returns amount of steps found. If
     * steps > 0, route exists. If steps = 0, route exists, but no need to move.
     * If steps < 0, route does not exist.
     */
    fun findRoute(srcX: Int, srcY: Int, srcZ: Int, sizeX: Int, sizeY: Int, strategy: RouteStrategy, findAlternative: Boolean): Int {
        //Reset previous data
        isAlternative = false
        for (x in 0 until GRAPH_SIZE) {
            for (y in 0 until GRAPH_SIZE) {
                directions[x][y] = 0
                distances[x][y] = 99999999
            }
        }

        //Copy region clipping data
        transmitClipData(srcX, srcY, srcZ)


        val found = when (Math.max(sizeX, sizeY)) {
            1 -> performCalculationS1(srcX, srcY, strategy)
            else -> performCalculationSX(srcX, srcY, sizeX, sizeY, strategy)
        }

        if (!found && !findAlternative) {
            return -1
        }

        //Start in centre
        val graphBaseX = srcX - GRAPH_SIZE / 2
        val graphBaseY = srcY - GRAPH_SIZE / 2
        var endX = exitX
        var endY = exitY

        if (!found && findAlternative) {
            isAlternative = true
            var lowestCost = Integer.MAX_VALUE
            var lowestDistance = Integer.MAX_VALUE

            val approxDestX = strategy.destinationX
            val approxDestY = strategy.destinationY

            // what we will do here is search the coordinates range of
            // destination +- ALTERNATIVE_ROUTE_RANGE
            // to see if at least one position in that range is reachable, and
            // reaching it takes no longer than ALTERNATIVE_ROUTE_MAX_DISTANCE
            // steps.
            // if we have multiple positions in our range that fits all the
            // conditions, we will choose the one which takes fewer steps.

            for (checkX in approxDestX.nearby(ALTERNATIVE_ROUTE_RANGE)) {
                for (checkY in approxDestY.nearby(ALTERNATIVE_ROUTE_RANGE)) {
                    val graphX = checkX - graphBaseX
                    val graphY = checkY - graphBaseY
                    if (graphX < 0 || graphY < 0 || graphX >= GRAPH_SIZE || graphY >= GRAPH_SIZE || distances[graphX][graphY] >= ALTERNATIVE_ROUTE_MAX_DISTANCE) {
                        continue // we are out of graph's bounds or too many steps
                    }

                    //Calculate deltas using strategy
                    val deltaX = if (approxDestX <= checkX) {
                        1 - approxDestX - (strategy.sizeX - checkX)
                    } else {
                        approxDestX - checkX
                    }

                    val deltaY = if (approxDestY <= checkY) {
                        1 - approxDestY - (strategy.sizeY - checkY)
                    } else {
                        approxDestY - checkY
                    }

                    val cost = deltaX * deltaX + deltaY * deltaY
                    if (cost < lowestCost || cost <= lowestCost && distances[graphX][graphY] < lowestDistance) {
                        //If the cost is lower than the lowest one, or same as the lowest one, but less steps, we accept this position as alternate.
                        lowestCost = cost
                        lowestDistance = distances[graphX][graphY]
                        endX = checkX
                        endY = checkY
                    }
                }
            }

            if (lowestCost == Integer.MAX_VALUE || lowestDistance == Integer.MAX_VALUE) {
                return -1//No alternative route found
            }
        }

        if (endX == srcX && endY == srcY) {
            return 0//Path found but no movement
        }

        //Trace path in reverse for best route
        var steps = 0
        var traceX = endX
        var traceY = endY
        var direction = directions[traceX - graphBaseX][traceY - graphBaseY]
        var lastwritten = direction
        //Queue destination position and start tracing from it
        lastPathBufferX[steps] = traceX
        lastPathBufferY[steps++] = traceY
        while (traceX != srcX || traceY != srcY) {
            //Direction changed
            if (lastwritten != direction) {
                lastPathBufferX[steps] = traceX
                lastPathBufferY[steps++] = traceY
                lastwritten = direction
            }

            if (direction and DIR_EAST != 0) {
                traceX++
            } else if (direction and DIR_WEST != 0) {
                traceX--
            }

            if (direction and DIR_NORTH != 0) {
                traceY++
            } else if (direction and DIR_SOUTH != 0) {
                traceY--
            }

            direction = directions[traceX - graphBaseX][traceY - graphBaseY]
        }

        return steps
    }

    /**
     * Perform's size 1 calculations.
     */
    private fun performCalculationS1(srcX: Int, srcY: Int, strategy: RouteStrategy): Boolean {
        //Cache static fields for jit compiler performance boost
        val directions = directions
        val distances = distances
        val clip = clip
        val bufferX = lastPathBufferX
        val bufferY = lastPathBufferY

        //Start in centre
        val graphBaseX = srcX - GRAPH_SIZE / 2
        val graphBaseY = srcY - GRAPH_SIZE / 2
        var currentX = srcX
        var currentY = srcY
        var currentGraphX = srcX - graphBaseX
        var currentGraphY = srcY - graphBaseY

        //Source tile info
        distances[currentGraphX][currentGraphY] = 0
        directions[currentGraphX][currentGraphY] = 99


        var read = 0
        var write = 0
        //Insert current position as first
        bufferX[write] = currentX
        bufferY[write++] = currentY

        while (read != write) {
            currentX = bufferX[read]
            currentY = bufferY[read]
            read = read + 1 and QUEUE_SIZE - 1

            currentGraphX = currentX - graphBaseX
            currentGraphY = currentY - graphBaseY

            //Check if path is complete
            if (strategy.exit(currentX, currentY, 1, 1, clip, graphBaseX, graphBaseY)) {
                exitX = currentX
                exitY = currentY
                return true
            }

            //Check where we can go next

            val nextDistance = distances[currentGraphX][currentGraphY] + 1
            if (currentGraphX > 0 && directions[currentGraphX - 1][currentGraphY] == 0
                    && clip[currentGraphX - 1][currentGraphY] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                //Can go west
                bufferX[write] = currentX - 1
                bufferY[write] = currentY
                write = write + 1 and QUEUE_SIZE - 1

                directions[currentGraphX - 1][currentGraphY] = DIR_EAST
                distances[currentGraphX - 1][currentGraphY] = nextDistance
            }
            if (currentGraphX < GRAPH_SIZE - 1 && directions[currentGraphX + 1][currentGraphY] == 0
                    && clip[currentGraphX + 1][currentGraphY] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                //Can go east
                bufferX[write] = currentX + 1
                bufferY[write] = currentY
                write = write + 1 and QUEUE_SIZE - 1

                directions[currentGraphX + 1][currentGraphY] = DIR_WEST
                distances[currentGraphX + 1][currentGraphY] = nextDistance
            }
            if (currentGraphY > 0 && directions[currentGraphX][currentGraphY - 1] == 0
                    && clip[currentGraphX][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE) == 0) {
                //Can go south
                bufferX[write] = currentX
                bufferY[write] = currentY - 1
                write = write + 1 and QUEUE_SIZE - 1

                directions[currentGraphX][currentGraphY - 1] = DIR_NORTH
                distances[currentGraphX][currentGraphY - 1] = nextDistance
            }
            if (currentGraphY < GRAPH_SIZE - 1 && directions[currentGraphX][currentGraphY + 1] == 0
                    && clip[currentGraphX][currentGraphY + 1] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE) == 0) {
                //Can go north
                bufferX[write] = currentX
                bufferY[write] = currentY + 1
                write = write + 1 and QUEUE_SIZE - 1

                directions[currentGraphX][currentGraphY + 1] = DIR_SOUTH
                distances[currentGraphX][currentGraphY + 1] = nextDistance
            }

            //Diagonal checks
            if (currentGraphX > 0 && currentGraphY > 0 && directions[currentGraphX - 1][currentGraphY - 1] == 0
                    && clip[currentGraphX - 1][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX - 1][currentGraphY] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE) == 0) {
                //Can go south-west
                bufferX[write] = currentX - 1
                bufferY[write] = currentY - 1
                write = write + 1 and QUEUE_SIZE - 1

                directions[currentGraphX - 1][currentGraphY - 1] = DIR_NORTH or DIR_EAST
                distances[currentGraphX - 1][currentGraphY - 1] = nextDistance
            }
            if (currentGraphX < GRAPH_SIZE - 1 && currentGraphY > 0
                    && directions[currentGraphX + 1][currentGraphY - 1] == 0
                    && clip[currentGraphX + 1][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX + 1][currentGraphY] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE) == 0) {
                //Can go south-east
                bufferX[write] = currentX + 1
                bufferY[write] = currentY - 1
                write = write + 1 and QUEUE_SIZE - 1

                directions[currentGraphX + 1][currentGraphY - 1] = DIR_NORTH or DIR_WEST
                distances[currentGraphX + 1][currentGraphY - 1] = nextDistance
            }
            if (currentGraphX > 0 && currentGraphY < GRAPH_SIZE - 1
                    && directions[currentGraphX - 1][currentGraphY + 1] == 0
                    && clip[currentGraphX - 1][currentGraphY + 1] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX - 1][currentGraphY] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX][currentGraphY + 1] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE) == 0) {
                //Can go north-west
                bufferX[write] = currentX - 1
                bufferY[write] = currentY + 1
                write = write + 1 and QUEUE_SIZE - 1

                directions[currentGraphX - 1][currentGraphY + 1] = DIR_SOUTH or DIR_EAST
                distances[currentGraphX - 1][currentGraphY + 1] = nextDistance
            }
            if (currentGraphX < GRAPH_SIZE - 1 && currentGraphY < GRAPH_SIZE - 1
                    && directions[currentGraphX + 1][currentGraphY + 1] == 0
                    && clip[currentGraphX + 1][currentGraphY + 1] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX + 1][currentGraphY] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX][currentGraphY + 1] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE) == 0) {
                //Can go north-east
                bufferX[write] = currentX + 1
                bufferY[write] = currentY + 1
                write = write + 1 and QUEUE_SIZE - 1

                directions[currentGraphX + 1][currentGraphY + 1] = DIR_SOUTH or DIR_WEST
                distances[currentGraphX + 1][currentGraphY + 1] = nextDistance
            }

        }

        exitX = currentX
        exitY = currentY
        return false
    }

    /**
     * Perform's size x calculations.
     */
    private fun performCalculationSX(srcX: Int, srcY: Int, sizeX: Int, sizeY: Int, strategy: RouteStrategy): Boolean {
        //Cache static fields for jit compiler performance boost
        val directions = directions
        val distances = distances
        val clip = clip
        val bufferX = lastPathBufferX
        val bufferY = lastPathBufferY

        //Start in centre
        val graphBaseX = srcX - GRAPH_SIZE / 2
        val graphBaseY = srcY - GRAPH_SIZE / 2
        var currentX = srcX
        var currentY = srcY
        var currentGraphX = srcX - graphBaseX
        var currentGraphY = srcY - graphBaseY

        //Source tile info
        distances[currentGraphX][currentGraphY] = 0
        directions[currentGraphX][currentGraphY] = 99

        var read = 0
        var write = 0
        //Insert current position as first
        bufferX[write] = currentX
        bufferY[write++] = currentY

        while (read != write) {
            currentX = bufferX[read]
            currentY = bufferY[read]
            read = read + 1 and QUEUE_SIZE - 1

            currentGraphX = currentX - graphBaseX
            currentGraphY = currentY - graphBaseY

            //Check if path is complete
            if (strategy.exit(currentX, currentY, sizeX, sizeY, clip, graphBaseX, graphBaseY)) {
                exitX = currentX
                exitY = currentY
                return true
            }

            //Check where we can go next

            val nextDistance = distances[currentGraphX][currentGraphY] + 1
            if (currentGraphX > 0 && directions[currentGraphX - 1][currentGraphY] == 0
                    && clip[currentGraphX - 1][currentGraphY] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX - 1][currentGraphY + (sizeY - 1)] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                exit@ do {
                    for (y in 1 until sizeY - 1) {
                        if (clip[currentGraphX - 1][currentGraphY + y] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE) != 0)
                            break@exit
                    }
                    //Can go west
                    bufferX[write] = currentX - 1
                    bufferY[write] = currentY
                    write = write + 1 and QUEUE_SIZE - 1

                    directions[currentGraphX - 1][currentGraphY] = DIR_EAST
                    distances[currentGraphX - 1][currentGraphY] = nextDistance
                } while (false)
            }
            if (currentGraphX < GRAPH_SIZE - sizeX && directions[currentGraphX + 1][currentGraphY] == 0
                    && clip[currentGraphX + sizeX][currentGraphY] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX + sizeX][currentGraphY + (sizeY - 1)] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                exit@ do {
                    for (y in 1 until sizeY - 1) {
                        if (clip[currentGraphX + sizeX][currentGraphY + y] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) != 0)
                            break@exit
                    }
                    //Can go east
                    bufferX[write] = currentX + 1
                    bufferY[write] = currentY
                    write = write + 1 and QUEUE_SIZE - 1

                    directions[currentGraphX + 1][currentGraphY] = DIR_WEST
                    distances[currentGraphX + 1][currentGraphY] = nextDistance
                } while (false)
            }
            if (currentGraphY > 0 && directions[currentGraphX][currentGraphY - 1] == 0
                    && clip[currentGraphX][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK or Flags.FLOOR_DECO_BLOCKS_WALK
                            or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX + (sizeX - 1)][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                exit@ do {
                    for (y in 1 until sizeY - 1) {
                        if (clip[currentGraphX + y][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE) != 0)
                            break@exit
                    }
                    //Can go south
                    bufferX[write] = currentX
                    bufferY[write] = currentY - 1
                    write = write + 1 and QUEUE_SIZE - 1

                    directions[currentGraphX][currentGraphY - 1] = DIR_NORTH
                    distances[currentGraphX][currentGraphY - 1] = nextDistance
                } while (false)
            }
            if (currentGraphY < GRAPH_SIZE - sizeY && directions[currentGraphX][currentGraphY + 1] == 0
                    && clip[currentGraphX][currentGraphY + sizeY] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE) == 0
                    && clip[currentGraphX + (sizeX - 1)][currentGraphY + sizeY] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                exit@ do {
                    for (y in 1 until sizeY - 1) {
                        if (clip[currentGraphX + y][currentGraphY + sizeY] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) != 0)
                            break@exit
                    }
                    //Can go north
                    bufferX[write] = currentX
                    bufferY[write] = currentY + 1
                    write = write + 1 and QUEUE_SIZE - 1

                    directions[currentGraphX][currentGraphY + 1] = DIR_SOUTH
                    distances[currentGraphX][currentGraphY + 1] = nextDistance
                } while (false)
            }

            //Diagonal checks
            if (currentGraphX > 0 && currentGraphY > 0 && directions[currentGraphX - 1][currentGraphY - 1] == 0
                    && clip[currentGraphX - 1][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                exit@ do {
                    for (y in 1 until sizeY) {
                        if (clip[currentGraphX - 1][currentGraphY + (y - 1)] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE) != 0 || clip[currentGraphX + (y - 1)][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE) != 0)
                            break@exit
                    }
                    //Can go south-west
                    bufferX[write] = currentX - 1
                    bufferY[write] = currentY - 1
                    write = write + 1 and QUEUE_SIZE - 1

                    directions[currentGraphX - 1][currentGraphY - 1] = DIR_NORTH or DIR_EAST
                    distances[currentGraphX - 1][currentGraphY - 1] = nextDistance
                } while (false)
            }
            if (currentGraphX < GRAPH_SIZE - sizeX && currentGraphY > 0
                    && directions[currentGraphX + 1][currentGraphY - 1] == 0
                    && clip[currentGraphX + sizeX][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                exit@ do {
                    for (y in 1 until sizeY) {
                        if (clip[currentGraphX + sizeX][currentGraphY + (y - 1)] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) != 0 || clip[currentGraphX + y][currentGraphY - 1] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE) != 0)
                            break@exit
                    }
                    //Can go south-east
                    bufferX[write] = currentX + 1
                    bufferY[write] = currentY - 1
                    write = write + 1 and QUEUE_SIZE - 1

                    directions[currentGraphX + 1][currentGraphY - 1] = DIR_NORTH or DIR_WEST
                    distances[currentGraphX + 1][currentGraphY - 1] = nextDistance
                } while (false)
            }
            if (currentGraphX > 0 && currentGraphY < GRAPH_SIZE - sizeY
                    && directions[currentGraphX - 1][currentGraphY + 1] == 0
                    && clip[currentGraphX - 1][currentGraphY + sizeY] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                exit@ do {
                    for (y in 1 until sizeY) {
                        if (clip[currentGraphX - 1][currentGraphY + y] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE) != 0 || clip[currentGraphX + (y - 1)][currentGraphY + sizeY] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) != 0)
                            break@exit
                    }
                    //Can go north-west
                    bufferX[write] = currentX - 1
                    bufferY[write] = currentY + 1
                    write = write + 1 and QUEUE_SIZE - 1

                    directions[currentGraphX - 1][currentGraphY + 1] = DIR_SOUTH or DIR_EAST
                    distances[currentGraphX - 1][currentGraphY + 1] = nextDistance
                } while (false)
            }
            if (currentGraphX < GRAPH_SIZE - sizeX && currentGraphY < GRAPH_SIZE - sizeY
                    && directions[currentGraphX + 1][currentGraphY + 1] == 0
                    && clip[currentGraphX + sizeX][currentGraphY + sizeY] and (Flags.FLOOR_BLOCKS_WALK
                            or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                            or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                            or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) == 0) {
                exit@ do {
                    for (y in 1 until sizeY) {
                        if (clip[currentGraphX + y][currentGraphY + sizeY] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_EAST_BLOCKS_WALK_ALTERNATIVE or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_EAST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) != 0 || clip[currentGraphX + sizeX][currentGraphY + y] and (Flags.FLOOR_BLOCKS_WALK
                                        or Flags.FLOOR_DECO_BLOCKS_WALK or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_NORTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_SOUTH_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.WALL_OBJ_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_NORTH_WEST_BLOCKS_WALK_ALTERNATIVE
                                        or Flags.CORNER_OBJ_SOUTH_WEST_BLOCKS_WALK_ALTERNATIVE) != 0)
                            break@exit
                    }
                    //Can go north-east
                    bufferX[write] = currentX + 1
                    bufferY[write] = currentY + 1
                    write = write + 1 and QUEUE_SIZE - 1

                    directions[currentGraphX + 1][currentGraphY + 1] = DIR_SOUTH or DIR_WEST
                    distances[currentGraphX + 1][currentGraphY + 1] = nextDistance
                } while (false)
            }

        }

        exitX = currentX
        exitY = currentY
        return false
    }

    /**
     * Transmit's clip data to route finder buffers.
     */
    private fun transmitClipData(x: Int, y: Int, z: Int) {
        val graphBaseX = x - GRAPH_SIZE / 2
        val graphBaseY = y - GRAPH_SIZE / 2
        //If map plugin isn't loaded allow no-clip, otherwise no movement
        val default = if (map == null) 0 else -1
        for (transmitRegionX in (graphBaseX shr 6)..(graphBaseX + (GRAPH_SIZE - 1) shr 6)) {
            for (transmitRegionY in (graphBaseY shr 6)..(graphBaseY + (GRAPH_SIZE - 1) shr 6)) {
                val startX = Math.max(graphBaseX, transmitRegionX shl 6)
                val startY = Math.max(graphBaseY, transmitRegionY shl 6)
                val endX = Math.min(graphBaseX + GRAPH_SIZE, (transmitRegionX shl 6) + 64)
                val endY = Math.min(graphBaseY + GRAPH_SIZE, (transmitRegionY shl 6) + 64)
                val regionId = transmitRegionX shl 8 or transmitRegionY
                val clipping = map?.getClipping(regions?.getEntityId(regionId))
                if (clipping != null) {
                    val masks = clipping.getMasks(z)
                    for (fillX in startX until endX) {
                        for (fillY in startY until endY) {
                            clip[fillX - graphBaseX][fillY - graphBaseY] = masks[fillX and 0x3F][fillY and 0x3F]
                        }
                    }
                } else {
                    for (fillX in startX until endX) {
                        for (fillY in startY until endY) {
                            clip[fillX - graphBaseX][fillY - graphBaseY] = default
                        }
                    }
                }
            }
        }
    }

    /**
     * Whether last path is only alternative path.
     */
    fun lastIsAlternative(): Boolean {
        return isAlternative
    }
}