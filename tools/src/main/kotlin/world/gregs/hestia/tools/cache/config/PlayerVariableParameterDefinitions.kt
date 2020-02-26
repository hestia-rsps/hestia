package world.gregs.hestia.tools.cache.config

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.config.readers.PlayerVariableParameterDefinitionReader
import world.gregs.hestia.core.Settings

class PlayerVariableParameterDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")
            val reader = PlayerVariableParameterDefinitionReader(store)
            val size = reader.size
            println("Size $size")
            repeat(size) { id ->
                val param = reader.get(id)
                println(ToStringBuilder.reflectionToString(param, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}