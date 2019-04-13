package worlds.gregs.hestia.game.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.api.client.components.ClientIndex
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.game.plugins.collision.components.Ghost
import worlds.gregs.hestia.api.client.update.components.DisplayName
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.api.client.update.components.AppearanceData
import worlds.gregs.hestia.services.add

class BotFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Player::class, ClientIndex::class, Renderable::class, Position::class, AppearanceData::class, DisplayName::class, Mobile::class, Ghost::class)
    }
}