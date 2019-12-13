package worlds.gregs.hestia.core.display.widget.logic.systems.screen

import worlds.gregs.hestia.core.display.widget.model.components.screen.GraphicsOptions
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseScreen

class GraphicsOptionsSystem : BaseScreen(GraphicsOptions::class) {

    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        println("Click $componentId")
    }

    companion object {
        private const val ID = 742
    }
}