package world.gregs.hestia.tools.cache.definition

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.definition.readers.AnimationDefinitionReader

class AnimationDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val store = CacheStore()
            val reader = AnimationDefinitionReader(store)
            val animation = reader.get(11788)
            println(ToStringBuilder.reflectionToString(animation, ToStringStyle.MULTI_LINE_STYLE))
            println("Time: ${animation.getTime()} Cycles: ${animation.getClientCycles()}")
        }
    }
}