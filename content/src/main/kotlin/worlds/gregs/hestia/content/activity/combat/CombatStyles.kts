package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.getInterfaceComponentId
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.CombatStyles
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceSettings

IntVariable(43, Variable.Type.VARP, true, 0).register("combat_style")

on<InterfaceOpened> {
    where { id == CombatStyles }
    then {
        toggle(true)
    }
}

on(InterfaceOption, option = 1, id = CombatStyles, components = *intArrayOf(4, 11, 12, 13, 14)) { hash, _, _, _ ->
    when (val component = getInterfaceComponentId(hash)) {
        4 -> {//Special attack bar
        }
        in 11..14 -> entity perform SetVariable("combat_style", component - 11)//Attack style
    }
}

on(InterfaceOption, "Auto Retaliate", id = CombatStyles) { _, _, _, _ ->

}

fun EntityAction.toggle(unlock: Boolean) {
    for (index in 11..14) {
        entity send InterfaceSettings(CombatStyles, index, -1, 0, if (unlock) 2 else 0)
    }
}