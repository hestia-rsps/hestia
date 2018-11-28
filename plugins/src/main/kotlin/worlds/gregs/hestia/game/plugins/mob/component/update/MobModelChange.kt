package worlds.gregs.hestia.game.plugins.mob.component.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class MobModelChange : Component() {
    var models: IntArray? = null
    var colours: IntArray? = null
    var textures: IntArray? = null
}

fun Entity.change(models: IntArray? = null, colours: IntArray? = null, textures: IntArray? = null) {
    val change = MobModelChange()
    change.apply {
        this.models = models
        this.colours = colours
        this.textures = textures
    }
    edit().add(change)
}