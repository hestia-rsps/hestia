package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.VarBitDefinitionReader
import world.gregs.hestia.core.Settings

class VarBitDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheLibrary("../hestia/data/cache")
            val reader = VarBitDefinitionReader(store)
            val varBit = reader.get(313)
            println(ToStringBuilder.reflectionToString(varBit, ToStringStyle.MULTI_LINE_STYLE))
        }
    }
}