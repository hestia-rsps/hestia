package world.gregs.hestia.tools.cache.config

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.config.readers.RenderAnimationDefinitionReader
import world.gregs.hestia.cache.definition.readers.NpcDefinitionReader

class RenderAnimationDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")
            val reader = RenderAnimationDefinitionReader(store)
            val npcReader = NpcDefinitionReader(store)
            val npcs = listOf(0)
            npcs.forEach {
                val npc = npcReader.get(it)
                val renderAnim = reader.get(npc.renderEmote)
                println(ToStringBuilder.reflectionToString(renderAnim, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}