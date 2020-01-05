package worlds.gregs.hestia.content.display.windows.fullscreen

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Logout
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WorldMap
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.script.dsl.task.PlayerOptions
import worlds.gregs.hestia.core.task.api.event.target
import worlds.gregs.hestia.network.client.encoders.messages.PlayerContextMenuOption

on<WindowOpened> {
    where { target == FixedGameframe || target == ResizableGameframe }
    task {
        val (_, window) = event(this)
        listOf(PlayerOptions.FOLLOW, PlayerOptions.TRADE, PlayerOptions.ASSIST).forEach {
            entity send PlayerContextMenuOption(it.string, it.slot, it.top)
        }
    }
}

on<WindowInteraction> {
    where { target == FixedGameframe }
    task {
        val (_, _, component, _, _, option) = event(this)
        val resizable = target == ResizableGameframe
        when {
            resizable && component == 176 || !resizable && component == 182 ->//Logout
                entity openWindow Logout
            resizable && component == 175 || !resizable && component == 184 -> {//Adviser button
            }
            resizable && component == 174 || !resizable && component == 178 -> {//Compass
            }
            resizable && component == 182 || !resizable && component == 180 ->//World map
                entity openWindow WorldMap
            resizable && component == 229 || !resizable && component == 0 -> {//XP orb
                when(option) {
                    1 -> {}//Toggle
                    7 -> {}//Reset
                }
            }
            resizable && component in 39..54 -> {//All tabs
                println("Open Tab ${component - 39}")
            }
            !resizable && component in 129..136 -> {//Top row tabs
                println("Open Tab ${component - 129}")
            }
            !resizable && component in 99..106 -> {//Bottom row tabs
                println("Open Tab ${component - 91}")//99 - 8
            }
        }
    }
}