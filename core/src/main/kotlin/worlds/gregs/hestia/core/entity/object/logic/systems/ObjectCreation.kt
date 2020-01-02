package worlds.gregs.hestia.core.entity.`object`.logic.systems

import com.artemis.Archetype
import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.entity.`object`.logic.ObjectFactory
import worlds.gregs.hestia.core.entity.`object`.model.components.GameObject
import worlds.gregs.hestia.core.entity.`object`.model.components.ObjectType
import worlds.gregs.hestia.core.entity.`object`.model.components.Rotation
import worlds.gregs.hestia.core.entity.`object`.model.events.CreateObject
import worlds.gregs.hestia.core.entity.entity.model.components.Position

class ObjectCreation : PassiveSystem() {

    private lateinit var gameObjectMapper: ComponentMapper<GameObject>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var objectTypeMapper: ComponentMapper<ObjectType>
    private lateinit var rotationMapper: ComponentMapper<Rotation>
    private lateinit var archetype: Archetype

    override fun initialize() {
        archetype = ObjectFactory().getBuilder().build(world)
    }

    @Subscribe
    fun create(event: CreateObject): Int {
        val entityId = world.create(archetype)

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