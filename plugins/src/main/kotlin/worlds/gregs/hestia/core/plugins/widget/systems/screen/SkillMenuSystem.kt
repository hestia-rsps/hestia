package worlds.gregs.hestia.core.plugins.widget.systems.screen

import worlds.gregs.hestia.game.Configs.SKILL_MENU
import worlds.gregs.hestia.core.plugins.widget.components.screen.SkillMenu
import worlds.gregs.hestia.core.plugins.widget.systems.BaseScreen
import worlds.gregs.hestia.network.client.encoders.messages.Config
import worlds.gregs.hestia.services.send

class SkillMenuSystem : BaseScreen(SkillMenu::class) {

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            in 10..25 -> {
                val index = 0
                val menu = 0
                //TODO config system so can get and use the previous skill menu value (aka StatsTabSystem's menuIndex)
                es.send(entityId, Config(SKILL_MENU, index * 1024 + menu))
            }
            28 -> {//More info
            }
        }
    }

    companion object {
        private const val ID = 499
    }
}