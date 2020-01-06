package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.model.WindowPane.MAIN_SCREEN

lateinit var windows: Windows

on<Command> {
    where { prefix == "inter" }
    then {
        val id = content.toInt()
        windows.closeWindows(entity, MAIN_SCREEN)
        if(id != -1) {
            windows.openWindow(entity, id)
        }
        isCancelled = true
    }
}