package worlds.gregs.hestia.game.plugins.movement.systems.update

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.stage.LocalDisplayFlagSystem
import worlds.gregs.hestia.game.plugins.movement.components.types.Moving
import worlds.gregs.hestia.game.plugins.movement.components.types.RunStep
import worlds.gregs.hestia.game.plugins.movement.components.types.WalkStep
import worlds.gregs.hestia.game.update.DisplayFlag

@Wire(failOnNull = false)
class MovementStageChecks : PassiveSystem() {

    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var walkMapper: ComponentMapper<WalkStep>
    private var localStageSystem: LocalDisplayFlagSystem? = null

    override fun initialize() {
        super.initialize()
        localStageSystem?.addCheck(DisplayFlag.MOVE) { _, other -> movingMapper.has(other)}
        localStageSystem?.addCheck(DisplayFlag.RUNNING) { _, other ->runMapper.has(other)}
        localStageSystem?.addCheck(DisplayFlag.WALKING) { _, other -> walkMapper.has(other)}
    }

}