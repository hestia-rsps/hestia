package worlds.gregs.hestia.core.entity.entity.model.events

import worlds.gregs.hestia.core.action.model.EntityAction

class Hit(val amount: Int, val mark: Int, val delay: Int = 0, var critical: Boolean = false, val source: Int = -1, val soak: Int = -1) : EntityAction()