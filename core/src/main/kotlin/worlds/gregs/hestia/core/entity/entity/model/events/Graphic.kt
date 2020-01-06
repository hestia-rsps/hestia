package worlds.gregs.hestia.core.entity.entity.model.events

import worlds.gregs.hestia.core.action.Action

data class Graphic(val id: Int, val delay: Int = 0, val height: Int = 0, val rotation: Int = 0, val forceRefresh: Boolean = false) : Action()