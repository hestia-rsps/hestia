package worlds.gregs.hestia.core.display.widget.logic.systems.screen

import worlds.gregs.hestia.core.display.widget.model.components.screen.SoundOptions
import worlds.gregs.hestia.core.display.widget.logic.systems.BaseScreen

class SoundsOptionsSystem : BaseScreen(SoundOptions::class) {
    override fun getId(entityId: Int): Int {
        return ID
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, fromSlot: Int, toSlot: Int, option: Int) {
    }

    companion object {
        private const val ID = 743
    }
}