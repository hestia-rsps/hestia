package worlds.gregs.hestia.network.game.out

import world.gregs.hestia.core.network.packets.Packet

class RunScript(id: Int, vararg params: Any) : Packet.Builder(50, Packet.Type.VAR_SHORT) {
    init {
        var parameterTypes = ""
        for (count in params.indices.reversed()) {
            parameterTypes += if (params[count] is String) {
                "s" // string
            } else {
                "i" // integer
            }
        }
        writeString(parameterTypes)
        var index = 0
        for (count in parameterTypes.length - 1 downTo 0) {
            if (parameterTypes[count] == 's') {
                writeString(params[index++] as String)
            } else {
                writeInt(params[index++] as Int)
            }
        }
        writeInt(id)
    }
}