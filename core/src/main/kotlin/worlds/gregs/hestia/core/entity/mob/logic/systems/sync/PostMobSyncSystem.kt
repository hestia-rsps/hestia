package worlds.gregs.hestia.core.entity.mob.logic.systems.sync

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.components.ModelChange
import worlds.gregs.hestia.api.client.update.components.UpdateCombatLevel
import worlds.gregs.hestia.api.client.update.components.UpdateDisplayName
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