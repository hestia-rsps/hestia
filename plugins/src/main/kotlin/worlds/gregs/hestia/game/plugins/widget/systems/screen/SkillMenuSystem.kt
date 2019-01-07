package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.game.Configs.SKILL_MENU
import worlds.gregs.hestia.game.plugins.widget.components.screen.SkillMenu
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen
import worlds.gregs.hestia.network.out.Config
import worlds.gregs.hestia.services.send

class SkillMenuSystem : BaseScreen(SkillMenu::class) {
    override var id = 499

    override fun click(entityId: Int, componentId: Int, option: Int) {
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
}