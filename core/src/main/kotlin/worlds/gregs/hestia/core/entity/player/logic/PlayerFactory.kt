package worlds.gregs.hestia.core.entity.player.logic

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.core.display.client.model.components.ClientIndex
import worlds.gregs.hestia.core.display.client.model.components.Viewport
import worlds.gregs.hestia.core.display.update.model.components.AppearanceData
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.direction.Face
import worlds.gregs.hestia.core.world.movement.api.Mobile
import worlds.gregs.hestia.core.world.movement.model.components.Shift
import worlds.gregs.hestia.game.entity.Player
import worlds.gregs.hestia.core.display.widget.api.GameFrame
import worlds.gregs.hestia.core.display.client.model.components.LastLoadedRegion
import worlds.gregs.hestia.core.display.widget.model.components.frame.chat.ChatBackground
import worlds.gregs.hestia.core.display.widget.model.components.frame.chat.ChatBox
import worlds.gregs.hestia.core.display.widget.model.components.frame.chat.ChatSettings
import worlds.gregs.hestia.core.display.widget.model.components.frame.chat.PrivateChat
import worlds.gregs.hestia.core.display.widget.model.components.frame.orbs.EnergyOrb
import worlds.gregs.hestia.core.display.widget.model.components.frame.orbs.HealthOrb
import worlds.gregs.hestia.core.display.widget.model.components.frame.orbs.PrayerOrb
import worlds.gregs.hestia.core.display.widget.model.components.frame.orbs.SummoningOrb
import worlds.gregs.hestia.core.display.widget.model.components.frame.tabs.*
import worlds.gregs.hestia.core.entity.entity.api.ArchetypeFactory
import worlds.gregs.hestia.core.task.model.components.TaskQueue
import worlds.gregs.hestia.core.world.collision.model.components.Ghost
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.service.add

class PlayerFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Player::class, ClientIndex::class, Renderable::class, Position::class, AppearanceData::class, DisplayName::class, Viewport::class, Mobile::class, LastLoadedRegion::class, Shift::class, Face::class, Ghost::class, GameFrame::class,
                ClanChatTab::class, CombatStylesTab::class, EmotesTab::class, FriendsChatTab::class, FriendsListTab::class, InventoryTab::class, MagicSpellBookTab::class, MusicPlayerTab::class, NotesTab::class, OptionsTab::class, PrayerListTab::class, StatsTab::class, TaskSystemTab::class, WornEquipmentTab::class, QuestJournalsTab::class, LogoutTab::class, ChatSettings::class, ChatBox::class, ChatBackground::class, PrivateChat::class,
                HealthOrb::class, PrayerOrb::class, EnergyOrb::class, SummoningOrb::class, TaskQueue::class)
    }
}