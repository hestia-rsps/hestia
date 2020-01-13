package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition
import java.util.*

class WidgetDefinition : Definition {
    var mouseReleasedHandler: Array<Any?>? = null
    var spriteYaw: Int = 0
    var menuActions: Array<String?>? = null
    var anObjectArray4688: Array<Any?>? = null
    var hidden: Boolean = false
    var scrollHeight: Int = 0
    var parent: Int = -1
    var baseWidth: Int = 0
    var lineHeight: Int = 0
    var anInt4698: Int = -1
    var updateHandler: Array<Any?>? = null
    var keyModifiers: IntArray? = null
    var mouseEnterHandler: Array<Any?>? = null
    var aBoolean4707: Boolean = false
    var anInt4708: Int = 0
    var viewportX = 0
    var shaded: Boolean = false
    var anObjectArray4711: Array<Any?>? = null
    var horizontalPositionMode: Byte = 0
    var lineMirrored: Boolean = false
    var baseHeight: Int = 0
    var ignoreZBuffer: Boolean = false
    var imageRotation: Int = 0
    var flipVertical = false
    var keyCodes: ByteArray? = null
    var scrollWidth: Int = 0
    var imageRepeat: Boolean = false
    var verticalSizeMode: Byte = 0
    var invUpdateHandler: Array<Any?>? = null
    var flipHorizontal = false
    var rotation: Int = 0
    var horizontalSizeMode: Byte = 0
    var anObjectArray4751: Array<Any?>? = null
    var lineWidth: Int = 1
    var anObjectArray4753: Array<Any?>? = null
    var colour: Int = 0
    var alpha: Int = 0
    var anObjectArray4758: Array<Any?>? = null
    var fontId: Int = -1
    var anInt4761: Int = -1
    var aString4765: String? = null
    var lineCount: Int = 0
    var anObjectArray4768: Array<Any?>? = null
    var filled: Boolean = false
    var anObjectArray4770: Array<Any?>? = null
    var anObjectArray4771: Array<Any?>? = null
    var animation: Int = -1
    var mousePressedHandler: Array<Any?>? = null
    var anObjectArray4775: Array<Any?>? = null
    var applyText: String = ""
    var aBoolean4782: Boolean = true
    var aString4784: String? = null
    var aString4786: String = ""
    var spriteScale: Int = 100
    var anObjectArray4788: Array<Any?>? = null
    var anIntArray4789: IntArray? = null
    var text: String = ""
    var anInt4795: Int = 0
    var backgroundColour: Int = 0
    var viewportY: Int = 0
    var viewportWidth: Int = 0
    var aBoolean4802: Boolean = false
    var mouseDraggedHandler: Array<Any?>? = null
    var anIntArray4805: IntArray? = null
    var keyRepeat: ByteArray? = null
    var anObjectArray4807: Array<Any?>? = null
    var aBoolean4808: Boolean = false
    var contentType: Int = 0
    var spritePitch: Int = 0
    var basePositionY: Int = 0
    var mouseExitHandler: Array<Any?>? = null
    var defaultImage: Int = -1
    var spriteRoll: Int = 0
    var id: Int = -1
    val params: HashMap<Long, Any>? = null
    var verticalTextAlign: Int = 0
    var clientVarc: IntArray? = null
    var monochrome: Boolean = true
    var monitoredInventories: IntArray? = null
    var mouseMotionHandler: Array<Any?>? = null
    var horizontalTextAlign: Int = 0
    var clientVarp: IntArray? = null
    var anInt4839: Int = -1
    lateinit var setting: WidgetSettingDefinition
    var type = 0
    var viewportZ: Int = 0
    var defaultMediaType: Int = 1
    var viewportHeight: Int = 0
    var basePositionX: Int = 0
    var verticalPositionMode: Byte = 0
    var anObjectArray4852: Array<Any?>? = null
    var mouseDragPassHandler: Array<Any?>? = null
    var disableHover: Boolean = false
    var anInt4860: Int = 0
    var aBoolean4861: Boolean = false
    var anIntArray4863: IntArray? = null
    var defaultMediaId = 0
    var centreType = false

    private fun decodeScript(buffer: Packet): Array<Any?>? {
        val i = buffer.readUnsignedByte()
        if (i == 0) {
            return null
        }
        val objects = arrayOfNulls<Any>(i)
        var i_3_ = 0
        while (i > i_3_) {
            val i_4_ = buffer.readUnsignedByte()
            if (i_4_ == 0) {
                objects[i_3_] = buffer.readInt()
            } else if (i_4_ == 1) {
                objects[i_3_] = buffer.readString()
            }
            i_3_++
        }
        aBoolean4808 = true
        return objects
    }


