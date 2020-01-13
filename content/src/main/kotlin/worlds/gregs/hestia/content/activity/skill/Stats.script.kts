package worlds.gregs.hestia.content.activity.skill

import worlds.gregs.hestia.content.activity.skill.Skill.*
import worlds.gregs.hestia.core.display.window.api.Variable
import worlds.gregs.hestia.core.display.window.api.Variables
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SkillGuide
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SkillLevelDetails
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Stats
import worlds.gregs.hestia.core.display.window.model.actions.OpenWindow
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.events.variable.RemoveVariable
import worlds.gregs.hestia.core.display.window.model.events.variable.SendVariable
import worlds.gregs.hestia.core.display.window.model.events.variable.SetVariable
import worlds.gregs.hestia.core.display.window.model.variable.BitwiseVariable
import worlds.gregs.hestia.core.display.window.model.variable.IntVariable
import worlds.gregs.hestia.network.client.encoders.messages.SkillLevel

val stats = listOf(STRENGTH, AGILITY, DEFENCE, HERBLORE, FISHING, RANGE, THIEVING, COOKING, PRAYER, CRAFTING, MAGIC, FLETCHING, RUNECRAFTING,
        SLAYER, FARMING, CONSTRUCTION, HUNTER, SUMMONING, DUNGEONEERING, WOODCUTTING, FIREMAKING, SMITHING, MINING, CONSTITUTION, ATTACK)
val menu = listOf(ATTACK, STRENGTH, RANGE, MAGIC, DEFENCE, CONSTITUTION, PRAYER, AGILITY, HERBLORE, THIEVING, CRAFTING, RUNECRAFTING,
        MINING, SMITHING, FISHING, COOKING, FIREMAKING, WOODCUTTING, FLETCHING, SLAYER, FARMING, CONSTRUCTION, HUNTER, SUMMONING, DUNGEONEERING)

BitwiseVariable(1179, Variable.Type.VARP, true, values = listOf(
        ATTACK, STRENGTH, DEFENCE, RANGE, PRAYER, MAGIC, CONSTITUTION, AGILITY, HERBLORE, THIEVING, CRAFTING, FLETCHING, MINING,
        SMITHING, FISHING, COOKING, FIREMAKING, WOODCUTTING, RUNECRAFTING, SLAYER, FARMING, CONSTRUCTION, HUNTER, SUMMONING, DUNGEONEERING
)).register("skill_stat_flash")
IntVariable(1230, Variable.Type.VARP).register("level_up_details")
IntVariable(965, Variable.Type.VARP).register("skill_guide")

lateinit var variables: Variables

on<WindowOpened> {
    where { target == Stats }
    then {
        entity perform SendVariable("skill_stat_flash")
        values().forEach {
            entity send SkillLevel(it.ordinal, 99, 14000000)
        }
    }
}

on<WindowInteraction> {
    where { target == Stats }
    then {
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

                if(variables.has(entity, "skill_stat_flash", skill)) {
                    val extra = 0//0 - normal, 2 - combat milestone, 4 - total milestone
                    entity perform SetVariable("level_up_details", menuIndex * 8 + extra)
                    entity perform OpenWindow(SkillLevelDetails)
                    entity perform RemoveVariable("skill_stat_flash", skill)
                } else {
                    entity perform SetVariable("skill_guide", menuIndex)
                    entity perform OpenWindow(SkillGuide)
                }
            }
        }
    }
}

