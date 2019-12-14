package world.gregs.hestia.tools.cache.definition

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
            repeat(reader.size) { id ->
                val widget = reader.get(id)
                if (widget.size > 1) {
//                    println("Widget $id")

                    widget.filterNotNull().forEach { window ->
                        if(window.params != null) {
                            println(window.params)
                        }
//                            println(ToStringBuilder.reflectionToString(window, ToStringStyle.MULTI_LINE_STYLE))
                    }
                }
            }
        }
    }
}