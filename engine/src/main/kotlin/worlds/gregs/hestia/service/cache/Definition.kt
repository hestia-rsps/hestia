package worlds.gregs.hestia.service.cache

import world.gregs.hestia.core.network.codec.packet.Packet

interface Definition {
    fun readValueLoop(packet: Packet, member: Boolean) {
        var opcode = packet.readUnsignedByte()
        while(opcode != 0) {
            readValues(opcode, packet, member)
            opcode = packet.readUnsignedByte()
        }
    }

    fun readValues(opcode: Int, packet: Packet, member: Boolean)

    fun calculateCapacity(i: Int): Int {
        var i = i
        i--
        i = i or i ushr 1
        i = i or i ushr 2
        i = i or i ushr 4
        i = i or i ushr 8
        i = i or i ushr 16
        return 1 + i
    }

    fun skip(buffer: Packet) {
        val length = buffer.readUnsignedByte()
        repeat(length) {
            buffer.skip(1)
            val amount = buffer.readUnsignedByte()
            buffer.skip(amount * 2)
        }
    }

    fun byteToChar(b: Byte): Char {
        var i = 0xff and b.toInt()
        require(i != 0) { "Non cp1252 character 0x" + i.toString(16) + " provided" }
        if (i in 128..159) {
            var char = UNICODE_TABLE[i - 128].toInt()
            if (char == 0) {
                char = 63
            }
            i = char
        }
        return i.toChar()
    }

    companion object {

        private var UNICODE_TABLE = charArrayOf('\u20ac', '\u0000', '\u201a', '\u0192', '\u201e', '\u2026', '\u2020', '\u2021', '\u02c6', '\u2030', '\u0160', '\u2039', '\u0152', '\u0000', '\u017d', '\u0000', '\u0000', '\u2018', '\u2019', '\u201c', '\u201d', '\u2022', '\u2013', '\u2014', '\u02dc', '\u2122', '\u0161', '\u203a', '\u0153', '\u0000', '\u017e', '\u0178')
    }
}