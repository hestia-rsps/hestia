package world.gregs.hestia.tools.cache.definition

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.definition.readers.BodyDefinitionReader

class BodyDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val store = CacheStore()
            val reader = BodyDefinitionReader(store)
            val body = reader.get(-1)
            println(body.disabledSlots.size)
            println(ToStringBuilder.reflectionToString(body, ToStringStyle.MULTI_LINE_STYLE))
        }
    }
}