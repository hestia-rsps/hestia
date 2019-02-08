package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet

class Logout(lobby: Boolean) : Packet.Builder(if(lobby) 59 else 51)