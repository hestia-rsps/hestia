package worlds.gregs.hestia.core.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.api.`object`.GameObject
import worlds.gregs.hestia.core.plugins.`object`.component.ObjectType
import worlds.gregs.hestia.core.plugins.`object`.component.Rotation
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.services.add
import worlds.gregs.hestia.game.archetypes.ArchetypeFactory

class ObjectFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(GameObject::class, Position::class, ObjectType::class, Rotation::class)
    }
}