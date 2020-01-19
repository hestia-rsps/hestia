package worlds.gregs.hestia.core

import com.artemis.World
import com.artemis.utils.IntBag
import worlds.gregs.hestia.core.entity.npc.api.Npc

/*
    Kotlin extension helpers
 */

fun World.npcs(): IntBag {
    return aspectSubscriptionManager.get(worlds.gregs.hestia.artemis.Aspect.all(Npc::class)).entities
}