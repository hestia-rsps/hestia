package worlds.gregs.hestia.game.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.core.components.entity.ClientIndex
import worlds.gregs.hestia.game.plugins.mob.component.Type
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.mob.component.Mob
import worlds.gregs.hestia.game.plugins.movement.components.Mobile
import worlds.gregs.hestia.services.add

class MobFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Mob::class, ClientIndex::class, Renderable::class, Position::class, Type::class, Mobile::class)
    }
}