package worlds.gregs.hestia.game.bot.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.game.archetypes.ArchetypeFactory
import worlds.gregs.hestia.game.component.*
import worlds.gregs.hestia.game.component.entity.ClientIndex
import worlds.gregs.hestia.game.player.component.Player
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.movement.Mobile
import worlds.gregs.hestia.game.component.update.appearance.AppearanceData
import worlds.gregs.hestia.game.component.update.DisplayName
import worlds.gregs.hestia.services.add

class BotFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Player::class, ClientIndex::class, Renderable::class, Position::class, AppearanceData::class, DisplayName::class, Mobile::class)
    }
}