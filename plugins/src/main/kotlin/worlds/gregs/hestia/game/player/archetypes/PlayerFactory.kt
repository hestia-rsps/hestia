package worlds.gregs.hestia.game.player.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.game.archetypes.ArchetypeFactory
import worlds.gregs.hestia.game.component.*
import worlds.gregs.hestia.game.component.entity.ClientIndex
import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.component.map.*
import worlds.gregs.hestia.game.component.movement.Mobile
import worlds.gregs.hestia.game.component.update.appearance.AppearanceData
import worlds.gregs.hestia.game.component.update.DisplayName
import worlds.gregs.hestia.game.mob.component.MobViewDistance
import worlds.gregs.hestia.game.player.component.PlayerViewDistance
import worlds.gregs.hestia.services.add

class PlayerFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Player::class, ClientIndex::class, Renderable::class, Position::class, AppearanceData::class, DisplayName::class, Viewport::class, PlayerViewDistance::class, MobViewDistance::class, Mobile::class, LastLoadedRegion::class)
    }
}