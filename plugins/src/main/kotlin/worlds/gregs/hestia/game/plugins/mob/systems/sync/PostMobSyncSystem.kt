package worlds.gregs.hestia.game.plugins.mob.systems.sync

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.mob.component.update.ModelChange
import worlds.gregs.hestia.game.plugins.mob.component.update.UpdateCombatLevel
import worlds.gregs.hestia.game.plugins.mob.component.update.UpdateDisplayName
import worlds.gregs.hestia.services.Aspect

class PostMobSyncSystem : IteratingSystem(Aspect.all(Renderable::class)) {

    private lateinit var updateDisplayNameMapper: ComponentMapper<UpdateDisplayName>
    private lateinit var updateCombatLevelMapper: ComponentMapper<UpdateCombatLevel>
    private lateinit var modelChangeMapper: ComponentMapper<ModelChange>

    override fun process(entityId: Int) {
        updateDisplayNameMapper.remove(entityId)
        updateCombatLevelMapper.remove(entityId)
        modelChangeMapper.remove(entityId)
    }
}