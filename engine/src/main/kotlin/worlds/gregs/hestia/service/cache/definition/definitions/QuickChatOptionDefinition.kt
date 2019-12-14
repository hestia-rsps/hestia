package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class QuickChatOptionDefinition : Definition {
    var optionText: String? = null
    var navigateChars: CharArray? = null
    var quickReplyOptions: IntArray? = null
    var dynamicData: IntArray? = null
    var staticData: CharArray? = null

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> optionText = packet.readString()
            2 -> {
                val length = packet.readUnsignedByte()
                quickReplyOptions = IntArray(length)
                navigateChars = CharArray(length)
                repeat(length) { count ->
                    quickReplyOptions!![count] = packet.readShort()
                    val b = packet.readByte().toByte()
                    navigateChars!![count] = if (b.toInt() == 0) '\u0000' else byteToChar(b)
                }
            }
            3 -> {
                val length = packet.readUnsignedByte()
                staticData = CharArray(length)
                dynamicData = IntArray(length)
                repeat(length) { count ->
                    dynamicData!![count] = packet.readShort()
                    val b = packet.readByte().toByte()
                    staticData!![count] = if (b.toInt() != 0) byteToChar(b) else '\u0000'
                }
            }
        }
    }

    fun method2297(c: Char): Int {
        if (dynamicData == null) {
            return -1
        }
        var i_15_ = 0
        while (dynamicData!!.size > i_15_) {
            if (staticData!![i_15_] == c) {
                return dynamicData!![i_15_]
            }
            i_15_++
        }
        return -1
    }

    fun method2298(c: Char): Int {
        if (quickReplyOptions == null) {
            return -1
        }
        for (i in quickReplyOptions!!.indices) {
            if (navigateChars!![i] == c) {
                return quickReplyOptions!![i]
            }
        }
        return -1
    }

    fun changeValues() {
        if (dynamicData != null) {
            for (i in dynamicData!!.indices) {
                dynamicData!![i] = dynamicData!![i] or 32768
            }
        }
        if (quickReplyOptions != null) {
            repeat(quickReplyOptions!!.size) { count ->
                quickReplyOptions!![count] = quickReplyOptions!![count] or 32768
            }
        }
    }
}