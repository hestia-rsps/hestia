package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.LogoutTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame

class LogoutTabSystem : BaseFrame(LogoutTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
        when(componentId) {
            6 -> {//Lobby
                world.delete(entityId)
            }
            13 -> {//Login
                world.delete(entityId)
            }
        }
    }

    companion object {
        private const val TAB_ID = 182
        private const val RESIZABLE_INDEX = 108
        private const val FIXED_INDEX = 222
    }
}