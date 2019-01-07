package worlds.gregs.hestia.game.plugins.widget.systems.screen

import worlds.gregs.hestia.game.plugins.widget.components.screen.SoundOptions
import worlds.gregs.hestia.game.plugins.widget.systems.BaseScreen

class SoundsOptionsSystem : BaseScreen(SoundOptions::class) {
    override var id = 743

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }
}