    override fun readValueLoop(packet: Packet, member: Boolean) {
        var i = packet.readUnsignedByte()
        if (i == 255) {
            i = -1
        }
        type = packet.readUnsignedByte()
        if (type and 0x80 != 0) {
            type = type and 0x7f
            aString4765 = packet.readString()
        }
        contentType = packet.readShort()
        basePositionX = packet.readUnsignedShort()
        basePositionY = packet.readUnsignedShort()
        baseWidth = packet.readShort()
        baseHeight = packet.readShort()
        horizontalSizeMode = packet.readByte().toByte()
        verticalSizeMode = packet.readByte().toByte()
        horizontalPositionMode = packet.readByte().toByte()
        verticalPositionMode = packet.readByte().toByte()
        parent = packet.readShort()
        parent = if (parent == 65535) {
            -1
        } else {
            parent + (id and -65536)
        }
        val i_17_ = packet.readUnsignedByte()
        hidden = 0x1 and i_17_ != 0
        if (i >= 0) {
            disableHover = i_17_ and 0x2 != 0
        }
        if (type == 0) {
            scrollWidth = packet.readShort()
            scrollHeight = packet.readShort()
            if (i < 0) {
                disableHover = packet.readUnsignedByte() == 1
            }
        }
        if (type == 5) {
            defaultImage = packet.readInt()
            imageRotation = packet.readShort()
            val i_18_ = packet.readUnsignedByte()
            aBoolean4861 = i_18_ and 0x1 != 0
            imageRepeat = i_18_ and 0x2 != 0
            alpha = packet.readUnsignedByte()
            rotation = packet.readUnsignedByte()
            backgroundColour = packet.readInt()
            flipVertical = packet.readUnsignedByte() == 1
            flipHorizontal = packet.readUnsignedByte() == 1
            colour = packet.readInt()
            if (i >= 3) {
                aBoolean4782 = packet.readUnsignedByte() == 1
            }
        }
        if (type == 6) {
            defaultMediaType = 1
            defaultMediaId = packet.readShort()
            if (defaultMediaId == 65535) {
                defaultMediaId = -1
            }
            val i_19_ = packet.readUnsignedByte()
            aBoolean4707 = 0x4 and i_19_ == 4
            val bool = 0x1 and i_19_ == 1
            centreType = i_19_ and 0x2 == 2
            ignoreZBuffer = 0x8 and i_19_ == 8
            if (bool) {
                viewportX = packet.readUnsignedShort()
                viewportY = packet.readUnsignedShort()
                spritePitch = packet.readShort()
                spriteRoll = packet.readShort()
                spriteYaw = packet.readShort()
                spriteScale = packet.readShort()
            } else if (centreType) {
                viewportX = packet.readUnsignedShort()
                viewportY = packet.readUnsignedShort()
                viewportZ = packet.readUnsignedShort()
                spritePitch = packet.readShort()
                spriteRoll = packet.readShort()
                spriteYaw = packet.readShort()
                spriteScale = packet.readUnsignedShort()
            }
            animation = packet.readShort()
            if (animation == 65535) {
                animation = -1
            }
            if (horizontalSizeMode.toInt() != 0) {
                viewportWidth = packet.readShort()
            }
            if (verticalSizeMode.toInt() != 0) {
                viewportHeight = packet.readShort()
            }
        }
        if (type == 4) {
            fontId = packet.readShort()
            if (fontId == 65535) {
                fontId = -1
            }
            if (i >= 2) {
                monochrome = packet.readUnsignedByte() == 1
            }
            text = packet.readString()
            lineHeight = packet.readUnsignedByte()
            horizontalTextAlign = packet.readUnsignedByte()
            verticalTextAlign = packet.readUnsignedByte()
            shaded = packet.readUnsignedByte() == 1
            colour = packet.readInt()
            alpha = packet.readUnsignedByte()
            if (i >= 0) {
                lineCount = packet.readUnsignedByte()
            }
        }
        if (type == 3) {
            colour = packet.readInt()
            filled = packet.readUnsignedByte() == 1
            alpha = packet.readUnsignedByte()
        }
        if (type == 9) {
            lineWidth = packet.readUnsignedByte()
            colour = packet.readInt()
            lineMirrored = packet.readUnsignedByte() == 1
        }
        val setting = packet.readMedium()
        var i_21_ = packet.readUnsignedByte()
        if (i_21_ != 0) {
            keyRepeat = ByteArray(11)
            keyCodes = ByteArray(11)
            keyModifiers = IntArray(11)
            while ( /**/i_21_ != 0) {
                val i_22_ = (i_21_ shr 4) - 1
                i_21_ = packet.readUnsignedByte() or i_21_ shl 8
                i_21_ = i_21_ and 0xfff
                if (i_21_ == 4095) {
                    i_21_ = -1
                }
                val b_23_ = packet.readByte().toByte()
                if (b_23_.toInt() != 0) {
                    aBoolean4802 = true
                }
                val b_24_ = packet.readByte().toByte()
                keyModifiers!![i_22_] = i_21_
                keyRepeat!![i_22_] = b_23_
                keyCodes!![i_22_] = b_24_
                i_21_ = packet.readUnsignedByte()
            }
        }
        applyText = packet.readString()
        val i_25_ = packet.readUnsignedByte()
        val i_26_ = 0xf and i_25_
        if (i_26_ > 0) {
            menuActions = arrayOfNulls(i_26_)
            var i_27_ = 0
            while (i_26_ > i_27_) {
                menuActions!![i_27_] = packet.readString()
                i_27_++
            }
        }
        val i_28_ = i_25_ shr 4
        if (i_28_ > 0) {
            val i_29_ = packet.readUnsignedByte()
            anIntArray4863 = IntArray(i_29_ + 1)
            var i_30_ = 0
            while (anIntArray4863!!.size > i_30_) {
                anIntArray4863!![i_30_] = -1
                i_30_++
            }
            anIntArray4863!![i_29_] = packet.readShort()
        }
        if (i_28_ > 1) {
            val i_31_ = packet.readUnsignedByte()
            anIntArray4863!![i_31_] = packet.readShort()
        }
        aString4784 = packet.readString()
        if (aString4784 == "") {
            aString4784 = null
        }
        anInt4708 = packet.readUnsignedByte()
        anInt4795 = packet.readUnsignedByte()
        anInt4860 = packet.readUnsignedByte()
        aString4786 = packet.readString()
        var i_32_ = -1
        if (setting and 0x3fda8 shr 11 != 0) {
            i_32_ = packet.readShort()
            if (i_32_ == 65535) {
                i_32_ = -1
            }
            anInt4698 = packet.readShort()
            if (anInt4698 == 65535) {
                anInt4698 = -1
            }
            anInt4839 = packet.readShort()
            if (anInt4839 == 65535) {
                anInt4839 = -1
            }
        }
        if (i >= 0) {
            anInt4761 = packet.readShort()
            if (anInt4761 == 65535) {
                anInt4761 = -1
            }
        }
        this.setting = WidgetSettingDefinition(setting, i_32_)
        if (i >= 0) {
            val i_33_ = packet.readUnsignedByte()
            var i_34_ = 0
            while (i_33_ > i_34_) {
                val i_35_ = packet.readMedium()
                val i_36_ = packet.readInt()
                params!![i_35_.toLong()] = i_36_
                i_34_++
            }
            val i_37_ = packet.readUnsignedByte()
            for (i_38_ in 0 until i_37_) {
                val i_39_ = packet.readMedium()
                val string = packet.readString()
                params!![i_39_.toLong()] = string
            }
        }
        anObjectArray4758 = decodeScript(packet)
        mouseEnterHandler = decodeScript(packet)
        mouseExitHandler = decodeScript(packet)
        anObjectArray4771 = decodeScript(packet)
        anObjectArray4768 = decodeScript(packet)
        anObjectArray4807 = decodeScript(packet)//State/value change
        invUpdateHandler = decodeScript(packet)
        anObjectArray4788 = decodeScript(packet)//Update/refresh
        updateHandler = decodeScript(packet)
        anObjectArray4770 = decodeScript(packet)
        if (i >= 0) {
            anObjectArray4751 = decodeScript(packet)
        }
        mouseMotionHandler = decodeScript(packet)
        mousePressedHandler = decodeScript(packet)
        mouseDraggedHandler = decodeScript(packet)
        mouseReleasedHandler = decodeScript(packet)
        mouseDragPassHandler = decodeScript(packet)
        anObjectArray4852 = decodeScript(packet)
        anObjectArray4711 = decodeScript(packet)
        anObjectArray4753 = decodeScript(packet)
        anObjectArray4688 = decodeScript(packet)
        anObjectArray4775 = decodeScript(packet)
        clientVarp = method4150(107.toByte(), packet)
        monitoredInventories = method4150(101.toByte(), packet)
        anIntArray4789 = method4150(98.toByte(), packet)
        clientVarc = method4150(126.toByte(), packet)
        anIntArray4805 = method4150(117.toByte(), packet)
    }

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
    }

    private fun method4150(b: Byte, buffer: Packet): IntArray? {
        val i = buffer.readUnsignedByte()
        if (i == 0) {
            return null
        }
        val `is` = IntArray(i)
        if (b < 93) {
            return null
        }
        for (i_60_ in 0 until i) `is`[i_60_] = buffer.readInt()
        return `is`
    }
}