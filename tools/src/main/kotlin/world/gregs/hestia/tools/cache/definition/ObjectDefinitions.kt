package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.ObjectDefinitionReader
import world.gregs.hestia.core.Settings

class ObjectDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheLibrary("../hestia/data/cache")
            val reader = ObjectDefinitionReader(store)
            val obj = reader.get(2213)
            println(ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE))
        }
    }
}