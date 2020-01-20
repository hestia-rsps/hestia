package world.gregs.hestia.tools.cache.definition

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.CacheStore
import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition
import worlds.gregs.hestia.service.cache.definition.readers.EnumDefinitionReader
import worlds.gregs.hestia.service.cache.definition.readers.ItemDefinitionReader
import kotlin.collections.set


class ItemDefinitions {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load("./settings.yml")
            val store = CacheStore()
            val reader = ItemDefinitionReader(store)
            val enumReader = EnumDefinitionReader(store)

            val list = mutableListOf<String>()

            val replacements = mapOf(
                    "4_5ths" to "Four_Fifths",
                    "3_5ths" to "Three_Fifths",
                    "2_5ths" to "Two_Fifths",
                    "1_5ths" to "One_Fifth",
                    "2_3rds" to "Two_Thirds",
                    "2_3" to "Two_Thirds",
                    "1_3rds" to "One_Third",
                    "10th" to "Tenth"
            )
            repeat(reader.size) {
                val item = reader.get(it)

                if(!item.options.contentEquals(ItemDefinitionReader.defaultOptions)) {
                    if(item.name != "null") {
                        var formatted = item.name
                                .replace("(", "").replace(")", "").replace(".", "")
                                .replace("-", " ").replace("'", " ").replace("+", "Plus").replace("/", " ")
                                .replace("&", "and")
                                .replace("\uFFEF", "i")
                                .replace(" ", "")
                        replacements.forEach { (find, replace) ->
                            if (formatted.startsWith(find)) {
                                formatted = formatted.replace(find, replace)
                            }
                        }
                        formatted = formatted.split(" ").map { it.capitalize() }.joinToString(" ")
                        if(list.contains(formatted)) {
                            return@repeat
                        }
                        println("object $formatted : Items(${item.id})")
                        list.add(formatted)
                    }
                }
                /*val createRequirements = item.getCreateItemRequirements()
                if(!createRequirements.isNullOrEmpty()) {
                    println("$it ${item.name} Requirements:")
                    createRequirements.forEach { (id, amount) ->
                        println("    $id $amount")
                        val enum = enumReader.get(id)
                        println(ToStringBuilder.reflectionToString(enum, ToStringStyle.MULTI_LINE_STYLE))
                    }
                }*/
            }
//            val item = reader.get(11694)
//            Param.values().forEach {
//                if(it.defaultValue is Int) {
//                    val value = item.getIntParameter(it)
//                    if(value != it.defaultValue) {
//                        println("${it.name} $value")
//                    }
//                } else if(it.defaultValue is Boolean) {
//                    val value = item.getBoolParameter(it)
//                    if(value != it.defaultValue) {
//                        println("${it.name} $value")
//                    }
//                }
//            }
//            val equipRequirements = item.getWearingSkillRequirements()
//            if(!equipRequirements.isNullOrEmpty()) {
//                println("Equip requirements:")
//                equipRequirements.forEach { (index, value) ->
//                    println("    $value ${Skill.values()[index].name.toLowerCase()}")
//                }
//            }
//            val createRequirements = item.getCreateItemRequirements()
//            if(!createRequirements.isNullOrEmpty()) {
//                println("Create Requirements:")
//                createRequirements.forEach { (id, amount) ->
//                    println("    $id ${reader.get(id).name} $amount")
//                }
//            }
//            println(ToStringBuilder.reflectionToString(item, ToStringStyle.MULTI_LINE_STYLE))
        }

        enum class Param(val id: Long, val defaultValue: Any) {
            SPECIAL_BAR(686, false),
            ATTACK_SPEED(14, 4),
            STAB_ATTACK(0, 0),
            SLASH_ATTACK(1, 0),
            CRUSH_ATTACK(2, 0),
            MAGIC_ATTACK(3, 0),
            RANGE_ATTACK(4, 0),
            STAB_DEFENCE(5, 0),
            SLASH_DEFENCE(6, 0),
            CRUSH_DEFENCE(7, 0),
            MAGIC_DEFENCE(8, 0),
            RANGE_DEFENCE(9, 0),
            SUMMONING_DEFENCE(417, 0),
            ABSORB_MELEE(967, 0),
            ABSORB_MAGIC(969, 0),
            ABSORB_RANGE(968, 0),
            STRENGTH_BONUS(641, 0),
            RANGED_STRENGTH_BONUS(643, 0),
            MAGIC_DAMAGE(685, 0),
            PRAYER_BONUS(11, 0),
            RENDER_ANIMATION(644, 1426),
            QUEST_ID(861, -1),
            MAXED_SKILL(277, -1);
        }

        fun ItemDefinition.getIntParameter(param: Param): Int {
            if (params == null) {
                return param.defaultValue as Int
            }
            val value = params!![param.id]
            return if (value != null && value is Int) value else param.defaultValue as Int
        }

        fun ItemDefinition.getBoolParameter(param: Param): Boolean {
            if (params == null) {
                return param.defaultValue as Boolean
            }
            val value = params!![param.id]
            return if (value != null && value is Boolean) value else param.defaultValue as Boolean
        }

        fun ItemDefinition.getWearingSkillRequirements(): HashMap<Int, Int>? {
            if (params == null) {
                return null
            }
            val skills = HashMap<Int, Int>()
            var nextLevel = -1
            var nextSkill = -1
            for (key in params!!.keys) {
                val value = params!![key]
                if (value is String) {
                    continue
                }
                val key = key.toInt()
                if (key == 23) {
                    skills[4] = value as Int
                    skills[11] = 61
                } else if (key in 749..796) {
                    if (key % 2 == 0) {
                        nextLevel = value as Int
                    } else {
                        nextSkill = value as Int
                    }
                    if (nextLevel != -1 && nextSkill != -1) {
                        skills[nextSkill] = nextLevel
                        nextLevel = -1
                        nextSkill = -1
                    }
                }
            }
            return skills
        }

        fun ItemDefinition.getCreateItemRequirements(): Map<Int, Int>? {
            if (params == null) {
                return null
            }
            val items = mutableMapOf<Int, Int>()
            var requiredId = -1
            var requiredAmount = -1
            for (key in params!!.keys) {
                val value = params!![key]
                if (value is String){
                    continue
                }
                if (key in 538..770) {
                    if (key.toInt() % 2 == 0) {
                        requiredId = value as Int
                    } else {
                        requiredAmount = value as Int
                    }
                    if (requiredId != -1 && requiredAmount != -1) {
                        items[requiredId] = requiredAmount
                        requiredId = -1
                        requiredAmount = -1
                    }
                }
            }
            return items
        }

        /*
        Exceptions
        Integer maxedSkill = (Integer) clientScriptData.get(277);
                        if (maxedSkill != null)
                                skills.put(maxedSkill, getId() == 19709 ? 120 : 99);
                        itemRequiriments = skills;
                        if (getId() == 7462)
                                itemRequiriments.put(Skills.DEFENCE, 40);
                        else if (getId() == 19784 || getId() == 22401 || getId() == 19780) { // Korasi
                                itemRequiriments.put(Skills.ATTACK, 78);
                                itemRequiriments.put(Skills.STRENGTH, 78);
                        } else if (getId() == 20822 || getId() == 20823 || getId() == 20824 || getId() == 20825 || getId() == 20826)
                                itemRequiriments.put(Skills.DEFENCE, 99);
                        else if (name.equals("Dragon defender")) {
                                itemRequiriments.put(Skills.ATTACK, 60);
                                itemRequiriments.put(Skills.DEFENCE, 60);
                        }
         */
    }
}