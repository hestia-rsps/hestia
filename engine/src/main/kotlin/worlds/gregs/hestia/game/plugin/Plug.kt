package worlds.gregs.hestia.game.plugin

import com.artemis.ArtemisPlugin
import com.artemis.World
import worlds.gregs.hestia.artemis.event.ExtendedFastEventDispatcher

interface Plug : ArtemisPlugin {

    fun init(world: World, dispatcher: ExtendedFastEventDispatcher)
}