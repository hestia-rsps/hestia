package worlds.gregs.hestia.content.activity.quest

import worlds.gregs.hestia.core.display.client.model.Configs
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.QuestJournals
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.network.client.encoders.messages.Config

on<WindowOpened> {
    where { target == QuestJournals }
    then {
        val questPoints = 1
        entity send Config(Configs.QUEST_POINTS, questPoints)
        //TODO maximum quest points config
        entity send Config(Configs.UNSTABLE_FOUNDATIONS_QUEST, 1000)//Unlocks filter
    }
}