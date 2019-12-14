package world.gregs.hestia.tools.cache.config

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.readers.RenderAnimationDefinitionReader
import worlds.gregs.hestia.service.cache.definition.readers.MobDefinitionReader

class RenderAnimationDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val store = CacheStore()
            val reader = RenderAnimationDefinitionReader(store)
            val mobReader = MobDefinitionReader(store)
            val mobs = listOf(0)
            mobs.forEach {
                val mob = mobReader.get(it)
                val renderAnim = reader.get(mob.renderEmote)
                println(ToStringBuilder.reflectionToString(renderAnim, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}