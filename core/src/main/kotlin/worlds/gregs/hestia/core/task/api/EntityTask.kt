package worlds.gregs.hestia.core.task.api

import com.artemis.World

interface EntityTask {
    val entity: Int
    val world: World
}