package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.action.model.EntityAction
import worlds.gregs.hestia.core.display.window.api.Variable
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.CombatStyles
import worlds.gregs.hestia.core.display.window.model.events.SetVariable
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.variable.IntVariable
import worlds.gregs.hestia.network.client.encoders.messages.WidgetSettings

IntVariable(43, Variable.Type.VARP, true, 0).register("combat_style")

on<WindowOpened> {
    where { target == CombatStyles }
    then {
        toggle(true)
    }
}

on<WindowInteraction> {
    where { target == CombatStyles }
    then {
        when (widget) {
            4 -> {//Special attack bar
            }
            in 11..14 -> entity perform SetVariable("combat_style", widget - 11)//Attack style
            15 -> {//Auto retaliate
            }
        }
    }
}

fun EntityAction.toggle(unlock: Boolean) {
    for (widget in 11..14) {
        entity send WidgetSettings(CombatStyles, widget, -1, 0, if (unlock) 2 else 0)
    }
}