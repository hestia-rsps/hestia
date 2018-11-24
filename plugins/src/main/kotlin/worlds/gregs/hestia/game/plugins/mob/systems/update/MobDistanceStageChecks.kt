package worlds.gregs.hestia.game.plugins.mob.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.plugins.entity.systems.update.stage.EntityStageSystem
import worlds.gregs.hestia.game.plugins.mob.component.Mob
import worlds.gregs.hestia.game.plugins.mob.component.MobViewDistance

class MobDistanceStageChecks : PassiveSystem() {

    private lateinit var entityStageSystem: EntityStageSystem
    private lateinit var mobMapper: ComponentMapper<Mob>
    private lateinit var mobViewDistanceMapper: ComponentMapper<MobViewDistance>

    override fun initialize() {
        super.initialize()
        entityStageSystem.addCheck { player, other ->
            if(mobMapper.has(other)) {
                mobViewDistanceMapper.get(player).distance
            } else {
                null
            }
        }
    }
}