package worlds.gregs.hestia.core.display.window.api

import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.window.model.WindowPane
import worlds.gregs.hestia.core.display.window.model.components.GameFrame
import worlds.gregs.hestia.core.display.window.model.components.WindowRelationships

abstract class Windows : SubscriptionSystem(Aspect.all(WindowRelationships::class, GameFrame::class)) {

    sealed class WindowResult {
        object Opened : WindowResult()
        sealed class Issue : WindowResult() {
            data class MissingParent(val id: Int) : Issue()
            object AlreadyOpen : Issue()
            object AnotherOpen : Issue()
        }
    }

    abstract fun setWindowPane(id: Int, pane: WindowPane)

    abstract fun openWindow(entityId: Int, id: Int): WindowResult

    abstract fun refreshWindow(entityId: Int, window: Int)

    abstract fun closeWindow(entityId: Int, window: Int, silent: Boolean = false)

    abstract fun closeWindows(entityId: Int, pane: WindowPane, silent: Boolean = false)

    abstract fun getWindow(entityId: Int, pane: WindowPane): Int?

    abstract fun updateGameframe(entityId: Int)

    abstract fun hasWindow(entityId: Int, pane: WindowPane): Boolean

    abstract fun hasWindow(entityId: Int, window: Int): Boolean

    abstract fun verifyWindow(entityId: Int, hash: Int): Boolean

    abstract fun getPane(id: Int): WindowPane

    companion object {
        //https://www.rune-server.ee/runescape-development/rs-503-client-server/configuration/683354-interface-list-official-names.html
        const val FixedGameframe = 548
        const val ResizableGameframe = 746

        const val ChatBackground = 137
        const val ChatBox = 752
        const val FilterButtons = 751
        const val PrivateChat = 754

        const val HealthOrb = 748
        const val PrayerOrb = 749
        const val EnergyOrb = 750
        const val SummoningOrb = 747

        const val ClanChat = 1110
        const val CombatStyles = 884
        const val Emotes = 590
        const val FriendsChat = 1109
        const val FriendsList = 550
        const val Inventory = 679
        const val Logout = 182
        const val ModernSpellbook = 192
        const val AncientSpellbook = 193
        const val LunarSpellbook = 430
        const val DungeoneeringSpellbook = 950
        const val MusicPlayer = 187
        const val Notes = 34
        const val Options = 261
        const val PrayerList = 271
        const val QuestJournals = 190
        const val Stats = 320
        const val TaskSystem = 1056
        const val WornEquipment = 387

        const val WorldMap = 755

        const val EquipmentBonuses = 667
        const val FriendsChatSetup = 1108
        const val GraphicsOptions = 742
        const val ItemsKeptOnDeath = 17
        const val PriceChecker = 206
        const val SkillLevelDetails = 741
        const val SkillGuide = 499
        const val SoundOptions = 743
        const val TaskList = 917
        const val TradeConfirm = 334
        const val TradeMain = 335
        const val TradeSide = 336
        const val AssistXP = 301
        const val AreaStatusIcon = 745
    }
}