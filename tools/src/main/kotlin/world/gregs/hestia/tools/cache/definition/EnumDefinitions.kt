package world.gregs.hestia.tools.cache.definition

import com.displee.cache.CacheLibrary
import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle
import world.gregs.hestia.cache.definition.readers.EnumDefinitionReader

class EnumDefinitions {
    /*
    1697 - Beacon messages
    1481 - quest names
    1477 - level up messages
    1466 - fairy ring characters
    1349 - more music descriptions
    1345/1347 - music names
    1278 - more summoning creature names
    1189-1198 - dungeoneering items
    1187 - summoning pouch names
    1186 - summoning pouch requirement description
    1100 - gravestone descriptions
    1099 - gravestone names
    1093 - titles
    1089 - Item set descriptions
    930 - summoning pets
    911 - curses names
    680 - skill list
    952 - music unlock descriptions

     */

    companion object {


        private fun printMusicId(reader: EnumDefinitionReader) {
            val musicIndex = reader.get(1345).getKey("Astea Frostweb")
            println(reader.get(1351).getInt(musicIndex))
        }

        private fun printQuests(reader: EnumDefinitionReader) {
            val names = reader.get(1345)
            val ids = reader.get(1351)
            val hint1 = reader.get(952)
            val hint2 = reader.get(1349)
            for (v in names.map!!.values) {
                val key = names.getKey(v)
                val id = ids.getInt(key)

                val hint = if (hint1.map!!.containsKey(key))
                    hint1.getString(key)
                else
                    hint2.getString(key)

                println("$id, $v; $hint, ")
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val store = CacheLibrary("../hestia/data/cache")
            val reader = EnumDefinitionReader(store)
//            val enum = reader.get( 2279)//677, 685, 1005, 686, 1029, 1006
//            println(ToStringBuilder.reflectionToString(enum, ToStringStyle.MULTI_LINE_STYLE))
            repeat(reader.size) { id ->
                val def = reader.get(id)
//                if(def.map?.containsValue(906) == true)
                println("$id ${ToStringBuilder.reflectionToString(def, ToStringStyle.MULTI_LINE_STYLE)}")
            }
//            printQuests(reader)
        }
    }
}