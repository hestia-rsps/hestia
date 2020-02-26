package world.gregs.hestia.tools.cache.config

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.config.readers.HitSplatDefinitionReader

class HitSplatDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")
            val reader = HitSplatDefinitionReader(store)
            val size = reader.size
            println("Size $size")
            repeat(size) { id ->
                val splat = reader.get(id)
                println(ToStringBuilder.reflectionToString(splat, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}