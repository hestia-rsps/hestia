package worlds.gregs.hestia.core.display.widget.logic.systems.screen

import worlds.gregs.hestia.core.display.widget.model.components.screen.SkillLevelDetails
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseScreen

class SkillLevelDetailsSystem : BaseScreen(SkillLevelDetails::class) {
    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val ID = 741
    }
}