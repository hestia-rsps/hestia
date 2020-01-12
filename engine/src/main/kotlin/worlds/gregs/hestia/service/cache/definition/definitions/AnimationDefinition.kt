package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class AnimationDefinition : Definition {
    var anIntArray690: IntArray? = null
    var aBoolean691 = false
    var anIntArray692: IntArray? = null
    var loopOffset = -1
    var animatingPrecedence: Int = -1
    var aBoolean699: Boolean = false
    var anIntArrayArray700: Array<IntArray?>? = null
    var anIntArray701: IntArray? = null
    var interleaveOrder: BooleanArray? = null
    var replayMode = 2
    var durations: IntArray? = null
    var maxLoops: Int = 99
    var priority: Int = 5
    var id = 0
    var secondaryFrames: IntArray? = null
    var rightHand: Int = -1
    var tweened: Boolean = false
    var primaryFrames: IntArray? = null
    var leftHand: Int = -1
    var walkingPrecedence: Int = -1

    fun getTime() = (durations?.sum() ?: 0) * 10

    fun getClientCycles(): Int {
        if (durations == null) {
            return 0
        }
        var total = 0
        for (i in 0 until durations!!.size - 3) {
            total += durations!![i]
        }
        return total
    }

    fun changeValues() {
        if (walkingPrecedence == -1) {
            walkingPrecedence = if (interleaveOrder == null) {
                0
            } else {
                2
            }
        }
        if (animatingPrecedence == -1) {
            animatingPrecedence = if (interleaveOrder == null) {
                0
            } else {
                2
            }
        }
    }

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when(opcode) {
            1 -> {
                val length = packet.readShort()
                durations = IntArray(length)
                repeat(length) { count ->
                    durations!![count] = packet.readShort()
                }
                primaryFrames = IntArray(length)
                repeat(length) { count ->
                    primaryFrames!![count] = packet.readShort()
                }
                repeat(length) { count ->
                    primaryFrames!![count] = (packet.readShort() shl 16) + primaryFrames!![count]
                }
            }
            2 -> loopOffset = packet.readShort()
            3 -> {
                interleaveOrder = BooleanArray(256)
                val length = packet.readUnsignedByte()
                repeat(length) {
                    interleaveOrder!![packet.readUnsignedByte()] = true
                }
            }
            5 -> priority = packet.readUnsignedByte()
            6 -> leftHand = packet.readShort()
            7 -> rightHand = packet.readShort()
            8 -> maxLoops = packet.readUnsignedByte()
            9 -> animatingPrecedence = packet.readUnsignedByte()
            10 -> walkingPrecedence = packet.readUnsignedByte()
            11 -> replayMode = packet.readUnsignedByte()
            12 -> {
                val length = packet.readUnsignedByte()
                secondaryFrames = IntArray(length)
                repeat(length) { count ->
                    secondaryFrames!![count] = packet.readShort()
                }
                repeat(length) { count ->
                    secondaryFrames!![count] = (packet.readShort() shl 16) + secondaryFrames!![count]
                }
            }
            13 -> {
                val length = packet.readShort()
                anIntArrayArray700 = arrayOfNulls(length)
                repeat(length) { count ->
                    val size = packet.readUnsignedByte()
                    if (size > 0) {
                        anIntArrayArray700!![count] = IntArray(size)
                        anIntArrayArray700!![count]!![0] = packet.readMedium()
                        for(index in 1 until size) {
                            anIntArrayArray700!![count]!![index] = packet.readShort()
                        }
                    }
                }
            }
            14 -> aBoolean691 = true
            15 -> tweened = true
            18 -> aBoolean699 = true
            19 -> {
                if (anIntArray701 == null) {
                    anIntArray701 = IntArray(anIntArrayArray700!!.size)
                    for (index in anIntArrayArray700!!.indices) {
                        anIntArray701!![index] = 255
                    }
                }
                anIntArray701!![packet.readUnsignedByte()] = packet.readUnsignedByte()
            }
            20 -> {
                if (anIntArray690 == null || anIntArray692 == null) {
                    anIntArray690 = IntArray(anIntArrayArray700!!.size)
                    anIntArray692 = IntArray(anIntArrayArray700!!.size)
                    for (index in anIntArrayArray700!!.indices) {
                        anIntArray690!![index] = 256
                        anIntArray692!![index] = 256
                    }
                }
                val length = packet.readUnsignedByte()
                anIntArray690!![length] = packet.readShort()
                anIntArray692!![length] = packet.readShort()
            }
        }
    }
}