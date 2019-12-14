package world.gregs.hestia.tools.cache.config

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.readers.StrutDefinitionReader


class StrutDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val store = CacheStore()
            val reader = StrutDefinitionReader(store)
            repeat(reader.size) { id ->
                val strut = reader.get(id)
                println(strut.params)
            }
        }
    }
}