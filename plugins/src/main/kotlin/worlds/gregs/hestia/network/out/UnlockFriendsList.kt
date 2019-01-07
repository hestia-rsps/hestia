package worlds.gregs.hestia.network.out

import world.gregs.hestia.core.network.packets.Packet

class UnlockFriendsList : Packet.Builder(85, Packet.Type.VAR_SHORT)