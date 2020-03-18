package worlds.gregs.hestia.core.display.interfaces.model

import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.AncientSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.AreaStatusIcon
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.CastleWarsScore
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.CastleWarsStatusOverlay
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Chat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatBackground
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatBoth
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatBox
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ChatNp4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ClanChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.CombatStyles
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ConfirmDestroy
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ContainerContinue
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleChat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DoubleObjBox
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.DungeoneeringSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Emotes
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.EnergyOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FilterButtons
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FixedGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FriendsChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.FriendsList
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.GardenQuiz
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.HealthOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Inventory
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.LevelUpDialog
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Logout
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.LunarSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MacroMimeEmotes
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MacroQuizShow
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MakeAmount
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Message5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MessageNp5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChat4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp1u
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp2u
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp3u
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.NpcChatNp4u
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MobilisingArmies1
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ModernSpellbook
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi2Mes
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi3Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi3OffCentre
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi4Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi4Offscreen
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Multi5Chat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar2Wide
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar3
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar4
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MultiVar5
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.MusicPlayer
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Notes
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ObjBox
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ObjDialog
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.OpenUrl
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Options
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PickAKitten
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PickAPuppy
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PohHangman
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PohHangmanGerman
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerList
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrayerOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PriceCheckBoxTitle
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.PrivateChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.QuestJournals
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.ResizableGameframe
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Select2Models
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SkillCreation
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SkillCreationAmount
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SmeltType
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SoulWarsGameOverlay
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SoulWarsRewards
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SoulWarsWaitingOverlay
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Stats
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SummoningOrb
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TaskSystem
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TextBoxChat
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TextBoxContinue
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TextBoxContinue2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TextBoxContinueResizable
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TextBoxModel
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TextBoxModelSprite
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TradeSide
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TutorialText
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.TutorialText2
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WorldMap
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.WornEquipment

/**
 * List of interface parents and child indices
 * @param parent The parent interface that [ids] should be display on (-1 for gameframe)
 * @param fixed The component index of the parent [ids] should be displayed on when fixed gameframe
 * @param resizable The component index of the parent [ids] should be displayed on when resizable gameframe
 * @param ids List of interfaces which use this [Window]
 */
enum class Window(val parent: Int, val fixed: Int, val resizable: Int, vararg val ids: Int) {
    //Chat box
    CHAT_BACKGROUND(ChatBox, 9, 9, ChatBackground),
    CHAT_BOX(-1, 192, 73, ChatBox),
    CHAT_SETTINGS(-1, 68, 19, FilterButtons),
    PRIVATE_CHAT(-1, 17, 72, PrivateChat),

    //Minimap
    ENERGY_ORB(-1, 186, 179, EnergyOrb),
    HEALTH_ORB(-1, 183, 177, HealthOrb),
    PRAYER_ORB(-1, 185, 178, PrayerOrb),
    SUMMONING_ORB(-1, 188, 180, SummoningOrb),

    //Tab slots
    CLAN_CHAT(-1, 215, 101, ClanChat),
    COMBAT_STYLES(-1, 204, 90, CombatStyles),
    EMOTES(-1, 217, 103, Emotes),
    FRIENDS_CHAT(-1, 214, 100, FriendsChat),
    FRIENDS_LIST(-1, 213, 99, FriendsList),
    INVENTORY(-1, 208, 94, Inventory, TradeSide),
    LOGOUT(-1, 222, 108, Logout),
    SPELLBOOK(-1, 211, 97, ModernSpellbook, LunarSpellbook, AncientSpellbook, DungeoneeringSpellbook),
    MUSIC_PLAYER(-1, 218, 104, MusicPlayer),
    NOTES(-1, 219, 105, Notes),
    OPTIONS(-1, 216, 102, Options),
    PRAYER_LIST(-1, 210, 96, PrayerList),
    QUEST_JOURNALS(-1, 207, 93, QuestJournals),
    STATS(-1, 206, 92, Stats),
    TASK_SYSTEM(-1, 205, 91, TaskSystem),
    WORN_EQUIPMENT(-1, 209, 95, WornEquipment),

    //Main screen
    DIALOGUE_BOX(ChatBox, 13, 13, Multi4Offscreen, TextBoxModelSprite, CastleWarsScore, CastleWarsStatusOverlay, NpcChatNp4u, NpcChatNp3u, NpcChatNp1u, NpcChatNp2u, ConfirmDestroy, GardenQuiz, ChatBoth, Select2Models, TextBoxModel, MacroMimeEmotes, MacroQuizShow, ContainerContinue, PriceCheckBoxTitle, TutorialText, ObjDialog, PohHangman, TutorialText2, OpenUrl, Multi3OffCentre, PohHangmanGerman, Multi2Mes, PickAPuppy, MultiVar2Wide, PickAKitten, LevelUpDialog, TextBoxContinue, TextBoxContinueResizable, MobilisingArmies1, SkillCreation, SmeltType, MakeAmount, TextBoxChat, TextBoxContinue2, DoubleChat3, DoubleChat2, DoubleChat1, DoubleChat4, DoubleObjBox, ObjBox, Chat1, Chat2, Chat3, Chat4, ChatNp1, ChatNp2, ChatNp3, ChatNp4, Message1, Message2, Message3, Message4, Message5, MessageNp1, MessageNp2, MessageNp3, MessageNp4, MessageNp5, Multi2, Multi2Chat, Multi3, Multi3Chat, Multi4, Multi4Chat, Multi5, Multi5Chat, MultiVar2, MultiVar3, MultiVar4, MultiVar5, NpcChat1, NpcChat2, NpcChat3, NpcChat4, NpcChatNp1, NpcChatNp2, NpcChatNp3, NpcChatNp4),
    AREA(-1, 15, 15, AreaStatusIcon),
    OVERLAY(-1, 9, 12, SoulWarsWaitingOverlay, SoulWarsGameOverlay),
    MAIN_SCREEN(-1, 9, 12),
    FULL_SCREEN(0, 0, 0, ResizableGameframe, FixedGameframe, WorldMap),

    SKILL_CREATION(SkillCreation, 4, 4, SkillCreationAmount),

}