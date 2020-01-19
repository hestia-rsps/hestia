package worlds.gregs.hestia.core.display.interfaces.model.components

import com.artemis.Component

class InterfaceRelationships : Component() {
    val relationships = mutableMapOf<Int, MutableList<Int>?>(0 to null)//Map<Interface, List<Child>>
}