package worlds.gregs.hestia.game.plugins.widget.systems.frame.tabs

import worlds.gregs.hestia.game.plugins.widget.components.frame.tabs.FriendsListTab
import worlds.gregs.hestia.game.plugins.widget.systems.BaseFrame
import worlds.gregs.hestia.network.game.out.Friend
import worlds.gregs.hestia.services.send

class FriendsListTabSystem : BaseFrame(FriendsListTab::class) {

    override fun getId(entityId: Int): Int {
        return TAB_ID
    }

    override fun getIndex(resizable: Boolean): Int {
        return if(resizable) RESIZABLE_INDEX else FIXED_INDEX
    }

    override fun open(entityId: Int) {
        super.open(entityId)
//        es.send(entityId, UnlockFriendsList())
        es.send(entityId, Friend("Bob", "Bob", 1, true, true))
    }

    override fun click(entityId: Int, componentId: Int, option: Int) {
    }

    companion object {
        private const val TAB_ID = 550
        private const val RESIZABLE_INDEX = 99
        private const val FIXED_INDEX = 213
    }
}