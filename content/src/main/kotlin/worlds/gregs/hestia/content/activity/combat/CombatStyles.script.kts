package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.display.client.model.Configs
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.CombatStyles
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.WidgetSettings

on<WindowOpened> {
    where { target == CombatStyles }
    task {
        toggle(true)
    }
}

on<WindowInteraction> {
    where { target == CombatStyles }
    task {
        val (_, _, widget) = event(this)
        when (widget) {
            4 -> {//Special attack bar
            }
            in 11..14 -> {//Attack style
                val attackStyle = widget - 11
                entity send Config(Configs.COMBAT_STYLE, attackStyle)
            }
            15 -> {//Auto retaliate
            }
        }
    }
}

fun Task.toggle(unlock: Boolean) {
    for (widget in 11..14) {
        entity send WidgetSettings(CombatStyles, widget, -1, 0, if (unlock) 2 else 0)
    }
}