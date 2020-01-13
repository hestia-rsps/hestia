package worlds.gregs.hestia.content.command

import world.gregs.hestia.core.network.protocol.encoders.messages.WidgetComponentText
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.window.api.Windows
import worlds.gregs.hestia.core.display.window.model.WindowPane.*
import worlds.gregs.hestia.network.client.encoders.messages.WidgetVisibility

lateinit var windows: Windows

on<Command> {
    where { prefix == "inter" }
    then {
        val id = content.toInt()
        windows.closeWindows(entity, MAIN_SCREEN, true)
        windows.closeWindows(entity, OVERLAY, true)
        windows.closeWindows(entity, DIALOGUE_BOX, true)
        if(id != -1) {
            windows.openWindow(entity, id)
        }
        isCancelled = true
    }
}
on<Command> {
    where { prefix == "hidec" }
    then {
        val part = content.split(" ")
        val id = part[0].toInt()
        val component = part[1].toInt()
        val hide = part[2].toInt() == 1
        entity send WidgetVisibility(id, component, hide)
        isCancelled = true
    }
}

on<Command> {
    where { prefix == "itext" }
    then {
        val part = content.split(" ")
        val id = part[0].toInt()
        val component = part[1].toInt()
        val used = part[0].length + part[1].length + 2
        val text = content.substring(used).replace(" br ", "<br>")
        entity send WidgetComponentText(id, component, text)
        isCancelled = true
    }
}