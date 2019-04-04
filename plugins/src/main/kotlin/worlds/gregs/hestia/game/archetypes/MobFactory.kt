package worlds.gregs.hestia.game.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.game.client.ClientIndex
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.api.mob.Mob
import worlds.gregs.hestia.game.entity.Type
import worlds.gregs.hestia.game.update.components.direction.Face
import worlds.gregs.hestia.api.movement.Mobile
import worlds.gregs.hestia.services.add

class MobFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Mob::class, ClientIndex::class, Renderable::class, Position::class, Type::class, Mobile::class, Face::class)
    }
}