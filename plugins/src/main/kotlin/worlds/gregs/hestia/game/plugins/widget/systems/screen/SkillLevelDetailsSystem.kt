package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.game.plugins.widget.components.screen.SkillLevelDetails
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen

class SkillLevelDetailsSystem : BaseScreen(SkillLevelDetails::class) {
    override var id = 741

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }
}