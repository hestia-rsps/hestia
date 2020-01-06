package worlds.gregs.hestia.core.display.client.model.events

import worlds.gregs.hestia.core.action.Action

data class Chat(val message: String, val type: Int = 0, val tile: Int = 0, val name: String? = null) : Action()