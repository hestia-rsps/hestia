package world.gregs.hestia.services

import com.artemis.ArtemisPlugin
import com.artemis.BaseSystem
import com.artemis.World
import com.artemis.WorldConfigurationBuilder
import com.artemis.utils.IntBag
import world.gregs.hestia.game.events.OutBoundPacket
import net.mostlyoriginal.api.event.common.EventSystem
import world.gregs.hestia.core.network.packets.Packet
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

fun IntBag.toArray(): IntArray {
    return Arrays.copyOf(data, size())
}

fun <T : BaseSystem>World.getSystem(type: KClass<T>) : T {
    return getSystem(type.java)
}