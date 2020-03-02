package worlds.gregs.hestia.content.display.fullscreen

import worlds.gregs.hestia.content.display.Tabs
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Logout
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WorldMap
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceInteraction
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface
import worlds.gregs.hestia.core.display.variable.model.events.SetVariable
import worlds.gregs.hestia.core.script.on

on<InterfaceInteraction> {
    where { id == FixedGameframe || id == ResizableGameframe }
    then {
        val resizable = id == ResizableGameframe
        when {
            resizable && component == 176 || !resizable && component == 182 ->//Logout
                entity perform OpenInterface(Logout)
            resizable && component == 175 || !resizable && component == 184 -> {//Adviser button
            }
            resizable && component == 174 || !resizable && component == 178 -> {//Compass
            }
            resizable && component == 182 || !resizable && component == 180 ->//World map
                entity perform OpenInterface(WorldMap, true)
            resizable && component == 229 || !resizable && component == 0 -> {//XP orb
                when(option) {
                    1 -> {}//Toggle
                    7 -> {}//Reset
                }
            }
            resizable && component in 39..54 -> {//All tabs
                entity perform SetVariable("tab", Tabs.tabNames[component - 39], false)
            }
            !resizable && component in 129..136 -> {//Top row tabs
                entity perform SetVariable("tab", Tabs.tabNames[component - 129], false)
            }
            !resizable && component in 99..106 -> {//Bottom row tabs
                entity perform SetVariable("tab", Tabs.tabNames[component - 91], false)
            }
        }
    }
}