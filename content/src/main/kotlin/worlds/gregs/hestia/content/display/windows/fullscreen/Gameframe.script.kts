package worlds.gregs.hestia.content.display.windows.fullscreen

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Logout
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WorldMap
import worlds.gregs.hestia.core.display.window.model.events.WindowInteraction
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.PlayerOptions
import worlds.gregs.hestia.core.task.api.event.target
import worlds.gregs.hestia.network.client.encoders.messages.PlayerContextMenuOption

on<WindowInteraction> {
    where { target == FixedGameframe || target == ResizableGameframe }
    task {
        val (_, _, widget, _, _, option) = event(this)
        val resizable = target == ResizableGameframe
        when {
            resizable && widget == 176 || !resizable && widget == 182 ->//Logout
                entity openWindow Logout
            resizable && widget == 175 || !resizable && widget == 184 -> {//Adviser button
            }
            resizable && widget == 174 || !resizable && widget == 178 -> {//Compass
            }
            resizable && widget == 182 || !resizable && widget == 180 ->//World map
                entity openWindow WorldMap
            resizable && widget == 229 || !resizable && widget == 0 -> {//XP orb
                when(option) {
                    1 -> {}//Toggle
                    7 -> {}//Reset
                }
            }
            resizable && widget in 39..54 -> {//All tabs
                println("Open Tab ${widget - 39}")
            }
            !resizable && widget in 129..136 -> {//Top row tabs
                println("Open Tab ${widget - 129}")
            }
            !resizable && widget in 99..106 -> {//Bottom row tabs
                println("Open Tab ${widget - 91}")//99 - 8
            }
        }
    }
}