package worlds.gregs.hestia.core.display.widget.logic.systems.frame.tabs

import worlds.gregs.hestia.core.display.client.model.Configs.QUEST_POINTS
import worlds.gregs.hestia.core.display.client.model.Configs.UNSTABLE_FOUNDATIONS_QUEST
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.QuestJournalsTab
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseFrame
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.service.send

class QuestJournalsTabSystem : BaseFrame(QuestJournalsTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun open(entityId: Int) {
        super.open(entityId)
        val questPoints = 1
        es.send(entityId, Config(QUEST_POINTS, questPoints))
        //TODO maximum quest points config
        es.send(entityId, Config(UNSTABLE_FOUNDATIONS_QUEST, 1000))//Not having it complete disables chats //TODO check this is true once impl chat box
        //TODO unlock free/members filter/dropdown list
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 190
        private const val RESIZABLE_INDEX = 93
        private const val FIXED_INDEX = 207
    }
}