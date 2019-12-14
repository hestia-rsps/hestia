package worlds.gregs.hestia.core.display.widget.logic.systems.screen

import worlds.gregs.hestia.core.display.widget.model.components.screen.TaskList
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseScreen

class TaskListSystem : BaseScreen(TaskList::class) {

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
        when (componentId) {
            165 -> {//Toggle tutorial
            }
            142 -> {//Turn off pop-ups
            }
            in 30..33 -> {//Tabs
                val tab = componentId - 30
            }
            41 -> {//Pin task
            }
            40 -> {//"Back" hide popup
            }
            114 -> {//Filter sets
            }
            106 -> {//Hide completed
            }
            117, 121 -> {//Area-list
            }
            123, 125, 127, 129, 131, 133, 135, 137, 153 -> {//Area selection
                val area = if (componentId == 153) 7 else (componentId - 123) / 2
            }
        }
    }

    companion object {
        private const val ID = 917
    }
}