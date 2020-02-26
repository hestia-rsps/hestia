package world.gregs.hestia.tools.cache.config

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.config.readers.QuestDefinitionReader

class QuestDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")
            val reader = QuestDefinitionReader(store)
            val size = reader.size
            println("Size $size")
            repeat(size) { id ->
                val options = reader.get(id)
                println(ToStringBuilder.reflectionToString(options, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}