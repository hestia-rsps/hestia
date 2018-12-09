package worlds.gregs.hestia.services

import com.artemis.*
import com.artemis.utils.IntBag
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.packets.Packet
import worlds.gregs.hestia.api.mob.Mob
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.game.events.OutBoundPacket
import java.util.*
import kotlin.reflect.KClass

/*
    Kotlin extension helpers
 */

/*
    Artemis
 */
fun WorldConfigurationBuilder.dependsOn(vararg clazz: KClass<out ArtemisPlugin>): WorldConfigurationBuilder {
    return dependsOn(*clazz.map { it.java }.toTypedArray())
}

fun EventSystem.send(entity: Int, builder: Packet.Builder) {
    send(entity, builder.build())
}


fun EventSystem.send(entity: Int, packet: Packet) {
    dispatch(OutBoundPacket(entity, packet))
}

fun Int.nearby(size: Int): IntRange {
    return this - size .. this + size
}

fun IntBag.toArray(): IntArray {
    return Arrays.copyOf(data, size())
}

fun IntBag.forEach(function: (Int) -> Unit) {
    for(i in 0 until size()) {
        function(get(i))
    }
}

class Aspect {
    companion object {
        fun all(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
            return com.artemis.Aspect.all(*clazz.map { it.java }.toTypedArray())
        }

        fun one(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
            return com.artemis.Aspect.one(*clazz.map { it.java }.toTypedArray())
        }

        fun exclude(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
            return com.artemis.Aspect.exclude(*clazz.map { it.java }.toTypedArray())
        }
    }
}

fun <T : BaseSystem>World.getSystem(type: KClass<T>) : T {
    return getSystem(type.java)
}

fun World.players(): IntArray {
    return aspectSubscriptionManager.get(Aspect.all(Player::class)).entities.toArray()
}

fun World.mobs(): IntArray {
    return aspectSubscriptionManager.get(Aspect.all(Mob::class)).entities.toArray()
}