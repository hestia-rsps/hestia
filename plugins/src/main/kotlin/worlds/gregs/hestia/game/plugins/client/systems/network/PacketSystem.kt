package worlds.gregs.hestia.game.plugins.client.systems.network

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.packets.PacketInfo
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.game.PacketHandlerSystem
import worlds.gregs.hestia.game.events.ClientPacket
import kotlin.system.measureNanoTime

/**
 * PacketSystem
 * Handles all inbound packets
 */
class PacketSystem : PassiveSystem() {

    private val queues = ArrayList<Int>()
    private val logger = LoggerFactory.getLogger(PacketSystem::class.java)

    override fun initialize() {
        super.initialize()

        val time = measureNanoTime {
            for(i in 0 until world.systems.size()) {
                val system = world.systems.get(i)
                if(system is PacketHandlerSystem && system::class.java.isAnnotationPresent(PacketInfo::class.java)) {
                    val annotation = system::class.java.getAnnotation(PacketInfo::class.java)
                    annotation.opcodes.forEach { opcode ->
                        PacketHandlerSystem.gamePackets[opcode] = Pair(system, annotation.size)
                    }
                }
            }
        }
        logger.debug("${PacketHandlerSystem.gamePackets.size} ${"packet".plural(PacketHandlerSystem.gamePackets.size)} loaded in ${time / 1000000}ms")
    }

    @Subscribe
    private fun event(event: ClientPacket) {
        val opcode = event.packet.opcode
        if(PacketHandlerSystem.gamePackets.containsKey(opcode)) {
            if(queues.contains(opcode)) {
                //queue[systems[opcode]] = event
            } else {
                PacketHandlerSystem.gamePackets.getPacket(opcode)?.handle(event.entityId, event.packet)
            }
        }
    }

}