package world.gregs.hestia.tools.cache.definition

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.definition.readers.GraphicDefinitionReader

class GraphicDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheStore()
            val reader = GraphicDefinitionReader(store)
            val graphic = reader.get(75)
            println(ToStringBuilder.reflectionToString(graphic, ToStringStyle.MULTI_LINE_STYLE))
        }
    }
}