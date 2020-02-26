package world.gregs.hestia.tools.cache.config

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.config.readers.ItemContainerDefinitionReader
import world.gregs.hestia.cache.definition.readers.ItemDefinitionReader

class ItemContainerDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")//"${System.getProperty("user.home")}\\Downloads\\rs718_cache\\")//667 doesn't have the full data natively
            val itemReader = ItemDefinitionReader(store)
            val reader = ItemContainerDefinitionReader(store)
            val data = store.index(2).archive(5)?.file(0)?.data

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