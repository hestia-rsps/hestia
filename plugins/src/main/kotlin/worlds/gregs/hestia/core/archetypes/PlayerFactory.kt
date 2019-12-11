package worlds.gregs.hestia.core.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.api.client.components.ClientIndex
import worlds.gregs.hestia.api.client.components.Viewport
import worlds.gregs.hestia.api.client.update.components.AppearanceData
import worlds.gregs.hestia.api.client.update.components.DisplayName
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.components.direction.Face
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.api.widget.GameFrame
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.core.plugins.client.components.LastLoadedRegion
import worlds.gregs.hestia.core.plugins.collision.components.Ghost
import worlds.gregs.hestia.core.plugins.task.components.TaskQueue
import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.ChatBackground
import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.ChatBox
import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.ChatSettings
import worlds.gregs.hestia.core.plugins.widget.components.frame.chat.PrivateChat
import worlds.gregs.hestia.core.plugins.widget.components.frame.orbs.EnergyOrb
import worlds.gregs.hestia.core.plugins.widget.components.frame.orbs.HealthOrb
import worlds.gregs.hestia.core.plugins.widget.components.frame.orbs.PrayerOrb
import worlds.gregs.hestia.core.plugins.widget.components.frame.orbs.SummoningOrb
import worlds.gregs.hestia.core.plugins.widget.components.frame.tabs.*
import worlds.gregs.hestia.services.add
import worlds.gregs.hestia.game.archetypes.ArchetypeFactory

class PlayerFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Player::class, ClientIndex::class, Renderable::class, Position::class, AppearanceData::class, DisplayName::class, Viewport::class, Mobile::class, LastLoadedRegion::class, Shift::class, Face::class, Ghost::class, GameFrame::class,
                ClanChatTab::class, CombatStylesTab::class, EmotesTab::class, FriendsChatTab::class, FriendsListTab::class, InventoryTab::class, MagicSpellBookTab::class, MusicPlayerTab::class, NotesTab::class, OptionsTab::class, PrayerListTab::class, StatsTab::class, TaskSystemTab::class, WornEquipmentTab::class, QuestJournalsTab::class, LogoutTab::class, ChatSettings::class, ChatBox::class, ChatBackground::class, PrivateChat::class,
                HealthOrb::class, PrayerOrb::class, EnergyOrb::class, SummoningOrb::class, TaskQueue::class)
    }
}