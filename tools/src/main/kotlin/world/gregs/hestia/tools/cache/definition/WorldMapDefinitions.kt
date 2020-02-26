package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.WorldMapDefinitionReader
import world.gregs.hestia.core.Settings

class WorldMapDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheLibrary("../hestia/data/cache")
            val reader = WorldMapDefinitionReader(store)
            val size = reader.size
            println("Size $size")
            repeat(size) { id ->
                val map = reader.get(id)
                println(ToStringBuilder.reflectionToString(map, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}