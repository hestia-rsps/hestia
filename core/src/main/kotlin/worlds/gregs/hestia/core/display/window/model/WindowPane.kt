package worlds.gregs.hestia.core.display.window.model

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.AncientSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatBackground
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatBox
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatSettings
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ClanChat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.CombatStyles
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DungeoneeringSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Emotes
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FriendsChat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FriendsList
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.HealthOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Inventory
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Logout
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.LunarSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ModernSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MusicPlayer
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Notes
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Options
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrayerList
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrayerOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrivateChat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.QuestJournals
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Stats
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SummoningOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TaskSystem
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WorldMap
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WornEquipment

/**
 * A representation of all game interfaces
 */

enum class WindowPane(val fixed: Int = 0, val resizable: Int, vararg val windows: Int) {
    //Chat box
    CHAT_BACKGROUND(9, 9, ChatBackground),
    CHAT_BOX(192, 73, ChatBox),
    CHAT_SETTINGS(68, 19, ChatSettings),
    PRIVATE_CHAT(17, 72, PrivateChat),

    //Minimap
    ENERGY_ORB(186, 179, EnergyOrb),
    HEALTH_ORB(183, 177, HealthOrb),
    PRAYER_ORB(185, 178, PrayerOrb),
    SUMMONING_ORB(188, 180, SummoningOrb),

    //Tab slots
    CLAN_CHAT(215, 101, ClanChat),
    COMBAT_STYLES(204, 90, CombatStyles),
    EMOTES(217, 103, Emotes),
    FRIENDS_CHAT(214, 100, FriendsChat),
    FRIENDS_LIST(213, 99, FriendsList),
    INVENTORY(208, 94, Inventory),
    LOGOUT(222, 108, Logout),
    SPELLBOOK(211, 97, ModernSpellbook, LunarSpellbook, AncientSpellbook, DungeoneeringSpellbook),
    MUSIC_PLAYER(218, 104, MusicPlayer),
    NOTES(219, 105, Notes),
    OPTIONS(216, 102, Options),
    PRAYER_LIST(210, 96, PrayerList),
    QUEST_JOURNALS(207, 93, QuestJournals),
    STATS(206, 92, Stats),
    TASK_SYSTEM(205, 91, TaskSystem),
    WORN_EQUIPMENT(209, 95, WornEquipment),

    //Main screen
    OVERLAY(9, 12),
    MAIN_SCREEN(9, 12),
    FULL_SCREEN(0, 0, ResizableGameframe, FixedGameframe, WorldMap);
}