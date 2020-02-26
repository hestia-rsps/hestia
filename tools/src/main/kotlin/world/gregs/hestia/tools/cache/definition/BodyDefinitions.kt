package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.BodyDefinitionReader
import world.gregs.hestia.core.Settings

class BodyDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheLibrary("../hestia/data/cache")
            val reader = BodyDefinitionReader(store)
            val body = reader.get(-1)
            println(body.disabledSlots.size)
            println(ToStringBuilder.reflectionToString(body, ToStringStyle.MULTI_LINE_STYLE))
        }
    }
}