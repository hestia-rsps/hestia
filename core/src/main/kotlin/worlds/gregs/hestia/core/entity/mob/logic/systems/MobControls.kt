package worlds.gregs.hestia.core.entity.mob.logic.systems

import com.artemis.Entity
import worlds.gregs.hestia.core.display.update.model.components.ModelChange

class MobControls
/**
 * Change model, colours or textures (mob only)
 */
fun Entity.change(models: IntArray? = null, colours: IntArray? = null, textures: IntArray? = null) {
    edit().add(ModelChange(models, colours, textures))
}