package worlds.gregs.hestia.game.archetypes

import com.artemis.ArchetypeBuilder
import worlds.gregs.hestia.api.`object`.GameObject
import worlds.gregs.hestia.game.plugins.`object`.component.ObjectType
import worlds.gregs.hestia.game.plugins.`object`.component.Rotation
import worlds.gregs.hestia.game.entity.Position
import worlds.gregs.hestia.services.add

class ObjectFactory : ArchetypeFactory {

    override fun getBuilder(): ArchetypeBuilder {
        return ArchetypeBuilder().add(GameObject::class, Position::class, ObjectType::class, Rotation::class)
    }
}