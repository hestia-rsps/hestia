package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.AncientSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DungeoneeringSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.LunarSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ModernSpellbook
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.script.on

on<InterfaceInteraction> {
    where { id == ModernSpellbook || id == LunarSpellbook || id == AncientSpellbook || id == DungeoneeringSpellbook }
    then {
        //TODO test these are all the same regardless of spellbook type
        when (component) {
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