package worlds.gregs.hestia.core.world.movement.logic.systems.calc

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.artemis.nearby
import worlds.gregs.hestia.core.display.update.model.Direction
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Size
import worlds.gregs.hestia.core.entity.entity.model.components.height
import worlds.gregs.hestia.core.entity.entity.model.components.width
import worlds.gregs.hestia.core.world.collision.api.Collision
import worlds.gregs.hestia.core.world.movement.api.RouteStrategy
import worlds.gregs.hestia.core.world.movement.logic.navigation.PrimaryNavigation
import worlds.gregs.hestia.core.world.movement.logic.navigation.SecondaryNavigation
import worlds.gregs.hestia.core.world.movement.logic.navigation.TertiaryNavigation

@Wire(failOnNull = false)
class PathFinderSystem : PassiveSystem() {

    val lastPathBufferX = IntArray(QUEUE_SIZE)
    val lastPathBufferY = IntArray(QUEUE_SIZE)

    private var exitX: Int = -1
    private var exitY: Int = -1
    var isPartial: Boolean = false
        private set
    private var collision: Collision? = null
    private lateinit var primary: PrimaryNavigation
    private lateinit var secondary: SecondaryNavigation
    private lateinit var tertiary: TertiaryNavigation
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var sizeMapper: ComponentMapper<Size>
    private var graphBaseX = -1
    private var graphBaseY = -1

    override fun initialize() {
        super.initialize()
        primary = PrimaryNavigation(collision)
        secondary = SecondaryNavigation(collision)
        tertiary = TertiaryNavigation(collision)
    }

    /**
     * Find the optimal path using a given strategy
     * @return number of steps to reach target
     */
    fun findRoute(entityId: Int, strategy: RouteStrategy): Int {
        val directions = directions
        val distances = distances

        // Reset previous data
        isPartial = false
        for (x in 0 until GRAPH_SIZE) {
            for (y in 0 until GRAPH_SIZE) {
                directions[x][y] = 0
                distances[x][y] = 99999999
            }
        }

        val position = positionMapper.get(entityId)

        // Start in centre
        graphBaseX = position.x - GRAPH_SIZE / 2
        graphBaseY = position.y - GRAPH_SIZE / 2

        val sizeX = sizeMapper.width(entityId)
        val sizeY = sizeMapper.height(entityId)

        val found = calculate(position, sizeX, sizeY, strategy)

        if (!found && !calculatePartialPath(strategy)) {
            return -1// No path found
        }

        if (exitX == position.x && exitY == position.y) {
            return 0// No movement
        }

        return backtrace(position)
    }

    private fun calculate(position: Position, sizeX: Int, sizeY: Int, strategy: RouteStrategy): Boolean {
        // Cache fields for jit compiler performance boost
        val directions = directions
        val distances = distances
        val bufferX = lastPathBufferX
        val bufferY = lastPathBufferY
        val all = all
        val queueBounds = QUEUE_SIZE - 1
        val graphBounds = GRAPH_SIZE - 1

        // Choose navigation calculation to use
        val nav = when {
            sizeX == 1 && sizeY == 1 -> primary
            sizeX == 2 && sizeY == 2 -> secondary
            else -> tertiary
        }

        // Start in centre
        var currentX = position.x
        var currentY = position.y
        var currentGraphX = GRAPH_SIZE / 2
        var currentGraphY = GRAPH_SIZE / 2

        // Set starting tile as visited
        distances[currentGraphX][currentGraphY] = 0
        directions[currentGraphX][currentGraphY] = 99

        var read = 0
        var write = 0
        // Queue current position
        bufferX[write] = currentX
        bufferY[write++] = currentY

        while (read != write) {
            currentX = bufferX[read]
            currentY = bufferY[read]
            read = read + 1 and queueBounds

            currentGraphX = currentX - graphBaseX
            currentGraphY = currentY - graphBaseY

            // Check if path is complete
            if (strategy.exit(currentX, currentY, sizeX, sizeY, graphBaseX, graphBaseY, collision)) {
                exitX = currentX
                exitY = currentY
                return true
            }

            // Check for collisions
            for (dir in all) {
                // Check all directions
                val deltaX = dir.deltaX
                val deltaY = dir.deltaY

                // Skip if horizontal out of bounds
                if (deltaX == -1 && currentGraphX <= 0 || deltaX == 1 && currentGraphX >= graphBounds) {
                    continue
                }

                // Skip if vertical out of bounds
                if (deltaY == -1 && currentGraphY <= 0 || deltaY == 1 && currentGraphY >= graphBounds) {
                    continue
                }

                if (directions[currentGraphX + deltaX][currentGraphY + deltaY] == 0 && nav.traversable(dir.inverse(), currentX, currentY, sizeX, sizeY, deltaX, deltaY)) {
                    // Set the next step
                    bufferX[write] = currentX + deltaX
                    bufferY[write] = currentY + deltaY
                    // Increase the queue
                    write = write + 1 and queueBounds

                    // Set the direction
                    directions[currentGraphX + deltaX][currentGraphY + deltaY] = getDirectionFlag(dir)
                    // Set the distance
                    distances[currentGraphX + deltaX][currentGraphY + deltaY] = distances[currentGraphX][currentGraphY] + 1
                }
            }
        }

        exitX = currentX
        exitY = currentY
        return false
    }

