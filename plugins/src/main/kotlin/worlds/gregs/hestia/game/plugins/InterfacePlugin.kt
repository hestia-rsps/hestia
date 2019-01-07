package worlds.gregs.hestia.game.plugins

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.INTERFACE_PRIORITY
import worlds.gregs.hestia.game.plugins.widget.systems.UserInterfaceSystem
import worlds.gregs.hestia.game.plugins.widget.systems.frame.GameFrameSystem
import worlds.gregs.hestia.game.plugins.widget.systems.full.WorldMapSystem
import worlds.gregs.hestia.game.plugins.widget.systems.screen.*
import worlds.gregs.hestia.game.plugins.widget.systems.tabs.*

class InterfacePlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(UserInterfaceSystem(), CustomScreenWidgetSystem())
        b.with(INTERFACE_PRIORITY, GameFrameSystem())
        //Default Tabs
        b.with(ClanChatTabSystem(), CombatStylesTabSystem(), EmotesTabSystem(), FriendsChatTabSystem(), FriendsListTabSystem(), InventoryTabSystem(), LogoutTabSystem(), MagicSpellBookTabSystem(), MusicPlayerTabSystem(), NotesTabSystem(), OptionsTabSystem(), PrayerListTabSystem(), QuestJournalsTabSystem(), StatsTabSystem(), TaskSystemTabSystem(), WornEquipmentTabSystem())
        //Custom Tabs
        b.with(ConquestCommandsTabSystem(), CustomQuestJournalTabSystem())
        //Options Screens
        b.with(GraphicsOptionsSystem(), SoundsOptionsSystem())
        //Tasks screens
        b.with(TaskListSystem())
        //Stats screens
        b.with(SkillMenuSystem())
        //Worn Equipment screens
        b.with(EquipmentBonusesSystem(), PriceCheckerSystem(), ItemsKeptOnDeathSystem())
        //Full screens
        b.with(WorldMapSystem())
    }

}