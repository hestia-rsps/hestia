package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import worlds.gregs.hestia.game.Configs.EVENT_EMOTES
import worlds.gregs.hestia.game.Configs.GOLBIN_QUEST_EMOTES
import worlds.gregs.hestia.game.Configs.HALLOWEEN_EMOTES
import worlds.gregs.hestia.game.Configs.STRONGHOLD_SECURITY_EMOTES
import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.EmotesTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.game.out.ComponentSettings
import worlds.gregs.hestia.network.game.out.Config
import worlds.gregs.hestia.services.send

class EmotesTabSystem : BaseFrame(EmotesTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)

        for (i in 0 until 149) {
            es.send(entityId, ComponentSettings(getId(entityId), i, 0, 190, 2150))
        }

        es.send(entityId, Config(GOLBIN_QUEST_EMOTES, 7))
        es.send(entityId, Config(STRONGHOLD_SECURITY_EMOTES, 7))
        es.send(entityId, Config(HALLOWEEN_EMOTES, 249852))
        es.send(entityId, Config(EVENT_EMOTES, 65535))
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 590
        private const val RESIZABLE_INDEX = 103
        private const val FIXED_INDEX = 217
    }
}