    /**
     *  Checks for a tile closest to the target which is reachable
     */
    private fun calculatePartialPath(strategy: RouteStrategy): Boolean {
        isPartial = true
        var lowestCost = Integer.MAX_VALUE
        var lowestDistance = Integer.MAX_VALUE

        val destX = strategy.destinationX
        val destY = strategy.destinationY
        var endX = exitX
        var endY = exitY

        for (checkX in destX.nearby(PARTIAL_PATH_RANGE)) {
            for (checkY in destY.nearby(PARTIAL_PATH_RANGE)) {
                val graphX = checkX - graphBaseX
                val graphY = checkY - graphBaseY
                if (graphX < 0 || graphY < 0 || graphX >= GRAPH_SIZE || graphY >= GRAPH_SIZE || distances[graphX][graphY] >= PARTIAL_MAX_DISTANCE) {
                    continue
                }

                // Calculate deltas using strategy
                val deltaX = if (destX <= checkX) {
                    1 - destX - (strategy.sizeX - checkX)
                } else {
                    destX - checkX
                }

                val deltaY = if (destY <= checkY) {
                    1 - destY - (strategy.sizeY - checkY)
                } else {
                    destY - checkY
                }

                val cost = deltaX * deltaX + deltaY * deltaY
                if (cost < lowestCost || cost <= lowestCost && distances[graphX][graphY] < lowestDistance) {
                    // Accept lower costs or shorter paths
                    lowestCost = cost
                    lowestDistance = distances[graphX][graphY]
                    endX = checkX
                    endY = checkY
                }
            }
        }

        if (lowestCost == Integer.MAX_VALUE || lowestDistance == Integer.MAX_VALUE) {
            return false// No partial path found
        }

        exitX = endX
        exitY = endY
        return true
    }

    /**
     *  Traces the path back to find how many steps were taken to reach the target
     */
    private fun backtrace(position: Position): Int {
        var steps = 0
        var traceX = exitX
        var traceY = exitY
        var direction = directions[traceX - graphBaseX][traceY - graphBaseY]
        var lastWritten = direction
        val bufferX = lastPathBufferX
        val bufferY = lastPathBufferY
        // Queue destination position and start tracing from it
        bufferX[steps] = traceX
        bufferY[steps++] = traceY
        while (traceX != position.x || traceY != position.y) {
            // Direction changed
            if (lastWritten != direction) {
                bufferX[steps] = traceX
                bufferY[steps++] = traceY
                lastWritten = direction
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

    private fun getDirectionFlag(dir: Direction): Int {
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

    companion object {
        private const val GRAPH_SIZE = 128
        private const val QUEUE_SIZE = GRAPH_SIZE * GRAPH_SIZE / 4
        private const val PARTIAL_MAX_DISTANCE = QUEUE_SIZE
        private const val PARTIAL_PATH_RANGE = 10

        private const val DIR_SOUTH = 0x1
        private const val DIR_WEST = 0x2
        private const val DIR_NORTH = 0x4
        private const val DIR_EAST = 0x8

        val directions = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }
        val distances = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }
        private val all = arrayOf(Direction.WEST, Direction.EAST, Direction.SOUTH, Direction.NORTH, Direction.SOUTH_WEST, Direction.SOUTH_EAST, Direction.NORTH_WEST, Direction.NORTH_EAST)
    }
}