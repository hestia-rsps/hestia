package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
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