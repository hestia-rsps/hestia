package world.gregs.hestia.tools.cache.config

import com.displee.cache.CacheLibrary
import world.gregs.hestia.cache.definition.config.readers.StrutDefinitionReader

class StrutDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")
            val reader = StrutDefinitionReader(store)
            repeat(reader.size) { id ->
                val strut = reader.get(id)
                println(strut.params)
            }
        }
    }
}