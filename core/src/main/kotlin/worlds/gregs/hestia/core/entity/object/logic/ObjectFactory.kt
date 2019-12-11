package worlds.gregs.hestia.core.entity.`object`.logic

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.api.`object`.GameObject
import worlds.gregs.hestia.core.entity.`object`.model.components.ObjectType
import worlds.gregs.hestia.core.entity.`object`.model.components.Rotation
import worlds.gregs.hestia.game.archetypes.ArchetypeFactory
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.services.add

class ObjectFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(GameObject::class, Position::class, ObjectType::class, Rotation::class)
    }
}