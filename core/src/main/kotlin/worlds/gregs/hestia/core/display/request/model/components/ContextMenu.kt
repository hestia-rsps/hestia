package worlds.gregs.hestia.core.display.request.model.components

import com.artemis.Component
import worlds.gregs.hestia.core.display.interfaces.model.PlayerOptions

class ContextMenu : Component() {
    val options = arrayOfNulls<PlayerOptions?>(8)
}