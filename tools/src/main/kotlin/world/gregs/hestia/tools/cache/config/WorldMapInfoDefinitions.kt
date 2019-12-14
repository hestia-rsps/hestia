package world.gregs.hestia.tools.cache.config

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.readers.WorldMapInfoDefinitionReader

class WorldMapInfoDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val store = CacheStore()
            val reader = WorldMapInfoDefinitionReader(store)
            val size = reader.size
            println("Size $size")
            repeat(size) { id ->
                val map = reader.get(id)
                println(ToStringBuilder.reflectionToString(map, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}