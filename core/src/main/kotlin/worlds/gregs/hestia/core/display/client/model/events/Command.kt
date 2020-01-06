package worlds.gregs.hestia.core.display.client.model.events

import worlds.gregs.hestia.core.action.Action

data class Command(val prefix: String, val content: String) : Action()