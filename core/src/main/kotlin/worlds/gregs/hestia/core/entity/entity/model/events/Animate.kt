package worlds.gregs.hestia.core.entity.entity.model.events

import worlds.gregs.hestia.core.action.model.EntityAction

data class Animate(val id: Int, val speed: Int = 0): EntityAction()