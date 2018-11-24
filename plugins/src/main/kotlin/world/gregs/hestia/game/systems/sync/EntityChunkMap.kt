package world.gregs.hestia.game.systems.sync

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class EntityChunkMap : PassiveSystem() {
    private val map = HashMap<Int, ArrayList<Int>>()

    fun getAll(xRange: IntRange, yRange: IntRange, plane: Int): List<Int> {
        val list = ArrayList<Int>()
        for(x in xRange) {
            for(y in yRange) {
                val hash = EntityChunkSystem.toHash(x, y, plane)
                if(map.containsKey(hash)) {
                    list.addAll(map[hash]!!)
                }
            }
        }
        return list
    }

    fun get(hash: Int): List<Int>? {
        return if(map.containsKey(hash)) {
            map[hash]
        } else {
            null
        }
    }

    fun add(hash: Int, entityId: Int) {
        if(!map.containsKey(hash)) {
            map[hash] = ArrayList()
        }

        map[hash]?.add(entityId)
    }

    fun remove(hash: Int, entityId: Int) {
        if(map.containsKey(hash)) {
            map[hash]?.remove(entityId)
        }
    }
}