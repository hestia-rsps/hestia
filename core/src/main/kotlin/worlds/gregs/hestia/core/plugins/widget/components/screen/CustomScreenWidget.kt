package worlds.gregs.hestia.core.plugins.widget.components.screen

import worlds.gregs.hestia.api.widget.components.ScreenWidget

class CustomScreenWidget() : ScreenWidget() {
    constructor(id: Int): this() {
        this.id = id
    }

    var id = 0
}