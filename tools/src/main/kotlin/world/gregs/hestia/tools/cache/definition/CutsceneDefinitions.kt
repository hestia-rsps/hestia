package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.WorldMapDefinitionReader
import world.gregs.hestia.core.Settings

class CutsceneDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheLibrary("../hestia/data/cache")
            val index = store.index(35)
            index.archiveIds().forEach {
                val archive = index.archive(it) ?: return@forEach
                if(archive.containsData()) {
                    println("Has data $it")
                }
            }
        }
    }
}