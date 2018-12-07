package worlds.gregs.hestia.game.plugins.core.systems.cache

import com.alex.store.Index
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.core.services.Cache

class CacheSystem : PassiveSystem() {

    fun getIndex(id: Int): Index {
        return Cache.getIndex(id)!!
    }

}