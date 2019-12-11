package worlds.gregs.hestia.core.world.region.logic

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.core.world.land.components.LandObjects
import worlds.gregs.hestia.core.world.region.components.RegionIdentifier
import worlds.gregs.hestia.game.archetypes.ArchetypeFactory
import worlds.gregs.hestia.services.add

class RegionFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(RegionIdentifier::class, LandObjects::class)
    }
}