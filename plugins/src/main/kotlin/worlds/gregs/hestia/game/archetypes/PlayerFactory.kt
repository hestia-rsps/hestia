package worlds.gregs.hestia.game.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.game.plugins.client.components.ClientMobPacket
import worlds.gregs.hestia.game.plugins.client.components.ClientPlayerPacket
import worlds.gregs.hestia.game.plugins.client.components.LastLoadedRegion
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalMobs
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalPlayers
import worlds.gregs.hestia.game.plugins.client.components.update.stage.EntityUpdates
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.entity.components.update.DisplayName
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Face
import worlds.gregs.hestia.game.plugins.mob.component.MobViewDistance
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.game.plugins.movement.components.Shift
import worlds.gregs.hestia.game.plugins.player.component.Player
import worlds.gregs.hestia.game.plugins.player.component.PlayerViewDistance
import worlds.gregs.hestia.game.plugins.player.component.update.AppearanceData
import worlds.gregs.hestia.services.add

class PlayerFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Player::class, ClientIndex::class, Renderable::class, Position::class, AppearanceData::class, DisplayName::class, Viewport::class, GlobalPlayers::class, GlobalMobs::class, ClientPlayerPacket::class, ClientMobPacket::class, EntityUpdates::class, PlayerViewDistance::class, MobViewDistance::class, Mobile::class, LastLoadedRegion::class, Shift::class, Face::class)
    }
}