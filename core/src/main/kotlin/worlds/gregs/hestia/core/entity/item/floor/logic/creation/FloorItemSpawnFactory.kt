package worlds.gregs.hestia.core.entity.item.floor.logic.creation

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.artemis.add
import worlds.gregs.hestia.core.entity.entity.api.ArchetypeFactory
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.SpawnPoint

class FloorItemSpawnFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(SpawnPoint::class, Type::class, Position::class, Amount::class)
    }
}