package worlds.gregs.hestia.game.api.map

import net.mostlyoriginal.api.system.core.PassiveSystem

/**
 * TileClipping
 * Checks whether the next tile to step on is free (unclipped)
 */
abstract class TileClipping : PassiveSystem() {

    abstract fun isTileWalkable(x: Int, y: Int, plane: Int, dir: Int, sizeX: Int, sizeY: Int): Boolean

}