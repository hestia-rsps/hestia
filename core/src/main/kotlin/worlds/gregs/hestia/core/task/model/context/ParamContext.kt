package worlds.gregs.hestia.core.task.model.context

import com.artemis.World
import worlds.gregs.hestia.core.task.api.EntityTask
import worlds.gregs.hestia.core.task.api.TaskContext

data class ParamContext<T>(override val world: World, override val entity: Int, val param: T) : TaskContext(), EntityTask {
    constructor(entityContext: EntityContext, param: T) : this(entityContext.world, entityContext.entity, param)
}
