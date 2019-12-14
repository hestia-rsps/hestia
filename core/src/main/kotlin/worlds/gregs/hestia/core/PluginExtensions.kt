package worlds.gregs.hestia.core

import com.artemis.Component
import com.artemis.EntityEdit
import com.artemis.World
import com.artemis.utils.IntBag
import worlds.gregs.hestia.core.entity.mob.api.Mob
import kotlin.reflect.KClass

/*
    Kotlin extension helpers
 */

fun World.mobs(): IntBag {
    return aspectSubscriptionManager.get(worlds.gregs.hestia.artemis.Aspect.all(Mob::class)).entities
}