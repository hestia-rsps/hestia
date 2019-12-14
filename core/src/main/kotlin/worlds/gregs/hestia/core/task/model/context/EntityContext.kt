package worlds.gregs.hestia.core.task.model.context

import com.artemis.World
import worlds.gregs.hestia.core.task.api.EntityTask
import worlds.gregs.hestia.core.task.api.TaskContext

data class EntityContext(override val world: World, override val entity: Int) : TaskContext(), EntityTask