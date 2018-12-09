package worlds.gregs.hestia.game.plugins.client.systems.network.`in`

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import worlds.gregs.hestia.game.PacketHandler
import worlds.gregs.hestia.network.login.Packets

@PacketSize(8)
@PacketOpcode(Packets.INTERFACE_BTN_1, Packets.INTERFACE_BTN_2, Packets.INTERFACE_BTN_3, Packets.INTERFACE_BTN_4, Packets.INTERFACE_BTN_5, Packets.INTERFACE_BTN_6, Packets.INTERFACE_BTN_7, Packets.INTERFACE_BTN_8, Packets.INTERFACE_BTN_9, Packets.INTERFACE_BTN_10)
class InterfaceHandler : PacketHandler() {
    override fun handle(entityId: Int, packet: Packet, length: Int) {
        val interfaceHash = packet.readInt2()
        val interfaceId = interfaceHash shr 16
        /*if (Utils.interfaceDefinitionsSize <= interfaceId) {
            return false
        }*/
        /*if (player.isDead || !player.interfaceManager!!.containsInterface(interfaceId)) {
            return null
        }*/
        val componentId = interfaceHash - (interfaceId shl 16)
        /*if (componentId != 65535 && Utils.getInterfaceDefinitionsComponentsSize(interfaceId) <= componentId) {
            return false
        }*/
        val slotId2 = packet.readLEShortA()
        val slotId = packet.readShort()

        val option =  when (packet.opcode) {
            Packets.INTERFACE_BTN_1 -> 1
            Packets.INTERFACE_BTN_2 -> 2
            Packets.INTERFACE_BTN_3 -> 3
            Packets.INTERFACE_BTN_4 -> 4
            Packets.INTERFACE_BTN_5 -> 5
            Packets.INTERFACE_BTN_6 -> 6
            Packets.INTERFACE_BTN_7 -> 7
            Packets.INTERFACE_BTN_8 -> 8
            Packets.INTERFACE_BTN_9 -> 9
            Packets.INTERFACE_BTN_10 -> 10
            else -> 0
        }
        println("Clicked $interfaceId $componentId $slotId")
        when(interfaceId) {
            746 -> {
                when(componentId) {
                    176 -> {
                        world.delete(entityId)
                    }
                }
            }
            548 -> {
                when(componentId) {
                    182 -> {
                        world.delete(entityId)
                    }
                }
            }
        }
    }

}