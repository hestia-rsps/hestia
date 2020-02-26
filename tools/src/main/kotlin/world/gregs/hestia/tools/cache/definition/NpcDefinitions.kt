package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.NpcDefinitionReader
import world.gregs.hestia.core.Settings

class NpcDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheLibrary("../hestia/data/cache")
            val reader = NpcDefinitionReader(store)
            val npc = reader.get(0)
            println(ToStringBuilder.reflectionToString(npc, ToStringStyle.MULTI_LINE_STYLE))
        }
    }
}