package world.gregs.hestia.network.`in`

import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.update.direction.face
import world.gregs.hestia.game.component.update.direction.turn
import world.gregs.hestia.game.component.update.direction.watch
import world.gregs.hestia.game.component.movement.*
import world.gregs.hestia.game.events.*
import world.gregs.hestia.network.login.Packets
import world.gregs.hestia.services.getComponent
import world.gregs.hestia.services.mobs
import world.gregs.hestia.services.players
import world.gregs.hestia.core.network.Session
import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.network.packets.PacketOpcode
import world.gregs.hestia.core.network.packets.PacketSize
import world.gregs.hestia.game.component.update.*
import world.gregs.hestia.game.update.Marker
import world.gregs.hestia.network.game.GamePacket

@PacketSize(8)
@PacketOpcode(Packets.INTERFACE_BTN_1, Packets.INTERFACE_BTN_2, Packets.INTERFACE_BTN_3, Packets.INTERFACE_BTN_4, Packets.INTERFACE_BTN_5, Packets.INTERFACE_BTN_6, Packets.INTERFACE_BTN_7, Packets.INTERFACE_BTN_8, Packets.INTERFACE_BTN_9, Packets.INTERFACE_BTN_10)
class InterfaceClick : GamePacket() {
    override fun read(session: Session, packet: Packet, length: Int): Boolean {
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
                        if(entity != null) {
                            entity!!.world.delete(entity!!.id)
                        }
                    }
                }
            }
            548 -> {
                when(componentId) {
                    182 -> {
                        if(entity != null) {
                            entity!!.world.delete(entity!!.id)
                        }
                    }
                }
            }
        }
        return true
    }

}