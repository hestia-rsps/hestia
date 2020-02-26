package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.GraphicDefinitionReader
import world.gregs.hestia.core.Settings

class GraphicDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheLibrary("../hestia/data/cache")
            val reader = GraphicDefinitionReader(store)
            val graphic = reader.get(75)
            println(ToStringBuilder.reflectionToString(graphic, ToStringStyle.MULTI_LINE_STYLE))
        }
    }
}