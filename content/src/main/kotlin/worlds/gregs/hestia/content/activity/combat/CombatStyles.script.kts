package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.display.client.model.Configs
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.CombatStyles
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.WidgetComponentSettings

on<WindowOpened> {
    where { target == CombatStyles }
    task {
        toggle(true)
    }
}

on<WindowInteraction> {
    where { target == CombatStyles }
    task {
        val (_, _, component) = event(this)
        when (component) {
            4 -> {//Special attack bar
            }
            in 11..14 -> {//Attack style
                val attackStyle = component - 11
                entity send Config(Configs.COMBAT_STYLE, attackStyle)
            }
            15 -> {//Auto retaliate
            }
        }
    }
}

fun Task.toggle(unlock: Boolean) {
    for (componentId in 11..14) {
        entity send WidgetComponentSettings(CombatStyles, componentId, -1, 0, if (unlock) 2 else 0)
    }
}