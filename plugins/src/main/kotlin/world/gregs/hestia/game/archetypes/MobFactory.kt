package world.gregs.hestia.game.archetypes

import com.artemis.ArchetypeBuilder
import world.gregs.hestia.game.component.*
import world.gregs.hestia.game.component.entity.ClientIndex
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.game.component.entity.Type
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.movement.Mobile
import world.gregs.hestia.services.add

class MobFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Mob::class, ClientIndex::class, Renderable::class, Position::class, Type::class, Mobile::class)
    }
}