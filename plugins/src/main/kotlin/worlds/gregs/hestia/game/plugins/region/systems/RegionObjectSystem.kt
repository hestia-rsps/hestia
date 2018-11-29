package worlds.gregs.hestia.game.plugins.region.systems

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.map.GameObject
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.systems.cache.ObjectDefinitionSystem
import worlds.gregs.hestia.game.plugins.region.components.Clipping
import worlds.gregs.hestia.game.plugins.region.components.Dynamic
import worlds.gregs.hestia.game.plugins.region.components.Objects
import worlds.gregs.hestia.game.plugins.region.components.ProjectileClipping
import java.util.concurrent.CopyOnWriteArrayList

class RegionObjectSystem : PassiveSystem() {
    private lateinit var objectsMapper: ComponentMapper<Objects>
    private lateinit var clippingMapper: ComponentMapper<Clipping>
    private lateinit var projectileClippingMapper: ComponentMapper<ProjectileClipping>
    private lateinit var dynamicMapper: ComponentMapper<Dynamic>
    private lateinit var rms: RegionMapSystem
    private lateinit var objectDef: ObjectDefinitionSystem

    fun addObject(entityId: Int, `object`: GameObject, plane: Int, localX: Int, localY: Int) {
        addMapObject(entityId, `object`, localX, localY)
        val objectMap = objectsMapper.get(entityId)

        if (objectMap.objects == null) {
            objectMap.objects = Array(REGION_PLANES) { Array<Array<Array<GameObject?>?>>(REGION_SIZE) { arrayOfNulls(REGION_SIZE) } }
        }
        val tileObjects = objectMap.objects!![plane][localX][localY]
        if (tileObjects == null)
            objectMap.objects!![plane][localX][localY] = arrayOf(`object`)
        else {
            val newTileObjects = arrayOfNulls<GameObject>(tileObjects.size + 1)
            newTileObjects[tileObjects.size] = `object`
            System.arraycopy(tileObjects, 0, newTileObjects, 0, tileObjects.size)
            objectMap.objects!![plane][localX][localY] = newTileObjects
        }
    }

    fun addMapObject(entityId: Int, `object`: GameObject, x: Int, y: Int) {
        if(dynamicMapper.has(entityId)) {
            return
        }

        val plane = `object`.plane
        val type = `object`.type
        val rotation = `object`.rotation

        if (isOutOfBounds(x, y)) {
            return
        }

        val objectDefinition = objectDef.get(`object`.id)

        if ((type == 22 && objectDefinition.solid != 0) || (type != 22 && objectDefinition.solid == 0)) {
            return
        }

        val map = clippingMapper.create(entityId)
        val clippedOnlyMap = projectileClippingMapper.create(entityId)

        when (type) {
            in 0..3 -> {
                rms.changeWall(entityId, map, plane, x, y, type, rotation, objectDefinition.isProjectileClipped, true, RegionMapSystem.ADD_MASK)
                if (objectDefinition.isProjectileClipped) {
                    rms.changeWall(entityId, clippedOnlyMap, plane, x, y, type, rotation, objectDefinition.isProjectileClipped, true, RegionMapSystem.ADD_MASK)
                }
            }
            in 9..21 -> {
                val sizeX: Short
                val sizeY: Short
                if (rotation != 1 && rotation != 3) {
                    sizeX = objectDefinition.sizeX
                    sizeY = objectDefinition.sizeY
                } else {
                    sizeX = objectDefinition.sizeY
                    sizeY = objectDefinition.sizeX
                }
                rms.changeObject(entityId, map, plane, x, y, sizeX, sizeY, objectDefinition.isProjectileClipped, true, RegionMapSystem.ADD_MASK)
                if (objectDefinition.isProjectileClipped) {
                    rms.changeObject(entityId, clippedOnlyMap, plane, x, y, sizeX, sizeY, objectDefinition.isProjectileClipped, true, RegionMapSystem.ADD_MASK)
                }
            }
            22 -> {
            }
        }
    }

    fun removeMapObject(entityId: Int, `object`: GameObject, x: Int, y: Int) {
        val plane = `object`.plane
        val type = `object`.type
        val rotation = `object`.rotation

        if (isOutOfBounds(x, y)) {
            return
        }

        val objectDefinition = objectDef.get(`object`.id) // load here
        if (if (type == 22) objectDefinition.solid != 0 else objectDefinition.solid == 0) {
            return
        }

        val map = clippingMapper.create(entityId)
        val clippedOnlyMap = projectileClippingMapper.create(entityId)
        if (type in 0..3) {
            rms.changeWall(entityId, map, plane, x, y, type, rotation, objectDefinition.isProjectileClipped, true, RegionMapSystem.REMOVE_MASK)
            if (objectDefinition.isProjectileClipped) {
                rms.changeWall(entityId, clippedOnlyMap, plane, x, y, type, rotation, objectDefinition.isProjectileClipped, true, RegionMapSystem.REMOVE_MASK)
            }
        } else if (type in 9..21) {
            val sizeX: Short
            val sizeY: Short
            if (rotation != 1 && rotation != 3) {
                sizeX = objectDefinition.sizeX
                sizeY = objectDefinition.sizeY
            } else {
                sizeX = objectDefinition.sizeY
                sizeY = objectDefinition.sizeX
            }
            rms.changeObject(entityId, map, plane, x, y, sizeX, sizeY, objectDefinition.isProjectileClipped, true, RegionMapSystem.REMOVE_MASK)
            if (objectDefinition.isProjectileClipped) {
                rms.changeObject(entityId, clippedOnlyMap, plane, x, y, sizeX, sizeY, objectDefinition.isProjectileClipped, true, RegionMapSystem.REMOVE_MASK)
            }
        } else if (type == 22) {
//            map.removeFloor(plane, x, y);
        }
    }

