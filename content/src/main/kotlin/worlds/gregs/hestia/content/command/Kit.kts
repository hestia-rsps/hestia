package worlds.gregs.hestia.content.command

import arrow.core.andThen
import worlds.gregs.hestia.content.activity.combat.equipment.EquippedItem
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.entity.item.container.logic.clear
import worlds.gregs.hestia.core.entity.item.container.logic.equipAll
import worlds.gregs.hestia.core.entity.item.container.model.ContainerType
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.core.script.on

val bandos = equipAll(11724, 11726, 11732, 18349, 6570, 10828, 20072, 6585, 7462, 15220, 20965)
val rune = equipAll(1127, 1079, 4151, 20072, 6570, 1725, 11732, 10828)
val pernix = equipAll(20147, 20151, 20155, 21790, 13740, 18357, 20769, 19335, 7462, 9242, 15019)
val sets = arrayOf(rune, bandos, pernix)

on<Command> {
    where { prefix == "kit" }
    then {
        println(ContainerType.EQUIPMENT transform (clear() andThen sets.random()))
        val equipment = entity container ContainerType.EQUIPMENT
        equipment.forEach {
            if(it != null) {
                entity perform EquippedItem(it)
            }
        }
        entity perform UpdateAppearance()
    }
}