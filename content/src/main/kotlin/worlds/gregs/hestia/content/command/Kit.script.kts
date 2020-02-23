package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance

val bandosSet = equipAll(11724, 11726, 11732, 4151, 6570, 10828, 20072, 6585, 7462)
val runeSet = equipAll(1127, 1079, 1333, 1201, 1007, 1725)
val pernix = equipAll(20147, 20151, 20155, 21790, 13740, 18357, 20769, 19335, 7462)

on<Command> {
    where { prefix == "kit" }
    then {
        println(ContainerType.EQUIPMENT transform pernix)
        entity perform UpdateAppearance()
    }
}