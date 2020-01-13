package world.gregs.hestia.tools.cache.definition

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.definition.readers.WindowDefinitionReader


class WindowDefinitions {
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
            Settings.load()
            val store = CacheStore()
            val reader = WindowDefinitionReader(store)
//            repeat(reader.size) { id ->
                val window = reader.get(26)
                if (window.size > 1) {
//                    println("Widget $id")
                    window.forEach { (index, widget) ->
                        println("$index ${getName(widget.type)}")
                        println(ToStringBuilder.reflectionToString(widget, ToStringStyle.MULTI_LINE_STYLE))
                    }
                }
//            }
        }
    }
}