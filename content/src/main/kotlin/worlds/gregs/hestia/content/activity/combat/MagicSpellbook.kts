package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceComponentId
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.AncientSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DungeoneeringSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.LunarSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ModernSpellbook

val spellbooks = intArrayOf(ModernSpellbook, LunarSpellbook, AncientSpellbook, DungeoneeringSpellbook)

on(InterfaceOption, "Defensive Casting", ids = *spellbooks) { _, _, _, _ ->
}

on(InterfaceOption, "Filter", ids = *spellbooks) { hash, _, _, _ ->
    //TODO test these are all the same regardless of spellbook type
    when (getInterfaceComponentId(hash)) {
        7 -> {//Combat spells
        }
        9 -> {//Teleport spells
        }
        11 -> {//Misc spells
        }
        13 -> {//Skill spells
        }
    }
}

on(InterfaceOption, "Sort", ids = *spellbooks) { hash, _, _, _ ->
    when (getInterfaceComponentId(hash)) {
        15 -> {//Sort by level order
        }
        16 -> {//Sort by combat first
        }
        17 -> {//Sort by teleports first
        }
    }
}