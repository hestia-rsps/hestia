package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.game.plugins.widget.components.screen.GraphicsOptions
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen

class GraphicsOptionsSystem : BaseScreen(GraphicsOptions::class) {
    override var id = 742

    override fun click(entityId: Int, componentId: Int, option: Int) {
        println("Click $componentId")
    }
}