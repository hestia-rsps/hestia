package worlds.gregs.hestia.core.display.client.model.events

import worlds.gregs.hestia.core.action.model.EntityAction

data class Command(val prefix: String, val content: String) : EntityAction()