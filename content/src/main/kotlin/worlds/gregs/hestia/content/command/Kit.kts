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
val elegant = equipAll(15339, 15434, 10404, 10394, 6790)
val sets = arrayOf(rune, bandos, pernix, elegant)
val veracDSq = arrayOf(4753, 9748, 1712, 4151, 4757, 1187, 4759, 7462, 11732)
val toragTokXil = arrayOf(10828, 1712, 4151, 4749, 6524, 10828, 4751, 7462, 11732)
val warriorVerac = arrayOf(3753, 1712, 4151, 4757, 10828, 4759, 7461, 11732, 6737)
val runeDefWhip = arrayOf(12680, 1712, 4151, 1127, 8850, 10828, 4759, 7462, 11732, 6737)
val blackDds = arrayOf(12680, 1712, 5698, 10551, 10828, 2497, 7462, 11732, 6737)
val antidragon = arrayOf(12680, 2513, 1712, 4151, 10551, 1540, 10828, 4087, 7462, 11732, 6737)
val toragDScim = arrayOf(10828, 1712, 4587, 4749, 8850, 4087, 7461, 11732)
val voidRange = arrayOf(11664, 6585, 9185, 8839, 3842, 8840, 8842, 6328, 6733)
val karilMeleeTank = arrayOf(10828, 6585, 4151, 4736, 3842, 4759, 8842, 11732, 6737)
val agsPure = arrayOf(12681, 6585, 11694, 10551, 3480, 7462, 11732, 6737)
val pureStyle = arrayOf(2595, 1725, 4587, 2591, 2497, 3105)
val dmed = arrayOf(1149, 1712, 4587, 4757, 4585, 11126, 3105)
val barrageTank = arrayOf(4753, 6585, 4675, 4712, 11283, 4714, 7462, 11732, 6731)

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