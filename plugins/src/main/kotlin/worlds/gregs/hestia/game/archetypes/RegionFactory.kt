package worlds.gregs.hestia.game.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.game.plugins.land.components.Objects
import worlds.gregs.hestia.game.plugins.region.components.RegionIdentifier
import worlds.gregs.hestia.services.add

class RegionFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(RegionIdentifier::class, Objects::class)
    }
}