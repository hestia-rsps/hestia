package worlds.gregs.hestia.game.plugins.mob.systems

import com.artemis.Entity
import worlds.gregs.hestia.game.update.components.ModelChange

class MobControls
/**
 * Change model, colours or textures (mob only)
 */
fun Entity.change(models: IntArray? = null, colours: IntArray? = null, textures: IntArray? = null) {
    edit().add(ModelChange(models, colours, textures))
}