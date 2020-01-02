package worlds.gregs.hestia.service.cache.config.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition

class RenderAnimationDefinition : Definition {
    var anIntArrayArray3249: Array<IntArray?>? = null
    var anInt3250: Int = 0
    var run: Int = -1
    var anInt3253: Int = -1
    var anIntArray3255: IntArray? = null
    var anInt3256: Int = -1
    var anInt3258 = 0
    var primaryIdle: Int = -1
    var anInt3260: Int = -1
    var anInt3261 = 0
    var anInt3262: Int = -1
    var anInt3263: Int = 0
    var anInt3266: Int = 0
    var aBoolean3267: Boolean = true
    var anInt3269: Int = -1
    var anInt3270: Int = -1
    var anInt3271 = -1
    var anInt3272: Int = 0
    var anIntArrayArray3273: Array<IntArray?>? = null
    var secondaryWalk: Int = -1
    var anInt3275: Int = -1
    var anIntArray3276: IntArray? = null
    var primaryWalk: Int = -1
    var anInt3278 = 0
    var anInt3281: Int = 0
    var anInt3282: Int = -1
    var anInt3283: Int = 0
    var anInt3284: Int = 0
    var anInt3285: Int = 0
    var walkBackwards: Int = -1
    var sideStepRight: Int = -1
    var anInt3289: Int = 0
    var anInt3290: Int = -1
    var anInt3291: Int = 0
    var anInt3292: Int = -1
    var anInt3293: Int = -1
    var anIntArray3294: IntArray? = null
    var anInt3297: Int = -1
    var anInt3298: Int = -1
    var turning: Int = -1
    var sideStepLeft: Int = -1
    var anIntArray3302: IntArray? = null
    var anInt3303: Int = -1
    var anInt3304: Int = -1
    var anInt3305: Int = -1

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> {
                primaryIdle = packet.readShort()
                primaryWalk = packet.readShort()
                if (primaryIdle == 65535) {
                    primaryIdle = -1
                }
                if (primaryWalk == 65535) {
                    primaryWalk = -1
                }
            }
            2 -> anInt3262 = packet.readShort()
            3 -> anInt3297 = packet.readShort()
            4 -> anInt3269 = packet.readShort()
            5 -> anInt3304 = packet.readShort()
            6 -> run = packet.readShort()
            7 -> anInt3271 = packet.readShort()
            8 -> anInt3270 = packet.readShort()
            9 -> anInt3293 = packet.readShort()
            26 -> {
                anInt3261 = (4 * packet.readUnsignedByte()).toShort().toInt()
                anInt3266 = (4 * packet.readUnsignedByte()).toShort().toInt()
            }
            27 -> {
                if (anIntArrayArray3273 == null) {
                    anIntArrayArray3273 = arrayOfNulls(defaultsSize)
                }
                val length = packet.readUnsignedByte()
                anIntArrayArray3273!![length] = IntArray(6)
                repeat(5) { index ->
                    anIntArrayArray3273!![length]!![index] = packet.readUnsignedShort()
                }
            }
            28 -> {
                val length = packet.readUnsignedByte()
                anIntArray3276 = IntArray(length)
                repeat(length) { count ->
                    anIntArray3276!![count] = packet.readUnsignedByte()
                    if (anIntArray3276!![count] == 255) {
                        anIntArray3276!![count] = -1
                    }
                }
            }
            29 -> anInt3258 = packet.readUnsignedByte()
            30 -> anInt3283 = packet.readShort()
            31 -> anInt3278 = packet.readUnsignedByte()
            32 -> anInt3284 = packet.readShort()
            33 -> anInt3250 = packet.readUnsignedShort()
            34 -> anInt3272 = packet.readUnsignedByte()
            35 -> anInt3289 = packet.readShort()
            36 -> anInt3285 = packet.readUnsignedShort()
            37 -> anInt3256 = packet.readUnsignedByte()
            38 -> turning = packet.readShort()
            39 -> secondaryWalk = packet.readShort()
            40 -> walkBackwards = packet.readShort()
            41 -> sideStepLeft = packet.readShort()
            42 -> sideStepRight = packet.readShort()
            43 -> anInt3290 = packet.readShort()
            44 -> anInt3292 = packet.readShort()
            45 -> anInt3303 = packet.readShort()
            46 -> anInt3275 = packet.readShort()
            47 -> anInt3260 = packet.readShort()
            48 -> anInt3282 = packet.readShort()
            49 -> anInt3253 = packet.readShort()
            50 -> anInt3298 = packet.readShort()
            51 -> anInt3305 = packet.readShort()
            52 -> {
                val length = packet.readUnsignedByte()
                anIntArray3294 = IntArray(length)
                anIntArray3302 = IntArray(length)
                repeat(length) { index ->
                    anIntArray3294!![index] = packet.readShort()
                    val value = packet.readUnsignedByte()
                    anIntArray3302!![index] = value
                    anInt3281 += value
                }
            }
            53 -> aBoolean3267 = false
            54 -> {
                anInt3263 = packet.readUnsignedByte() shl 6
                anInt3291 = packet.readUnsignedByte() shl 6
            }
            55 -> {
                if (anIntArray3255 == null) {
                    anIntArray3255 = IntArray(defaultsSize)
                }
                val index = packet.readUnsignedByte()
                anIntArray3255!![index] = packet.readShort()
            }
            56 -> {
                if (anIntArrayArray3249 == null) {
                    anIntArrayArray3249 = arrayOfNulls(defaultsSize)
                }
                val length = packet.readUnsignedByte()
                anIntArrayArray3249!![length] = IntArray(3)
                repeat(2) { index ->
                    anIntArrayArray3249!![length]!![index] = packet.readUnsignedShort()
                }
            }
        }
    }
    companion object {
        const val defaultsSize = 15//aClass281_3265.aClass363_3578.anIntArray4508.length
    }
}