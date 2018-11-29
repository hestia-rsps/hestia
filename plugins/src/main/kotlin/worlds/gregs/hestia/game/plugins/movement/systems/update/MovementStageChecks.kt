package worlds.gregs.hestia.game.plugins.movement.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.stage.LocalDisplayFlagSystem
import worlds.gregs.hestia.game.plugins.movement.components.types.Moving
import worlds.gregs.hestia.game.plugins.movement.components.types.Run
import worlds.gregs.hestia.game.plugins.movement.components.types.Walk
import worlds.gregs.hestia.game.update.DisplayFlag

class MovementStageChecks : PassiveSystem() {

    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var runMapper: ComponentMapper<Run>
    private lateinit var walkMapper: ComponentMapper<Walk>
    private lateinit var localStageSystem: LocalDisplayFlagSystem

    override fun initialize() {
        super.initialize()
        localStageSystem.addCheck(DisplayFlag.MOVE) { _, other -> movingMapper.has(other)}
        localStageSystem.addCheck(DisplayFlag.RUNNING) { _, other -> runMapper.has(other)}
        localStageSystem.addCheck(DisplayFlag.WALKING) { _, other -> walkMapper.has(other)}
    }

}