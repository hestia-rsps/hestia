package world.gregs.hestia.game.archetypes

import com.artemis.ArchetypeBuilder
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.Renderable
import world.gregs.hestia.services.add

class RegionFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Renderable::class, Position::class)
    }
}