package worlds.gregs.hestia.content.display

import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.model.variable.ListVariable

object Tabs {

    const val CombatStylesTab = "Combat Styles"
    const val TaskSystemTab = "Task System"
    const val StatsTab = "Stats"
    const val QuestJournalsTab = "Quest Journals"
    const val InventoryTab = "Inventory"
    const val WornEquipmentTab = "Worn Equipment"
    const val PrayerListTab = "Prayer List"
    const val MagicSpellbookTab = "Magic Spellbook"
    const val SummoningTab = "Summoning"
    const val FriendsListTab = "Friends List"
    const val FriendsChatTab = "Friends Chat"
    const val ClanChatTab = "Clan Chat"
    const val OptionsTab = "Options"
    const val EmotesTab = "Emotes"
    const val Music_PlayerTab = "Music Player"
    const val NotesTab = "Notes"

    val tabNames = listOf(
            CombatStylesTab,
            TaskSystemTab,
            StatsTab,
            QuestJournalsTab,
            InventoryTab,
            WornEquipmentTab,
            PrayerListTab,
            MagicSpellbookTab,
            SummoningTab,
            FriendsListTab,
            FriendsChatTab,
            ClanChatTab,
            OptionsTab,
            EmotesTab,
            Music_PlayerTab,
            NotesTab
    )

    init {
        ListVariable(168, Variable.Type.VARC, values = tabNames, defaultValue = InventoryTab).register("tab")
    }
}