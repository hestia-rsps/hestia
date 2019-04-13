package worlds.gregs.hestia.game.plugins.movement.systems.calc

import com.artemis.BaseSystem
import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.api.collision.ObjectCollision
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.game.GameTest
import worlds.gregs.hestia.game.map.Flags
import worlds.gregs.hestia.game.plugins.collision.systems.CollisionSystem
import worlds.gregs.hestia.game.plugins.map.systems.CollisionTestInterface
import worlds.gregs.hestia.game.plugins.map.systems.MapCollisionSystem
import worlds.gregs.hestia.services.getSystem

abstract class MovementTester(offset: Boolean, vararg systems: BaseSystem) : GameTest(WorldConfigurationBuilder().with(ClippingBuilderTester(offset), CollisionSystem(), MapCollisionSystem(), *systems)), CollisionTestInterface {

    override var clip: ClippingBuilderTester? = null
    override var start = Pair(0, 0)
    override var size = Pair(1, 1)

    open fun reset(startX: Int, startY: Int, sizeX: Int, sizeY: Int) {
        clip = world.getSystem(ClippingBuilderTester::class)
        start = Pair(startX, startY)
        size = Pair(sizeX, sizeY)
    }

    open fun build(action: (ClippingBuilderTester.() -> Unit)? = null): ClippingBuilderTester {
        val builderTester = world.getSystem(ClippingBuilderTester::class)
        action?.invoke(builderTester)
        return builderTester
    }

    class ClippingBuilderTester(private val offset: Boolean) : ObjectCollision() {

        private var baseX = 0
        private var baseY = 0

        override fun load(position: Position, single: Boolean) {
            if(offset) {
                baseX = position.x - GRAPH_SIZE / 2
                baseY = position.y - GRAPH_SIZE / 2
            } else {
                baseX = 0
                baseY = 0
            }
        }

        override fun collides(localX: Int, localY: Int, mask: Int): Boolean {
            if (localX + baseX > clip.size || localX + baseX < 0) {
                return true
            }

            if (localY + baseY > clip[localX + baseX].size || localY + baseY < 0) {
                return true
            }

            return clip[localX + baseX][localY + baseY] and mask != 0
        }


        private var width: IntRange? = null
        private var height: IntRange? = null
        private var rotation = 0
        private val clip = Array(GRAPH_SIZE) { IntArray(GRAPH_SIZE) }

        fun rotate(rotation: Int) {
            this.rotation = rotation
        }

        fun crop(width: IntRange, height: IntRange) {
            this.width = width
            this.height = height
        }

        fun createDisplay(fill: Boolean): Array<CharArray> {
            val clip = Array((width?.last ?: clip.size) + 1) { CharArray((height?.last ?: clip[0].size) + 1) }
            for (x in width ?: this.clip.indices) {
                for (y in height ?: this.clip[0].indices) {
                    clip[x][y] = if (this.clip[x][y] == BLOCKED) '1' else if (fill) '0' else ' '
                }
            }
            return clip
        }

        fun block(range: IntRange, y: Int) {
            range.forEach {
                set(it, y, BLOCKED)
            }
        }

        fun block(x: Int, range: IntRange) {
            range.forEach {
                set(x, it, BLOCKED)
            }
        }

        fun block(xRange: IntRange, yRange: IntRange) {
            xRange.forEach { x ->
                yRange.forEach { y ->
                    set(x, y, BLOCKED)
                }
            }
        }

        fun block(x: Int, y: Int) {
            set(x, y, BLOCKED)
        }

        fun clear(x: Int, y: Int) {
            set(x, y, CLEAR)
        }

        fun clear(range: IntRange, y: Int) {
            range.forEach {
                set(it, y, CLEAR)
            }
        }

        fun clear(x: Int, range: IntRange) {
            range.forEach {
                set(x, it, CLEAR)
            }
        }

        fun clear(rangeX: IntRange, rangeY: IntRange) {
            rangeX.forEach { x ->
                rangeY.forEach { y ->
                    set(x, y, CLEAR)
                }
            }
        }

        fun set(x: Int, y: Int, value: Int) {
            clip[x][y] = value
        }

        fun blockAll() {
            setAll(BLOCKED)
        }

        fun setAll(value: Int) {
            for (x in clip.indices) {
                for (y in clip[x].indices) {
                    set(x, y, value)
                }
            }
        }

        fun print() {
            println("Map")
            printClip(createDisplay(true))
        }

        companion object {
            private const val GRAPH_SIZE = 128
            private const val CLEAR = 0
            private const val BLOCKED = Flags.OBJ or Flags.OBJ_BLOCKS_FLY or Flags.OBJ_BLOCKS_WALK_ALTERNATIVE
        }
    }

    companion object {
        fun printClip(array: Array<CharArray>) {
            for (y in array[0].indices.reversed()) {
                for (x in array.indices) {
                    print(array[x][y])
                    print(" ")
                }
                println()
            }
            println()
        }
    }
}