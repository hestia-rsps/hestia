package worlds.gregs.hestia.content.activity.quest

import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.QuestJournals
import worlds.gregs.hestia.core.display.variable.model.events.SendVariable
import worlds.gregs.hestia.core.display.interfaces.model.events.InterfaceOpened
import worlds.gregs.hestia.core.display.variable.model.variable.IntVariable
import worlds.gregs.hestia.core.display.variable.model.variable.StringMapVariable

IntVariable(101, Variable.Type.VARP, true, 0).register("quest_points")

StringMapVariable(281, Variable.Type.VARP, true, defaultValue = "complete", values = mapOf(
        0 to "unstarted",
        1 to "incomplete",
        1000 to "complete"
)).register("unstable_foundations")

on<InterfaceOpened> {
    where { id == QuestJournals }
    then {
        entity perform SendVariable("quest_points")
        entity perform SendVariable("unstable_foundations")//Unlocks filter
        //TODO maximum quest points config
    }
}