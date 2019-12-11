package worlds.gregs.hestia.core.display.widget

import com.artemis.WorldConfigurationBuilder
import worlds.gregs.hestia.core.display.widget.systems.UserInterfaceSystem
import worlds.gregs.hestia.core.display.widget.systems.frame.GameFrameSystem
import worlds.gregs.hestia.core.display.widget.systems.frame.chat.*
import worlds.gregs.hestia.core.display.widget.systems.frame.orbs.EnergyOrbSystem
import worlds.gregs.hestia.core.display.widget.systems.frame.orbs.HealthOrbSystem
import worlds.gregs.hestia.core.display.widget.systems.frame.orbs.PrayerOrbSystem
import worlds.gregs.hestia.core.display.widget.systems.frame.orbs.SummoningOrbSystem
import worlds.gregs.hestia.core.display.widget.systems.frame.tabs.*
import worlds.gregs.hestia.core.display.widget.systems.full.WorldMapSystem
import worlds.gregs.hestia.core.display.widget.systems.screen.*
import worlds.gregs.hestia.game.plugin.Plugin
import worlds.gregs.hestia.game.plugin.Plugin.Companion.INTERFACE_PRIORITY

class WidgetPlugin : Plugin {

    override fun setup(b: WorldConfigurationBuilder) {
        b.with(UserInterfaceSystem(), CustomScreenWidgetSystem())
        b.with(INTERFACE_PRIORITY, GameFrameSystem())
        //Tabs
        b.with(ClanChatTabSystem(), CombatStylesTabSystem(), EmotesTabSystem(), FriendsChatTabSystem(), FriendsListTabSystem(), InventoryTabSystem(), LogoutTabSystem(), MagicSpellBookTabSystem(), MusicPlayerTabSystem(), NotesTabSystem(), OptionsTabSystem(), PrayerListTabSystem(), QuestJournalsTabSystem(), StatsTabSystem(), TaskSystemTabSystem(), WornEquipmentTabSystem())
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
        //Chat box
        b.with(ChatSettingsSystem(), ChatBoxSystem(), PrivateChatSystem(), ChatBackgroundSystem(), DialogueBoxSystem())
        //Minimap orbs
        b.with(HealthOrbSystem(), PrayerOrbSystem(), EnergyOrbSystem(), SummoningOrbSystem())
        //Friends chat
        b.with(FriendsChatSetupSystem())
    }

}