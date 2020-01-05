package worlds.gregs.hestia.core.display.window.model

sealed class FilterMode {
    object On : FilterMode()
    object Friends : FilterMode()
    object Off : FilterMode()
}