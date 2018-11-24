package worlds.gregs.hestia.game.mob.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.game.archetypes.ArchetypeFactory
import worlds.gregs.hestia.game.component.*
import worlds.gregs.hestia.game.component.entity.ClientIndex
import worlds.gregs.hestia.game.mob.component.Mob
import worlds.gregs.hestia.game.component.entity.Type
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.movement.Mobile
import worlds.gregs.hestia.services.add

class MobFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Mob::class, ClientIndex::class, Renderable::class, Position::class, Type::class, Mobile::class)
    }
}