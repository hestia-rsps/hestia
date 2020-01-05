package worlds.gregs.hestia.content.activity.skill

import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.client.model.Configs
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SkillLevelDetails
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SkillMenu
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Stats
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.game.entity.Skill
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.ConfigFile
import worlds.gregs.hestia.network.client.encoders.messages.SkillLevel
import kotlin.math.pow

lateinit var es: EventSystem

val flash = listOf(Skill.ATTACK, Skill.STRENGTH, Skill.DEFENCE, Skill.RANGE, Skill.PRAYER, Skill.MAGIC, Skill.CONSTITUTION, Skill.AGILITY, Skill.HERBLORE, Skill.THIEVING, Skill.CRAFTING, Skill.FLETCHING, Skill.MINING,
        Skill.SMITHING, Skill.FISHING, Skill.COOKING, Skill.FIREMAKING, Skill.WOODCUTTING, Skill.RUNECRAFTING, Skill.SLAYER, Skill.FARMING, Skill.CONSTRUCTION, Skill.HUNTER, Skill.SUMMONING, Skill.DUNGEONEERING)
val stats = listOf(Skill.STRENGTH, Skill.AGILITY, Skill.DEFENCE, Skill.HERBLORE, Skill.FISHING, Skill.RANGE, Skill.THIEVING, Skill.COOKING, Skill.PRAYER, Skill.CRAFTING, Skill.MAGIC, Skill.FLETCHING, Skill.RUNECRAFTING,
        Skill.SLAYER, Skill.FARMING, Skill.CONSTRUCTION, Skill.HUNTER, Skill.SUMMONING, Skill.DUNGEONEERING, Skill.WOODCUTTING, Skill.FIREMAKING, Skill.SMITHING, Skill.MINING, Skill.CONSTITUTION, Skill.ATTACK)
val menu = listOf(Skill.ATTACK, Skill.STRENGTH, Skill.RANGE, Skill.MAGIC, Skill.DEFENCE, Skill.CONSTITUTION, Skill.PRAYER, Skill.AGILITY, Skill.HERBLORE, Skill.THIEVING, Skill.CRAFTING, Skill.RUNECRAFTING,
        Skill.MINING, Skill.SMITHING, Skill.FISHING, Skill.COOKING, Skill.FIREMAKING, Skill.WOODCUTTING, Skill.FLETCHING, Skill.SLAYER, Skill.FARMING, Skill.CONSTRUCTION, Skill.HUNTER, Skill.SUMMONING, Skill.DUNGEONEERING)

on<WindowOpened> {
    where { target == Stats }
    then { (entityId, _) ->

        val total = flash.filter { false /* has leveled up */ }.map { 2.0.pow(it.ordinal) }.sum().toInt()
        es.send(entityId, Config(Configs.SKILL_STAT_FLASH, total))

        Skill.values().forEach {
            es.send(entityId, SkillLevel(it.ordinal, 99, 14000000))
        }
    }
}

on<WindowInteraction> {
    where { target == Stats }
    task {
        val (_, _, component, _, _, _) = event(this)
        when(component) {
            11, 19, 28, 36, 44, 52, 60, 68, 76, 84, 93, 101, 110, 118, 126, 134, 142, 150, 158, 165, 172, 179, 186, 193, 200 -> {
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

                val leveledUp = false
                val extra = 0
                /*
                    2 - combat level milestone
                    4 - total level milestone
                    6 - total level & combat level milestones
                 */
                val config = if(leveledUp) menuIndex * 8 + extra else menuIndex
                entity send Config(if(leveledUp) Configs.LEVEL_UP_DETAILS else Configs.SKILL_MENU, config)
                entity openWindow if(leveledUp) SkillLevelDetails else SkillMenu
                if(leveledUp) {
                    //TODO Replace with SKILL_STAT_FLASH config refresh once level up system is added
                    //Disable flash
                    entity send ConfigFile(if(skill == Skill.DUNGEONEERING) 7756 else 4731 + menuIndex, 0)
                }
            }
        }
    }
}

