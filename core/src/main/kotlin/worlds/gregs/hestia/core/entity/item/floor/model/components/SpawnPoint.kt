package worlds.gregs.hestia.core.entity.item.floor.model.components

import com.artemis.Component
import com.artemis.annotations.EntityId

/**
 * A spawn point
 * @param entityId The spawned entities id
 * @param delay The remaining delay until next spawn
 * @param delayTime The delay in ticks between respawn's
 */
data class SpawnPoint(@EntityId @JvmField var entityId: Int = -1, var delay: Int = 0, var delayTime: Int = 100) : Component()