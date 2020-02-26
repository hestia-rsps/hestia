package world.gregs.hestia.tools

import com.displee.cache.CacheLibrary
import world.gregs.hestia.core.Settings
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Paths

object CreateChecksumFile {
    @JvmStatic
    fun main(args: Array<String>) {
        //Load necessary files
        Settings.load("./settings.yml")
        val cache = CacheLibrary("../hestia/data/cache")
        //Create buffer
        val count = cache.indices().size
        val buffer = ByteBuffer.allocate(1 + count * 4)
        //Write index count
        buffer.put(count.toByte())
        //Write index crcs
        repeat(count) {
            buffer.putInt(cache.index(it).crc)
        }
        //Write to file
        val file = Paths.get("checksum.dat")
        Files.write(file, buffer.array())
    }
}