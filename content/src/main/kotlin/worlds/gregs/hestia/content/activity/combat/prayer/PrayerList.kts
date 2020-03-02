package worlds.gregs.hestia.content.activity.combat.prayer

import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.PRAYERS
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.PRAYER_POINTS
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.SELECTING_QUICK_PRAYERS
import worlds.gregs.hestia.content.activity.combat.prayer.PrayerConfigs.USING_QUICK_PRAYERS
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerList
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerOrb
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.core.display.variable.model.events.SendVariable
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.variable.model.variable.BooleanVariable
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.display.variable.model.variable.ListVariable
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings

ListVariable(1584, Variable.Type.VARP, true, listOf(
        "normal",
        "curses"
)).register(PRAYERS)

BooleanVariable(181, Variable.Type.VARC).register(SELECTING_QUICK_PRAYERS)
BooleanVariable(182, Variable.Type.VARC).register(USING_QUICK_PRAYERS)

IntVariable(2382, Variable.Type.VARP, true, 990).register(PRAYER_POINTS)

lateinit var variables: Variables

on<InterfaceOpened> {
    where { id == PrayerOrb }
    then {
        entity perform SetVariable(PRAYERS, "curses")//temp
        entity perform SendVariable(SELECTING_QUICK_PRAYERS)
        entity perform SendVariable(USING_QUICK_PRAYERS)
    }
}

on<InterfaceOpened> {
    where { id == PrayerList }
    then {
        entity perform SendVariable(PRAYERS)
        entity perform SendVariable(PRAYER_POINTS)
        val quickPrayers = variables.get(entity, SELECTING_QUICK_PRAYERS, false)
        entity send InterfaceSettings(PrayerList, if (quickPrayers) 42 else 8, 0, 29, 2)
    }
}