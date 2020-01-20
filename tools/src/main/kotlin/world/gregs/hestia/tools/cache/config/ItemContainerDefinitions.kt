package world.gregs.hestia.tools.cache.config

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.config.readers.ItemContainerDefinitionReader
import worlds.gregs.hestia.service.cache.definition.readers.ItemDefinitionReader


class ItemContainerDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheStore("${System.getProperty("user.home")}\\Downloads\\rs718_cache\\")//667 doesn't have this data natively
            val itemReader = ItemDefinitionReader(CacheStore())
            val reader = ItemContainerDefinitionReader(store)
            val data = store.getArchive(2, 5)

//            val file = RandomAccessFile("./data/containers.dat", "r")
            println(reader.size)
            repeat(reader.size) {
                val container = reader.get(it)
//                if(container.ids?.any { id -> id > 22323 } == true) {
                println(it)
                println(container.ids?.map { id -> itemReader.get(id).name }?.joinToString(", "))
                println(ToStringBuilder.reflectionToString(container, ToStringStyle.MULTI_LINE_STYLE))
//                }
            }
        }
    }


}