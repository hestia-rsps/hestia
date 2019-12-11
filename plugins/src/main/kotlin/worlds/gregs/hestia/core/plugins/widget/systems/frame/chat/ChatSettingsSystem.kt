package worlds.gregs.hestia.core.plugins.widget.systems.frame.chat

import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.ChatSettings
import worlds.gregs.hestia.core.plugins.widget.systems.BaseFrame

class ChatSettingsSystem : BaseFrame(ChatSettings::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, interfaceHash: Int, componentId: Int, option: Int) {
        when(componentId) {
            31 -> {//Regular chat
                when(option) {
                    1 -> {//View
                    }
                    2 -> {//All game
                    }
                    4 -> {//Filter game
                    }
                }
            }
            25 -> {//Private chat
            }
            19 -> {//Trade chat
            }
            16 -> {//Assist chat
            }
            13 -> {//Report abuse
            }
        }
    }

    companion object {
        private const val TAB_ID = 751
        private const val RESIZABLE_INDEX = 19
        private const val FIXED_INDEX = 68
    }
}