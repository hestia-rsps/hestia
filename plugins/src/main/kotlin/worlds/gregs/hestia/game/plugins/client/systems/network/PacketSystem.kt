package worlds.gregs.hestia.game.plugins.client.systems.network

import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import world.gregs.hestia.core.services.plural
import worlds.gregs.hestia.game.PacketHandler
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
                if(system is PacketHandler && system::class.java.isAnnotationPresent(PacketOpcode::class.java)) {
                    val opcodes = system::class.java.getAnnotation(PacketOpcode::class.java).opcodes
                    val size = system::class.java.getAnnotation(PacketSize::class.java).size
                    opcodes.forEach { opcode ->
                        PacketHandler.gamePackets[opcode] = Pair(system, size)
                    }
                }
            }
        }
        logger.debug("${PacketHandler.gamePackets.size} ${"packet".plural(PacketHandler.gamePackets.size)} loaded in ${time / 1000000}ms")
    }

    @Subscribe
    private fun event(event: ClientPacket) {
        val opcode = event.packet.opcode
        if(PacketHandler.gamePackets.containsKey(opcode)) {
            if(queues.contains(opcode)) {
                //queue[systems[opcode]] = event
            } else {
                PacketHandler.gamePackets.getPacket(opcode)?.handle(event.entityId, event.packet, event.length)
            }
        }
    }

}