package worlds.gregs.hestia.core.task.api

import com.artemis.World

interface EntityTask {
    val entity: Int
    val world: World
}

val Task.entity: Int
    get() = (context as EntityTask).entity

val Task.world: World
    get() = (context as EntityTask).world