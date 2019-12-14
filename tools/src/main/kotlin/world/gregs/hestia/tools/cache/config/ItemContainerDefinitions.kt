package world.gregs.hestia.tools.cache.config

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.readers.ItemContainerDefinitionReader


class ItemContainerDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val store = CacheStore("C:\\Users\\Greg\\Downloads\\rs718_cache\\")//667 doesn't have this data natively
            val reader = ItemContainerDefinitionReader(store)
            repeat(reader.size) {
                val container = reader.get(it)
                println(ToStringBuilder.reflectionToString(container, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}