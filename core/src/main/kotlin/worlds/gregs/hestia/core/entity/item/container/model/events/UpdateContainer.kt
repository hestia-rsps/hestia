package worlds.gregs.hestia.core.entity.item.container.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.action.model.EntityAction

class UpdateContainer(val id: Int) : EntityAction(), InstantEvent