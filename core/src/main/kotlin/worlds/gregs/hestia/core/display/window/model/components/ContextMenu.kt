package worlds.gregs.hestia.core.display.window.model.components

import com.artemis.Component
import worlds.gregs.hestia.core.display.window.model.PlayerOptions

class ContextMenu : Component() {
    val options = arrayOfNulls<PlayerOptions?>(8)
}