    internal fun getObject(entityId: Int, plane: Int, x: Int, y: Int): GameObject? {
        val objects = getObjects(entityId, plane, x, y) ?: return null
        return objects[0]
    }

    internal fun getObject(entityId: Int, plane: Int, x: Int, y: Int, type: Int): GameObject? {
        val objects = getObjects(entityId, plane, x, y) ?: return null
        for (`object` in objects) {
            if (`object`!!.type == type) {
                return `object`
            }
        }
        return null
    }

    // override by static region to get objects from needed
    fun getObjects(entityId: Int, plane: Int, x: Int, y: Int): Array<GameObject?>? {
        // if objects just loaded now will return null, anyway after they load
        // will return correct so np
        val objectMap = objectsMapper.get(entityId)
        return if (objectMap.objects == null) {
            null
        } else {
            objectMap.objects!![plane][x][y]
        }
    }

    fun getObject(entityId: Int, id: Int, tile: Position): GameObject? {
        val localX = tile.x % 64
        val localY = tile.y % 64
        if (isOutOfBounds(localX, localY)) {
            return null
        }
        val spawnedObject = getSpawnedObject(entityId, tile, id)
        if (spawnedObject != null) {
            return spawnedObject
        }
        val removedObject = getRemovedObject(entityId, tile)
        if (removedObject != null && removedObject.id == id) {
            return null
        }
        val mapObjects = getObjects(entityId, tile.plane, localX, localY) ?: return null
        for (`object` in mapObjects) {
            if (`object`!!.id == id) {
                return `object`
            }
        }
        return null
    }

    fun getSpawnedObject(entityId: Int, tile: Position, id: Int): GameObject? {
        val objectMap = objectsMapper.get(entityId)
        if (objectMap.spawnedObjects == null) {
            return null
        }
        for (`object` in objectMap.spawnedObjects!!) {
            if (`object`.x == tile.x && `object`.y == tile.y && `object`.plane == tile.plane
                    && (`object`.id == id || id == -1)) {
                return `object`
            }
        }
        return null
    }

    private fun getRemovedObject(entityId: Int, tile: Position): GameObject? {
        val objectMap = objectsMapper.get(entityId)
        if (objectMap.removedObjects == null) {
            return null
        }
        for (`object` in objectMap.removedObjects!!) {
            if (`object`.x == tile.x && `object`.y == tile.y && `object`.plane == tile.plane) {
                return `object`
            }
        }
        return null
    }

    internal fun addObject(entityId: Int, `object`: GameObject) {
        val objectMap = objectsMapper.get(entityId)
        if (objectMap.spawnedObjects == null) {
            objectMap.spawnedObjects = CopyOnWriteArrayList()
        }
        objectMap.spawnedObjects!!.add(`object`)
    }

    fun removeObject(entityId: Int, `object`: GameObject) {
        val objectMap = objectsMapper.get(entityId)
        if (objectMap.spawnedObjects == null) {
            return
        }
        objectMap.spawnedObjects!!.remove(`object`)
    }

    internal fun addRemovedObject(entityId: Int, `object`: GameObject) {
        val objectMap = objectsMapper.get(entityId)
        if (objectMap.removedObjects == null) objectMap.removedObjects = CopyOnWriteArrayList()
        objectMap.removedObjects!!.add(`object`)
    }

    internal fun removeRemovedObject(entityId: Int, `object`: GameObject) {
        val objectMap = objectsMapper.get(entityId)
        if (objectMap.removedObjects == null) return
        objectMap.removedObjects!!.remove(`object`)
    }

    fun getSpawnedObjects(entityId: Int): MutableList<GameObject>? {
        val objectMap = objectsMapper.get(entityId)
        return objectMap.spawnedObjects
    }

    fun getRemovedObjects(entityId: Int): List<GameObject>? {
        val objectMap = objectsMapper.get(entityId)
        return objectMap.removedObjects
    }

    internal fun getRealObject(entityId: Int, spawnObject: GameObject): GameObject? {
        val localX = spawnObject.x % 64
        val localY = spawnObject.y % 64
        val mapObjects = getObjects(entityId, spawnObject.plane, localX, localY) ?: return null
        for (`object` in mapObjects) {
            if (`object`!!.type == spawnObject.type) {
                return `object`
            }
        }
        return null
    }

    fun containsObject(entityId: Int, id: Int, tile: Position): Boolean {
        val localX = tile.x % 64
        val localY = tile.y % 64
        if (isOutOfBounds(localX, localY)) {
            return false
        }
        val spawnedObject = getSpawnedObject(entityId, tile, id)
        if (spawnedObject != null) {
            return spawnedObject.id == id
        }
        val removedObject = getRemovedObject(entityId, tile)
        if (removedObject != null && removedObject.id == id) {
            return false
        }
        val mapObjects = getObjects(entityId, tile.plane, localX, localY) ?: return false
        for (`object` in mapObjects) {
            if (`object`!!.id == id) {
                return true
            }
        }
        return false
    }

    fun clear() {
//        objects = null
    }

    companion object {
        const val REGION_PLANES = 4
        const val REGION_SIZE = 64

        private val REGION_RANGE = 0 until REGION_SIZE

        fun isOutOfBounds(localX: Int, localY: Int): Boolean {
            return localX !in REGION_RANGE || localY !in REGION_RANGE
        }
    }
}