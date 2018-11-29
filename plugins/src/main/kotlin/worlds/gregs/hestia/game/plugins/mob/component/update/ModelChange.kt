package worlds.gregs.hestia.game.plugins.mob.component.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ModelChange() : Component() {

    constructor(models: IntArray? = null, colours: IntArray? = null, textures: IntArray? = null) : this() {
        this.models = models
        this.colours = colours
        this.textures = textures
    }

    var models: IntArray? = null
    var colours: IntArray? = null
    var textures: IntArray? = null
}

fun Entity.change(models: IntArray? = null, colours: IntArray? = null, textures: IntArray? = null) {
    edit().add(ModelChange(models, colours, textures))
}