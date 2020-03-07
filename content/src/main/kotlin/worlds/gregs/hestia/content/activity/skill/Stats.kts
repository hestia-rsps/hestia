package worlds.gregs.hestia.content.activity.skill

import worlds.gregs.hestia.content.activity.skill.Skill.*
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceComponentId
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SkillGuide
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SkillLevelDetails
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Stats
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.variable.model.events.RemoveVariable
import worlds.gregs.hestia.core.display.variable.model.events.SendVariable
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.variable.BitwiseVariable
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.network.client.encoders.messages.SkillLevel
import worlds.gregs.hestia.core.script.on

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

on<InterfaceOpened> {
    where { id == Stats }
    then {
        entity perform SendVariable("skill_stat_flash")
        values().forEach {
            entity send SkillLevel(it.ordinal, 99, 14000000)
        }
    }
}

on(InterfaceOption, "View", id = Stats) { hash, _, _, _ ->
    val component = getInterfaceComponentId(hash)
    val index = when {
        component >= 165 -> 19 + (component - 165) / 7
        component >= 110 -> 12 + (component - 110) / 8
        component == 101 -> 11
        component == 93 -> 10
        component >= 28 -> 2 + (component - 28) / 8
        component == 19 -> 1
        else -> 0
    }

    val skill = stats[index]
    val menuIndex = menu.indexOf(skill) + 1

    if (variables.has(entity, "skill_stat_flash", skill)) {
        val extra = 0//0 - normal, 2 - combat milestone, 4 - total milestone
        entity perform SetVariable("level_up_details", menuIndex * 8 + extra)
        entity perform OpenInterface(SkillLevelDetails)
        entity perform RemoveVariable("skill_stat_flash", skill)
    } else {
        entity perform SetVariable("skill_guide", menuIndex)
        entity perform OpenInterface(SkillGuide)
    }
}

on(InterfaceOption, "Open subsection", id = SkillGuide) { hash, _, _, _ ->
    val index = getInterfaceComponentId(hash) - 10
    val guide = variables.get<Int>(entity, "skill_guide")!!
    val menuIndex = guide.rem(1024)
    entity perform SetVariable("skill_guide", menuIndex + index * 1024)
}

