package worlds.gregs.hestia.core.plugins.widget.events

import worlds.gregs.hestia.api.widget.components.ScreenWidget
import worlds.gregs.hestia.artemis.InstantEvent

data class ScreenClosed(val entityId: Int, val screen: ScreenWidget) : InstantEvent