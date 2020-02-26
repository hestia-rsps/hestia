package world.gregs.hestia.tools.cache.config

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.config.readers.IdentityKitDefinitionReader

class IdentityKitDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")
            val reader = IdentityKitDefinitionReader(store)
            repeat(reader.size) { id ->
                val identityKit = reader.get(id)
                println(ToStringBuilder.reflectionToString(identityKit, ToStringStyle.MULTI_LINE_STYLE))
            }
        }
    }
}