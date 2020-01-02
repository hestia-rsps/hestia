package worlds.gregs.hestia.core.entity.item.floor.logic.creation

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.artemis.add
import worlds.gregs.hestia.core.entity.entity.api.ArchetypeFactory
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount

class FloorItemFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(Type::class, Amount::class, Position::class)
    }
}