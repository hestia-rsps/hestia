package world.gregs.hestia.tools.cache.config

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.readers.IdentityKitDefinitionReader


class IdentityKitDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val store = CacheStore()
            val reader = IdentityKitDefinitionReader(store)
            repeat(reader.size) { id ->
                val identityKit = reader.get(id)
                println(ToStringBuilder.reflectionToString(identityKit, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}