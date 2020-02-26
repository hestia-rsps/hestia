package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.AnimationDefinitionReader
import world.gregs.hestia.core.Settings

class AnimationDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheLibrary("../hestia/data/cache")
            val reader = AnimationDefinitionReader(store)
            val animation = reader.get(11788)
            println(ToStringBuilder.reflectionToString(animation, ToStringStyle.MULTI_LINE_STYLE))
            println("Time: ${animation.getTime()} Cycles: ${animation.getClientCycles()}")
        }
    }
}