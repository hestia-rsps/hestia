package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.AncientSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DungeoneeringSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.LunarSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ModernSpellbook
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction

on<WindowInteraction> {
    where { target == ModernSpellbook || target == LunarSpellbook || target == AncientSpellbook || target == DungeoneeringSpellbook }
    then {
        //TODO test these are all the same regardless of spellbook type
        when (widget) {
            2 -> {//Defensive casting
            }
            7 -> {//Combat spells
            }
            9 -> {//Teleport spells
            }
            11 -> {//Misc spells
            }
            13 -> {//Skill spells
            }
            15 -> {//Sort by level order
            }
            16 -> {//Sort by combat first
            }
            17 -> {//Sort by teleports first
            }
        }
    }
}