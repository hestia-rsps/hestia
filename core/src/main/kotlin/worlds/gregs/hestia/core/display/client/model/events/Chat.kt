package worlds.gregs.hestia.core.display.client.model.events

import worlds.gregs.hestia.core.action.model.EntityAction

data class Chat(val message: String, val type: Int = 0, val tile: Int = 0, val name: String? = null) : EntityAction()