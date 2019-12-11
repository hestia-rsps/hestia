package worlds.gregs.hestia.services

import com.artemis.*
import com.artemis.utils.IntBag
import net.mostlyoriginal.api.event.common.EventSystem
import org.apache.commons.text.WordUtils
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.api.mob.Mob
import worlds.gregs.hestia.api.player.Player
import worlds.gregs.hestia.artemis.events.OutBoundMessage
import worlds.gregs.hestia.artemis.events.OutBoundPacket
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

fun EventSystem.send(entity: Int, message: Message) {
    dispatch(OutBoundMessage(entity, message))
}

fun ArchetypeBuilder.add(vararg clazz: KClass<out Component>): ArchetypeBuilder {
    return add(*clazz.map { it.java }.toTypedArray())
}

fun EventSystem.send(entity: Int, packet: Packet) {
    dispatch(OutBoundPacket(entity, packet))
}

fun String.wrap(maxLength: Int = 45): List<String> {
    return WordUtils.wrap(this, maxLength).split(System.lineSeparator())
}

fun Int.nearby(size: Int): IntRange {
    return this - size .. this + size
}

fun IntBag.toArray(): IntArray {
    return data.copyOf(size())
}

fun IntBag.forEach(function: (Int) -> Unit) {
    repeat(size()) {
        function(data[it])
    }
}

fun IntBag.forEachIndexed(function: (index: Int, Int) -> Unit) {
    repeat(size()) {
        function(it, data[it])
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

fun com.artemis.Aspect.Builder.all(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
    return all(*clazz.map { it.java }.toTypedArray())
}

fun com.artemis.Aspect.Builder.one(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
    return one(*clazz.map { it.java }.toTypedArray())
}

fun com.artemis.Aspect.Builder.exclude(vararg clazz: KClass<out Component>): com.artemis.Aspect.Builder {
    return exclude(*clazz.map { it.java }.toTypedArray())
}

fun <T : BaseSystem>World.getSystem(type: KClass<T>) : T {
    return getSystem(type.java)
}

fun World.players(): IntBag {
    return aspectSubscriptionManager.get(Aspect.all(Player::class)).entities
}

fun World.mobs(): IntBag {
    return aspectSubscriptionManager.get(Aspect.all(Mob::class)).entities
}