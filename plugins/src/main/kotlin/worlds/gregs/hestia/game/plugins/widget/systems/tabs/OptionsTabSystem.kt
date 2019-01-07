package worlds.gregs.hestia.game.plugins.widget.systems.tabs

import worlds.gregs.hestia.api.widget.UserInterface
import worlds.gregs.hestia.game.plugins.widget.components.screen.GraphicsOptions
import worlds.gregs.hestia.game.plugins.widget.components.screen.SoundOptions
import worlds.gregs.hestia.game.plugins.widget.components.tabs.OptionsTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseTab

class OptionsTabSystem : BaseTab(OptionsTab::class) {

    private lateinit var ui: UserInterface
    override var id = TAB_ID

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
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