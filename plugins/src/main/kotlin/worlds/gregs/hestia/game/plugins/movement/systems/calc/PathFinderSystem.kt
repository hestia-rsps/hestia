package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.collision.Collision
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.api.core.components.Size
import worlds.gregs.hestia.api.movement.RouteStrategy
import worlds.gregs.hestia.game.movement.PrimaryNavigation
import worlds.gregs.hestia.game.movement.SecondaryNavigation
import worlds.gregs.hestia.game.movement.TertiaryNavigation
import worlds.gregs.hestia.game.update.Direction
import worlds.gregs.hestia.services.nearby

@Wire(failOnNull = false)
class PathFinderSystem : PassiveSystem() {

    val lastPathBufferX = IntArray(QUEUE_SIZE)
    val lastPathBufferY = IntArray(QUEUE_SIZE)

    private var exitX: Int = -1
    private var exitY: Int = -1
    private var isAlternative: Boolean = false
    private var collision: Collision? = null
    private lateinit var primary: PrimaryNavigation
    private lateinit var secondary: SecondaryNavigation
    private lateinit var tertiary: TertiaryNavigation
    private var graphBaseX = -1
    private var graphBaseY = -1
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var sizeMapper: ComponentMapper<Size>

    override fun initialize() {
        super.initialize()
        primary = PrimaryNavigation(collision)
        secondary = SecondaryNavigation(collision)
        tertiary = TertiaryNavigation(collision)
    }

    /**
     * Find's route using given strategy. Returns amount of steps found. If
     * steps > 0, route exists. If steps = 0, route exists, but no need to move.
     * If steps < 0, route does not exist.
     */
    fun findRoute(entityId: Int, strategy: RouteStrategy, findAlternative: Boolean, collide: Boolean): Int {//TODO shouldn't alternative & collide be in the strategy?
        val position = positionMapper.get(entityId)
        //Calculate entity size
        var sizeX = 1
        var sizeY = 1
        if(sizeMapper.has(entityId)) {
            val size = sizeMapper.get(entityId)
            sizeX = size.sizeX
            sizeY = size.sizeY
        }

        val directions = directions
        val distances = distances

        //Reset previous data
        isAlternative = false
        for (x in 0 until GRAPH_SIZE) {
            for (y in 0 until GRAPH_SIZE) {
                directions[x][y] = 0
                distances[x][y] = 99999999
            }
        }

        //Load collision map
        if (collide) {
            collision?.load(entityId, false)
        }

        this.graphBaseX = position.x - GRAPH_SIZE / 2
        this.graphBaseY = position.y - GRAPH_SIZE / 2

        val found = calculate(position, sizeX, sizeY, strategy)

        if (!found && !findAlternative) {
            return -1
        }

        //Start in centre
        val graphBaseX = graphBaseX
        val graphBaseY = graphBaseY
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

        if (endX == position.x && endY == position.y) {
            return 0//Path found but no movement
        }

        //Trace path in reverse for best route
        var steps = 0
        var traceX = endX
        var traceY = endY
        var direction = directions[traceX - graphBaseX][traceY - graphBaseY]
        var lastwritten = direction
        val bufferX = lastPathBufferX
        val bufferY = lastPathBufferY
        //Queue destination position and start tracing from it
        bufferX[steps] = traceX
        bufferY[steps++] = traceY
        while (traceX != position.x || traceY != position.y) {
            //Direction changed
            if (lastwritten != direction) {
                bufferX[steps] = traceX
                bufferY[steps++] = traceY
                lastwritten = direction
            }

            if (direction and DIR_WEST != 0) {
                traceX++
            } else if (direction and DIR_EAST != 0) {
                traceX--
            }

            if (direction and DIR_SOUTH != 0) {
                traceY++
            } else if (direction and DIR_NORTH != 0) {
                traceY--
            }

            direction = directions[traceX - graphBaseX][traceY - graphBaseY]
        }

        return steps
    }

    private fun calculate(position: Position, sizeX: Int, sizeY: Int, strategy: RouteStrategy): Boolean {
        //Cache fields for jit compiler performance boost
        val directions = directions
        val distances = distances
        val bufferX = lastPathBufferX
        val bufferY = lastPathBufferY
        val all = all
        val queueBounds = QUEUE_SIZE - 1
        val graphBounds = GRAPH_SIZE - 1

        //Choose navigation calculation to use
        val nav = when {
            sizeX == 1 && sizeY == 1 -> primary
            sizeX == 2 && sizeY == 2 -> secondary
            else -> tertiary
        }

        //Start in centre
        val graphBaseX = position.x - GRAPH_SIZE / 2
        val graphBaseY = position.y - GRAPH_SIZE / 2
        var currentX = position.x
        var currentY = position.y
        var currentGraphX = position.x - graphBaseX//Isn't this just graph_size/2?
        var currentGraphY = position.y - graphBaseY//Isn't this just graph_size/2?

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

            read = read + 1 and queueBounds

            currentGraphX = currentX - graphBaseX
            currentGraphY = currentY - graphBaseY

            //Check if path is complete
            if (strategy.exit(currentX, currentY, sizeX, sizeY, currentGraphX, currentGraphY, collision)) {
                exitX = currentX
                exitY = currentY
                return true
            }

            //Check clipping
            for (dir in all) {
                //Check all directions
                val deltaX = dir.deltaX
                val deltaY = dir.deltaY

                //Skip if horizontal out of bounds
                if (deltaX == -1 && currentGraphX <= 0 || deltaX == 1 && currentGraphX >= graphBounds) {
                    continue
                }

                //Skip if vertical out of bounds
                if (deltaY == -1 && currentGraphY <= 0 || deltaY == 1 && currentGraphY >= graphBounds) {
                    continue
                }

                if (directions[currentGraphX + deltaX][currentGraphY + deltaY] == 0 && nav.traversable(dir, currentGraphX, currentGraphY, sizeX, sizeY, deltaX, deltaY)) {
                    //Set the next step
                    bufferX[write] = currentX + deltaX
                    bufferY[write] = currentY + deltaY
                    //Increase the queue
                    write = write + 1 and queueBounds

                    //Set the direction
                    directions[currentGraphX + deltaX][currentGraphY + deltaY] = getDirectionMask(dir)
                    //Set the distance
                    distances[currentGraphX + deltaX][currentGraphY + deltaY] = distances[currentGraphX][currentGraphY] + 1
                }
            }
        }

        exitX = currentX
        exitY = currentY
        return false
    }

    private fun getDirectionMask(dir: Direction): Int {
        return when (dir.deltaX) {
            1 -> DIR_EAST
            -1 -> DIR_WEST
            else -> 0
        } or when (dir.deltaY) {
            1 -> DIR_NORTH
            -1 -> DIR_SOUTH
            else -> 0
        }
    }

    /**
     * Whether last path is only alternative path.
     */
    fun lastIsAlternative(): Boolean {
        return isAlternative
    }

    companion object {
        private const val GRAPH_SIZE = 128
        private const val QUEUE_SIZE = GRAPH_SIZE * GRAPH_SIZE / 4
        private const val ALTERNATIVE_ROUTE_MAX_DISTANCE = 100
        private const val ALTERNATIVE_ROUTE_RANGE = 10

        private const val DIR_SOUTH = 0x1
        private const val DIR_WEST = 0x2
        private const val DIR_NORTH = 0x4
        private const val DIR_EAST = 0x8

        val directions = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }
        val distances = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }
        private val all = listOf(Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.NORTH, Direction.SOUTH_WEST, Direction.SOUTH_EAST, Direction.NORTH_WEST, Direction.NORTH_EAST).toTypedArray()
    }
}