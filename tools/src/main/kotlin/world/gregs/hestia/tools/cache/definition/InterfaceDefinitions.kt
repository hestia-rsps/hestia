package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.InterfaceDefinitionReader
import world.gregs.hestia.core.Settings

class InterfaceDefinitions {
    companion object {
        fun getName(type: Int): String {
            return when (type) {
                0 -> "Window"
                3 -> "Rectangle"
                4 -> "Text"
                5 -> "Sprite"
                6 -> "Model"
                9 -> "Text2"
                else -> "Unknown"
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")
            val reader = InterfaceDefinitionReader(store)
//            val str = "Burst of Strength"
//            repeat(reader.size) { id ->
                val iface = reader.get(751)
                if (iface.size > 1) {
//                    println("Interface $id")
                    iface.forEach { (index, component) ->

                        println("$index ${getName(component.type)}")
                        println(ToStringBuilder.reflectionToString(component, ToStringStyle.MULTI_LINE_STYLE))
                    }
                }
//            }
        }
    }
}