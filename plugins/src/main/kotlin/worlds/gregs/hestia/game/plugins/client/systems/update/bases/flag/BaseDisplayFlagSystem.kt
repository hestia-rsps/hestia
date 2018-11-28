package worlds.gregs.hestia.game.plugins.client.systems.update.bases.flag

import com.artemis.Aspect
import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.client.components.update.list.Entities
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.update.DisplayFlag

abstract class BaseDisplayFlagSystem(aspect: Aspect.Builder) : IteratingSystem(aspect) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private val checks = HashMap<DisplayFlag?, (Int, Int) -> Boolean>()
    private lateinit var map: HashMap<Int, DisplayFlag>

    fun addCheck(flag: DisplayFlag?, check: (Int, Int) -> Boolean) {
        checks[flag] = check
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
        list.forEach { other ->
            for ((flag, check) in checks) {
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