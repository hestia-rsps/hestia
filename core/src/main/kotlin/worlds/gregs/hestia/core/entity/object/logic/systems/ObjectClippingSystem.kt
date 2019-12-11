package worlds.gregs.hestia.core.entity.`object`.logic.systems

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.api.`object`.GameObject
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.api.map.ClippingMasks
import worlds.gregs.hestia.api.region.Regions
import worlds.gregs.hestia.game.map.Flags.FLOOR_DECO_BLOCKS_WALK
import worlds.gregs.hestia.game.map.MapConstants.isOutOfBounds
import worlds.gregs.hestia.core.entity.`object`.model.components.ObjectType
import worlds.gregs.hestia.core.entity.`object`.model.components.Rotation
import worlds.gregs.hestia.core.misc.systems.cache.ObjectDefinitionSystem
import worlds.gregs.hestia.core.world.map.systems.ClippingMaskSystem.Companion.ADD_MASK
import worlds.gregs.hestia.core.world.map.systems.ClippingMaskSystem.Companion.REMOVE_MASK
import worlds.gregs.hestia.services.Aspect

@Wire(failOnNull = false)
class ObjectClippingSystem : SubscriptionSystem(Aspect.all(GameObject::class)) {

    private lateinit var gameObjectMapper: ComponentMapper<GameObject>
    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var objectTypeMapper: ComponentMapper<ObjectType>
    private lateinit var rotationMapper: ComponentMapper<Rotation>
    private var masks: ClippingMasks? = null
    private var regions: Regions? = null
    private lateinit var objectDef: ObjectDefinitionSystem

    override fun inserted(entityId: Int) {
        clipObject(entityId, ADD_MASK)
    }

    override fun removed(entityId: Int) {
        clipObject(entityId, REMOVE_MASK)
    }

    private fun clipObject(entityId: Int, changeType: Int) {
        val position = positionMapper.get(entityId)
        val objectType = objectTypeMapper.get(entityId)
        val type = objectType.type
        val rotation = rotationMapper.get(entityId).rotation
        val gameObject = gameObjectMapper.get(entityId)

        val regionEntityId = regions?.getEntityId(position.regionId) ?: return

        if (isOutOfBounds(position.xInRegion, position.yInRegion)) {
            return
        }

        val definition = objectDef.get(gameObject.id)

        //If isn't solid
        if (definition.solid == 0) {
            return
        }

        when (type) {
            in 0..3 -> masks?.changeWall(regionEntityId, position.xInRegion, position.yInRegion, position.plane, type, rotation, definition.isProjectileClipped, !definition.ignoreClipOnAlternativeRoute, changeType)
            in 9..21 -> {
                var sizeX = definition.sizeX
                var sizeY = definition.sizeY
                if (rotation and 0x1 == 1) {
                    sizeX = definition.sizeY
                    sizeY = definition.sizeX
                }
                masks?.changeObject(regionEntityId, position.xInRegion, position.yInRegion, position.plane, sizeX, sizeY, definition.isProjectileClipped, !definition.ignoreClipOnAlternativeRoute, changeType)
            }
            22 -> {
                if(definition.solid == 1) {
                    masks?.changeMask(regionEntityId, position.xInRegion, position.yInRegion, position.plane, FLOOR_DECO_BLOCKS_WALK, changeType)
                }
            }
        }
    }
}