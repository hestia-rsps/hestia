package worlds.gregs.hestia.game.plugins.client.systems.update.bases.flag

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.client.components.Entities
import worlds.gregs.hestia.api.core.components.Position
import worlds.gregs.hestia.game.update.DisplayFlag
import java.util.*

abstract class BaseDisplayFlagSystem(aspect: Aspect.Builder) : IteratingSystem(aspect) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private var checks : HashMap<DisplayFlag?, (Int, Int) -> Boolean>? = HashMap()
    private var sorted: SortedMap<DisplayFlag?, (Int, Int) -> Boolean>? = null
    private lateinit var map: HashMap<Int, DisplayFlag>

    fun addCheck(flag: DisplayFlag?, check: (Int, Int) -> Boolean) {
        checks!![flag] = check
        sorted = checks!!.toSortedMap(compareBy { it?.ordinal ?: DisplayFlag.values().size })
        //TODO probably a better way of doing this than having two maps
    }

    override fun initialize() {
        super.initialize()
        map = HashMap()
    }

    protected fun check(entityId: Int, mapper: ComponentMapper<out Entities>): HashMap<Int, DisplayFlag> {
        map.clear()
        if(mapper.has(entityId)) {
            check(entityId, mapper.get(entityId).list)
        }
        return map
    }

    protected fun check(entityId: Int, list: List<Int>): HashMap<Int, DisplayFlag>  {
        map.clear()
        if(checks != null) {
            checks = null
        }
        list.forEach { other ->
            for ((flag, check) in sorted!!) {
                if (check.invoke(entityId, other)) {
                    if (flag != null) {
                        map[other] = flag
                    }
                    break
                }
            }
        }
        return map
    }

    internal open fun withinDistance(position: Position, otherId: Int): Boolean {
        if(!positionMapper.has(otherId)) {
            return false
        }
        val other = positionMapper.get(otherId)
        return other.withinDistance(position)
    }
}