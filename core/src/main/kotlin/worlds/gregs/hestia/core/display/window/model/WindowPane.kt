package worlds.gregs.hestia.core.display.window.model

import worlds.gregs.hestia.core.display.window.api.Windows.Companion.AncientSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.AreaStatusIcon
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.CastleWarsScore
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.CastleWarsStatusOverlay
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Chat1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Chat2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Chat3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Chat4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatBackground
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatBoth
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatBox
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatNp1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatNp2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatNp3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ChatNp4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ClanChat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.CombatStyles
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ConfirmDestroy
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ContainerContinue
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleChat1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleChat2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleChat3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleChat4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DoubleObjBox
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.DungeoneeringSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Emotes
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FilterButtons
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FriendsChat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.FriendsList
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.GardenQuiz
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.HealthOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Inventory
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.LevelUpDialog
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Logout
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.LunarSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MacroMimeEmotes
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MacroQuizShow
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MakeAmount
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Message5
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MessageNp5
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChat1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChat2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChat3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChat4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp1u
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp2u
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp3u
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobChatNp4u
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MobilisingArmies1
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ModernSpellbook
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi2Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi2Mes
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi3Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi3OffCentre
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi4Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi4Offscreen
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi5
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Multi5Chat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar2Wide
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar3
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar4
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MultiVar5
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.MusicPlayer
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Notes
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ObjBox
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ObjDialog
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.OpenUrl
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Options
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PickAKitten
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PickAPuppy
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PohHangman
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PohHangmanGerman
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrayerList
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrayerOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PriceCheckBoxTitle
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.PrivateChat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.QuestJournals
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Select2Models
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SkillCreation
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SkillCreationAmount
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SmeltType
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Stats
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.SummoningOrb
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TaskSystem
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TextBoxChat
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TextBoxContinue
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TextBoxContinue2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TextBoxContinueResizable
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TextBoxModel
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TextBoxModelSprite
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TradeSide
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TutorialText
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.TutorialText2
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WorldMap
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.WornEquipment

/**
 * A representation of all game interfaces
 * -1 parent equals dynamic gameframe id
 */
enum class WindowPane(val fixed: Int = 0, val resizable: Int, val parent: Int, vararg val windows: Int) {
    //Chat box
    CHAT_BACKGROUND(9, 9, ChatBox, ChatBackground),
    CHAT_BOX(192, 73, -1, ChatBox),
    CHAT_SETTINGS(68, 19, -1, FilterButtons),
    PRIVATE_CHAT(17, 72, -1, PrivateChat),

    //Minimap
    ENERGY_ORB(186, 179, -1, EnergyOrb),
    HEALTH_ORB(183, 177, -1, HealthOrb),
    PRAYER_ORB(185, 178, -1, PrayerOrb),
    SUMMONING_ORB(188, 180, -1, SummoningOrb),

    //Tab slots
    CLAN_CHAT(215, 101, -1, ClanChat),
    COMBAT_STYLES(204, 90, -1, CombatStyles),
    EMOTES(217, 103, -1, Emotes),
    FRIENDS_CHAT(214, 100, -1, FriendsChat),
    FRIENDS_LIST(213, 99, -1, FriendsList),
    INVENTORY(208, 94, -1, Inventory, TradeSide),
    LOGOUT(222, 108, -1, Logout),
    SPELLBOOK(211, 97, -1, ModernSpellbook, LunarSpellbook, AncientSpellbook, DungeoneeringSpellbook),
    MUSIC_PLAYER(218, 104, -1, MusicPlayer),
    NOTES(219, 105, -1, Notes),
    OPTIONS(216, 102, -1, Options),
    PRAYER_LIST(210, 96, -1, PrayerList),
    QUEST_JOURNALS(207, 93, -1, QuestJournals),
    STATS(206, 92, -1, Stats),
    TASK_SYSTEM(205, 91, -1, TaskSystem),
    WORN_EQUIPMENT(209, 95, -1, WornEquipment),

    //Main screen
    DIALOGUE_BOX(13, 13, ChatBox, Multi4Offscreen, TextBoxModelSprite, CastleWarsScore, CastleWarsStatusOverlay, MobChatNp4u, MobChatNp3u, MobChatNp1u, MobChatNp2u, ConfirmDestroy, GardenQuiz, ChatBoth, Select2Models, TextBoxModel, MacroMimeEmotes, MacroQuizShow, ContainerContinue, PriceCheckBoxTitle, TutorialText, ObjDialog, PohHangman, TutorialText2, OpenUrl, Multi3OffCentre, PohHangmanGerman, Multi2Mes, PickAPuppy, MultiVar2Wide, PickAKitten, LevelUpDialog, TextBoxContinue, TextBoxContinueResizable, MobilisingArmies1, SkillCreation, SmeltType, MakeAmount, TextBoxChat, TextBoxContinue2, DoubleChat3, DoubleChat2, DoubleChat1, DoubleChat4, DoubleObjBox, ObjBox, Chat1, Chat2, Chat3, Chat4, ChatNp1, ChatNp2, ChatNp3, ChatNp4, Message1, Message2, Message3, Message4, Message5, MessageNp1, MessageNp2, MessageNp3, MessageNp4, MessageNp5, Multi2, Multi2Chat, Multi3, Multi3Chat, Multi4, Multi4Chat, Multi5, Multi5Chat, MultiVar2, MultiVar3, MultiVar4, MultiVar5, MobChat1, MobChat2, MobChat3, MobChat4, MobChatNp1, MobChatNp2, MobChatNp3, MobChatNp4),
    AREA(15, 15, -1, AreaStatusIcon),
    OVERLAY(9, 12, -1),
    MAIN_SCREEN(9, 12, -1),
    FULL_SCREEN(0, 0, 0, ResizableGameframe, FixedGameframe, WorldMap),

    SKILL_CREATION(4, 4, SkillCreation, SkillCreationAmount),

}