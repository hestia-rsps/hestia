package worlds.gregs.hestia.core.display.request.model

sealed class FilterMode {
    object On : FilterMode()
    object Friends : FilterMode()
    object Off : FilterMode()
}