package worlds.gregs.hestia.core.display.window.model.components

import com.artemis.Component

class WindowRelationships : Component() {
    val relationships = mutableMapOf<Int, MutableList<Int>?>(0 to null)//Map<Window, List<Child>>
}