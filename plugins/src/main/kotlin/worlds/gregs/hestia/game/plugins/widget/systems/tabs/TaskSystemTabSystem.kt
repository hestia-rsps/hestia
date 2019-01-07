package worlds.gregs.hestia.game.plugins.widget.systems.tabs

import com.artemis.annotations.Wire
import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.plugins.widget.components.screen.TaskList
import worlds.gregs.hestia.game.plugins.widget.components.tabs.TaskSystemTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseTab

@Wire(failOnNull = false, injectInherited = true)
class TaskSystemTabSystem : BaseTab(TaskSystemTab::class) {

    override var id = TAB_ID
    private var ui: UserInterface? = null

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
        when(componentId) {
            99, 142, 147, 152, 157, 162 -> {//Select task
                val index = if(componentId == 99) 0 else (componentId - 137)/5
            }
            102 -> {//Open tasks list
                ui?.open(entityId, TaskList())
            }
        }
    }

    companion object {
        private const val TAB_ID = 1056
        private const val RESIZABLE_INDEX = 91
        private const val FIXED_INDEX = 205
    }
}