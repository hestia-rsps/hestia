package worlds.gregs.hestia.core.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.core.plugins.land.components.LandObjects
import worlds.gregs.hestia.core.plugins.region.components.RegionIdentifier
import worlds.gregs.hestia.game.archetypes.ArchetypeFactory
import worlds.gregs.hestia.services.add

class RegionFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(RegionIdentifier::class, LandObjects::class)
    }
}