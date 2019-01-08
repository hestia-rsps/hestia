package worlds.gregs.hestia.api.widget

import worlds.gregs.hestia.api.widget.components.FullScreenWidget

class GameFrame() : FullScreenWidget() {

    constructor(displayMode: Int, width: Int, height: Int) : this() {
        this.displayMode = displayMode
        this.width = width
        this.height = height
    }

    var openTab = 4//Inventory
    var displayMode = 0
    var width = 0
    var height = 0

    val resizable: Boolean
        get() {
            return displayMode == 2 || displayMode == 3
        }

    val fixed: Boolean
        get() {
            return !resizable
        }

}