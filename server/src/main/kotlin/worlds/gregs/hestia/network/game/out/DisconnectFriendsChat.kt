package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet

class DisconnectFriendsChat : Packet.Builder(12, Packet.Type.VAR_SHORT)