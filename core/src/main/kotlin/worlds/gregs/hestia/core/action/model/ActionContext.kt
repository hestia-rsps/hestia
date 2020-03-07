package worlds.gregs.hestia.core.action.model

import com.artemis.Component
import com.artemis.World

class ActionContext : Component(), EntityActions {
    override var entity: Int = -1
    override lateinit var world: World
}