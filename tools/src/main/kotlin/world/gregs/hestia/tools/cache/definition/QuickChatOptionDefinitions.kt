package world.gregs.hestia.tools.cache.definition

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.definition.readers.QuickChatOptionDefinitionReader

class QuickChatOptionDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheStore()
            val reader = QuickChatOptionDefinitionReader(store)
            val size = reader.size
            println("Size $size")
            repeat(size) { id ->
                val options = reader.get(id)
                println(ToStringBuilder.reflectionToString(options, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}