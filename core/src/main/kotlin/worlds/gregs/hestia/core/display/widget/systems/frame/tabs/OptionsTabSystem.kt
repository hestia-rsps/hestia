package worlds.gregs.hestia.core.display.widget.systems.frame.tabs

import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.OptionsTab
import worlds.gregs.hestia.core.display.widget.model.components.screen.GraphicsOptions
import worlds.gregs.hestia.core.display.widget.model.components.screen.SoundOptions
import worlds.gregs.hestia.core.display.widget.systems.BaseFrame

class OptionsTabSystem : BaseFrame(OptionsTab::class) {

    private lateinit var ui: UserInterface
    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            14 -> {//Graphics settings
                ui.open(entityId, GraphicsOptions())
            }
            16 -> {//Audio settings
                ui.open(entityId, SoundOptions())
            }
            6 -> {//Mouse buttons
            }
            3 -> {//Profanity filter
            }
            4 -> {//Chat effects
            }
            5 -> {//Chat setup
            }
            7 -> {//Accept aid
            }
            8 -> {//House options
            }
            18 -> {//Adventures log options
            }
        }
    }

    companion object {
        private const val TAB_ID = 261
        private const val RESIZABLE_INDEX = 102
        private const val FIXED_INDEX = 216
    }
}