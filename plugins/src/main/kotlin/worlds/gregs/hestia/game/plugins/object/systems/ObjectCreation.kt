package worlds.gregs.hestia.game.plugins.`object`.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.`object`.GameObject
import worlds.gregs.hestia.game.archetypes.EntityFactory
import worlds.gregs.hestia.game.archetypes.ObjectFactory
import worlds.gregs.hestia.artemis.events.CreateObject
import worlds.gregs.hestia.game.plugins.`object`.component.ObjectType
import worlds.gregs.hestia.game.plugins.`object`.component.Rotation
import worlds.gregs.hestia.game.entity.components.Position

class ObjectCreation : PassiveSystem() {

    private lateinit var gameObjectMapper: ComponentMapper<GameObject>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var objectTypeMapper: ComponentMapper<ObjectType>
    private lateinit var rotationMapper: ComponentMapper<Rotation>

    @Subscribe
    fun create(event: CreateObject): Int {
        val entityId = EntityFactory.create(ObjectFactory::class)

        val gameObject = gameObjectMapper.get(entityId)
        gameObject.id = event.objectId

        val position = positionMapper.get(entityId)
        position.set(event.x, event.y, event.plane)

        val objectType = objectTypeMapper.get(entityId)
        objectType.type = event.type

        val rotation = rotationMapper.get(entityId)
        rotation.rotation = event.rotation
        return entityId
    }
}