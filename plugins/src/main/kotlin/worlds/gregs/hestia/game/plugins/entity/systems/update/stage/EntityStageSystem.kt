package worlds.gregs.hestia.game.plugins.entity.systems.update.stage

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.stage.GlobalDisplayFlagSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.stage.LocalDisplayFlagSystem
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.entity.systems.ViewDistanceSystem
import worlds.gregs.hestia.game.update.DisplayFlag

class EntityStageSystem : PassiveSystem() {

    private val checks = ArrayList<(Int, Int) -> Int?>()

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var localStageSystem: LocalDisplayFlagSystem
    private lateinit var globalStageSystem: GlobalDisplayFlagSystem

    fun addCheck(check: (Int, Int) -> Int?) {
        checks.add(check)
    }

    override fun initialize() {
        super.initialize()
        localStageSystem.addCheck(DisplayFlag.REMOVE) { player, other ->
            !world.entityManager.isActive(other) /* || local.hasFinished() */ || !withinDistance(player, other)
        }

        globalStageSystem.addCheck(DisplayFlag.ADD) { player, other ->
            /* !global.hasFinished() && */ withinDistance(player, other)
        }
    }

    private fun withinDistance(player: Int, otherId: Int): Boolean {
        if(!positionMapper.has(otherId)) {
            return false
        }
        val position = positionMapper.get(player)
        val other = positionMapper.get(otherId)
        var distance = ViewDistanceSystem.DEFAULT_VIEW_DISTANCE
        for(check in checks) {
            val value = check.invoke(player, otherId)
            if(value != null) {
                distance = value
                break
            }
        }
        return other.withinDistance(position, distance)
    }
}