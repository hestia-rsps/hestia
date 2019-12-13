package worlds.gregs.hestia.service

import world.gregs.hestia.core.Settings
import java.io.RandomAccessFile
import kotlin.system.measureTimeMillis

/**
 * Loads map XTEA keys from xteas.dat file
 */
object Xteas {

    val KEY_TABLE = arrayOfNulls<IntArray>(255 * 255)

    init {
        var total = 0
        val time = measureTimeMillis {
            val file = RandomAccessFile(Settings.getString("xteaPath"), "r")
            var regionId: Int
            while (file.filePointer < file.length()) {
                regionId = file.readShort().toInt()
                val xtea = IntArray(4)
                for (i in xtea.indices) {
                    xtea[i] = file.readInt()
                }
                KEY_TABLE[regionId] = xtea
                total++
            }
            file.close()
        }
        println("Loaded $total xteas in ${time}ms.")
    }

}