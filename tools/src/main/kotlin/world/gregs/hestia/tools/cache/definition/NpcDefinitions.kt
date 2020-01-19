package world.gregs.hestia.tools.cache.definition

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.definition.readers.NpcDefinitionReader

class NpcDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val store = CacheStore()
            val reader = NpcDefinitionReader(store)
            val npc = reader.get(0)
            println(ToStringBuilder.reflectionToString(npc, ToStringStyle.MULTI_LINE_STYLE))
        }
    }
}