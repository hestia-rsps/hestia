package world.gregs.hestia.game.map

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.Cache

class MapFileDecoder(val buffer: Packet) {

    fun decode() {
        val mapSettings = Array(4) { Array(64) { ByteArray(64) } }
        for (plane in 0 until 4) {
            for (x in 0 until 64) {
                for (y in 0 until 64) {
                    while (true) {
                        val value = buffer.readUnsignedByte()
                        if (value == 0) {
                            break
                        } else if (value == 1) {
                            buffer.readByte()
                            break
                        } else if (value <= 49) {
                            buffer.readByte()

                        } else if (value <= 81) {
                            mapSettings[plane][x][y] = (value - 49).toByte()
                        }
                    }
                }
            }
        }
        for (plane in 0 until 4) {
            for (x in 0 until 64) {
                for (y in 0 until 64) {
                    if (mapSettings[plane][x][y].toInt() and BLOCKED_TILE == BLOCKED_TILE && mapSettings[1][x][y].toInt() and BRIDGE_TILE != BRIDGE_TILE) {
//                        region.getMap(false).changeMask(plane, x, y, Flags.FLOOR_BLOCKS_WALK, RegionMap.ADD_MASK)
                    }
                }
            }
        }
    }

    companion object {
        private const val BLOCKED_TILE = 0x1
        private const val BRIDGE_TILE = 0x2

        fun create(index: MapIndex): MapFileDecoder {
            val file = Cache.getIndex(5)!!.getFile(index.mapIndex)
            return MapFileDecoder(Packet(file))
        }

        @JvmStatic
        fun main(args: Array<String>) {
            Cache.init()
            MapIndexDecoder().run()
            MapIndex.indices?.forEach { id, index ->
                create(index).decode()
            }
        }
    }

}