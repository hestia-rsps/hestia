package worlds.gregs.hestia.content.activity.combat

import worlds.gregs.hestia.core.display.client.model.Configs
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrayerList
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.network.client.encoders.messages.ConfigGlobal
import worlds.gregs.hestia.network.client.encoders.messages.WindowWidgetSettings

on<WindowOpened> {
    where { target == PrayerList }
    task {
        val quickPrayers = false
        entity send WindowWidgetSettings(PrayerList, if (quickPrayers) 42 else 8, 0, 29, options = *intArrayOf(0))
        entity send Config(Configs.PRAYER_POINTS, 990)
        entity send Config(Configs.CURSES, 0)
        entity send ConfigGlobal(181, 0)//Setting quick prayers
//        entity send ConfigGlobal(182, 0)//Using quick prayers
    }
}

on<WindowInteraction> {
    where { target == PrayerList }
    task {
        val (_, _, widget) = event(this)
        when(widget) {
            12 -> {}//Show/Hide stat adjustments
        }
    }
}