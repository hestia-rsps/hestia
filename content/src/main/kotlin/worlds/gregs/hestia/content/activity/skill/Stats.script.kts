package worlds.gregs.hestia.content.activity.skill

import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.content.activity.skill.Skill.*
import worlds.gregs.hestia.core.display.client.model.Configs
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SkillGuide
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SkillLevelDetails
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Stats
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile
import worlds.gregs.hestia.network.client.encoders.messages.SkillLevel
import kotlin.math.pow

lateinit var es: EventSystem

val flash = listOf(ATTACK, STRENGTH, DEFENCE, RANGE, PRAYER, MAGIC, CONSTITUTION, AGILITY, HERBLORE, THIEVING, CRAFTING, FLETCHING, MINING,
        SMITHING, FISHING, COOKING, FIREMAKING, WOODCUTTING, RUNECRAFTING, SLAYER, FARMING, CONSTRUCTION, HUNTER, SUMMONING, DUNGEONEERING)
val stats = listOf(STRENGTH, AGILITY, DEFENCE, HERBLORE, FISHING, RANGE, THIEVING, COOKING, PRAYER, CRAFTING, MAGIC, FLETCHING, RUNECRAFTING,
        SLAYER, FARMING, CONSTRUCTION, HUNTER, SUMMONING, DUNGEONEERING, WOODCUTTING, FIREMAKING, SMITHING, MINING, CONSTITUTION, ATTACK)
val menu = listOf(ATTACK, STRENGTH, RANGE, MAGIC, DEFENCE, CONSTITUTION, PRAYER, AGILITY, HERBLORE, THIEVING, CRAFTING, RUNECRAFTING,
        MINING, SMITHING, FISHING, COOKING, FIREMAKING, WOODCUTTING, FLETCHING, SLAYER, FARMING, CONSTRUCTION, HUNTER, SUMMONING, DUNGEONEERING)

on<WindowOpened> {
    where { target == Stats }
    then { (entityId, _) ->

        val total = flash.filter { false /* has leveled up */ }.map { 2.0.pow(it.ordinal) }.sum().toInt()
        es.send(entityId, Config(Configs.SKILL_STAT_FLASH, total))

        values().forEach {
            es.send(entityId, SkillLevel(it.ordinal, 99, 14000000))
        }
    }
}

on<WindowInteraction> {
    where { target == Stats }
    task {
        val (_, _, widget, _, _, _) = event(this)
        when(widget) {
            11, 19, 28, 36, 44, 52, 60, 68, 76, 84, 93, 101, 110, 118, 126, 134, 142, 150, 158, 165, 172, 179, 186, 193, 200 -> {
                val index = when {
                    widget >= 165 -> 19 + (widget - 165) / 7
                    widget >= 110 -> 12 + (widget - 110) / 8
                    widget == 101 -> 11
                    widget == 93 -> 10
                    widget >= 28 -> 2 + (widget - 28) / 8
                    widget == 19 -> 1
                    else -> 0
                }
                val skill = stats[index]
                val menuIndex = menu.indexOf(skill) + 1

                val leveledUp = false
                val extra = 0
                /*
                    2 - combat level milestone
                    4 - total level milestone
                    6 - total level & combat level milestones
                 */
                val config = if(leveledUp) menuIndex * 8 + extra else menuIndex
                entity send Config(if(leveledUp) Configs.LEVEL_UP_DETAILS else Configs.SKILL_MENU, config)
                entity openWindow if(leveledUp) SkillLevelDetails else SkillGuide
                if(leveledUp) {
                    //TODO Replace with SKILL_STAT_FLASH config refresh once level up system is added
                    //Disable flash
                    entity send ConfigFile(if(skill == DUNGEONEERING) 7756 else 4731 + menuIndex, 0)
                }
            }
        }
    }
}

