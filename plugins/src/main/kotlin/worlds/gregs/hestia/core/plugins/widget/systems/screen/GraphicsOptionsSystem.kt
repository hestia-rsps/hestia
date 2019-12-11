package worlds.gregs.hestia.core.plugins.widget.systems.screen

import worlds.gregs.hestia.core.plugins.widget.components.screen.GraphicsOptions
import worlds.gregs.hestia.core.plugins.widget.systems.BaseScreen

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