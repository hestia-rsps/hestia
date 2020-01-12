package worlds.gregs.hestia.content.activity.quest

import worlds.gregs.hestia.core.display.window.api.Variable
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.QuestJournals
import worlds.gregs.hestia.core.display.window.model.events.SendVariable
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.variable.IntVariable
import worlds.gregs.hestia.core.display.window.model.variable.StringVariable

IntVariable(101, Variable.Type.VARP, true, 0).register("quest_points")

StringVariable(281, Variable.Type.VARP, true, "complete", mapOf(
        0 to "unstarted",
        1 to "incomplete",
        1000 to "complete"
)).register("unstable_foundations")

on<WindowOpened> {
    where { target == QuestJournals }
    then {
        entity perform SendVariable("quest_points")
        entity perform SendVariable("unstable_foundations")//Unlocks filter
        //TODO maximum quest points config
    }
}