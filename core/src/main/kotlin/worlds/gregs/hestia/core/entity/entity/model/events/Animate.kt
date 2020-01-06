package worlds.gregs.hestia.core.entity.entity.model.events

import worlds.gregs.hestia.core.action.Action

data class Animate(val id: Int, val speed: Int = 0): Action()