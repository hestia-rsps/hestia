package worlds.gregs.hestia.content.command

import world.gregs.hestia.core.network.protocol.encoders.messages.InterfaceComponentText
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces
import worlds.gregs.hestia.core.display.interfaces.model.Window.*
import worlds.gregs.hestia.network.client.encoders.messages.InterfaceVisibility
import worlds.gregs.hestia.core.script.on

lateinit var interfaces: Interfaces

on<Command> {
    where { prefix == "inter" }
    then {
        val id = content.toInt()
        if(id != -1) {
            interfaces.closeWindow(entity, interfaces.getWindow(id))
            interfaces.openInterface(entity, id)
        } else {
            interfaces.closeWindow(entity, MAIN_SCREEN)
            interfaces.closeWindow(entity, OVERLAY)
            interfaces.closeWindow(entity, DIALOGUE_BOX)
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
        entity send InterfaceVisibility(id, component, hide)
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
        entity send InterfaceComponentText(id, component, text)
        isCancelled = true
    }
}