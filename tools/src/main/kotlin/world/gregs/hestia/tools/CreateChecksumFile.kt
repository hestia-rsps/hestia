package world.gregs.hestia.tools

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import java.nio.ByteBuffer
import java.nio.file.Files
import java.nio.file.Paths

object CreateChecksumFile {
    @JvmStatic
    fun main(args: Array<String>) {
        //Load necessary files
        Settings.load()
        val cache = CacheStore()
        //Create buffer
        val count = cache.indexCount()
        val buffer = ByteBuffer.allocate(1 + count * 4)
        //Write index count
        buffer.put(count.toByte())
        //Write index crcs
        repeat(count) {
            buffer.putInt(cache.getIndex(it).crc)
        }
        //Write to file
        val file = Paths.get("checksum.dat")
        Files.write(file, buffer.array())
    }
}