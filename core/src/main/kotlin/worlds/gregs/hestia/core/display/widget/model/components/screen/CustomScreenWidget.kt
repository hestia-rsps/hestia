package worlds.gregs.hestia.core.display.widget.model.components.screen

import worlds.gregs.hestia.core.display.widget.model.components.ScreenWidget

class CustomScreenWidget() : ScreenWidget() {
    constructor(id: Int): this() {
        this.id = id
    }

    var id = 0
}