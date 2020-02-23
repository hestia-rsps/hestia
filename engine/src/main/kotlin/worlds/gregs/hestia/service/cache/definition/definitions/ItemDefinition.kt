package worlds.gregs.hestia.service.cache.definition.definitions

import world.gregs.hestia.core.network.codec.packet.Packet
import worlds.gregs.hestia.service.cache.Definition
import java.util.*

class ItemDefinition : Definition {
    var secondaryCursorOpcode = -1
    var originalTextureColours: ShortArray? = null
    var secondaryCursor = -1
    var lendTemplateId = -1
    var primaryMaleModel = -1
    var originalColours: ShortArray? = null
    var notedTemplateId = -1
    var primaryInterfaceCursor = -1
    var name = "null"
    var secondaryInterfaceCursor = -1
    var primaryFemaleDialogueHead = -1
    var secondaryInterfaceCursorOpcode = -1
    var modifiedColours: ShortArray? = null
    var spriteCameraYaw = 0
    var tertiaryMaleModel = -1
    var spriteCameraRoll = 0
    var members = false
    var stackAmounts: IntArray? = null
    var params: HashMap<Long, Any>? = null
    var spriteScale = 2000
    var spriteTranslateY = 0
    var ambience = 0
    var dummyItem = 0
    var secondaryMaleModel = -1
    var multiStackSize = -1
    var primaryFemaleModel = -1
    var recolourPalette: ByteArray? = null
    var secondaryFemaleModel = -1
    var spritePitch = 0
    var diffusion = 0
    var noteId = -1
    var secondaryMaleDialogueHead = -1
    var primaryCursor = -1
    var femaleWieldX = 0
    var bindId = -1
    var stackable = 0
    var femaleWieldY = 0
    var maleWieldZ = 0
    var groundScaleZ = 128
    var secondaryFemaleDialogueHead = -1
    var modelId = 0
    var team = 0
    var primaryInterfaceCursorOpcode = -1
    lateinit var options: Array<String?>
    var groundScaleX = 128
    var spriteTranslateX = 0
    var primaryCursorOpcode = -1
    var modifiedTextureColours: ShortArray? = null
    var groundScaleY = 128
    var primaryMaleDialogueHead = -1
    var id = 0
    var tertiaryFemaleModel = -1
    var femaleWieldZ = 0
    var stackIds: IntArray? = null
    var pickSizeShift = 0
    lateinit var floorOptions: Array<String?>
    var cost = 1
    var campaigns: IntArray? = null
    var lendId = -1
    var bindTemplateId = -1
    var maleWieldY = 0
    var maleWieldX = 0
    var unnoted = false
    var equipSlot = -1
    var equipType = -1
    var equipId = -1

    override fun readValues(opcode: Int, packet: Packet, member: Boolean) {
        when (opcode) {
            1 -> modelId = if(sevenEighteen) packet.readBigSmart() else packet.readShort()
            2 -> name = packet.readString()
            4 -> spriteScale = packet.readShort()
            5 -> spritePitch = packet.readShort()
            6 -> spriteCameraRoll = packet.readShort()
            7 -> {
                spriteTranslateX = packet.readShort()
                if (spriteTranslateX > 32767) {
                    spriteTranslateX -= 65536
                }
            }
            8 -> {
                spriteTranslateY = packet.readShort()
                if (spriteTranslateY > 32767) {
                    spriteTranslateY -= 65536
                }
            }
            11 -> stackable = 1
            12 -> cost = packet.readInt()
            13 -> equipSlot = packet.readUnsignedByte()//718 only
            14 -> equipType = packet.readUnsignedByte()//718 only
            16 -> members = true
            18 -> multiStackSize = packet.readShort()
            23 -> primaryMaleModel = if(sevenEighteen) packet.readBigSmart() else packet.readUnsignedShort()
            24 -> secondaryMaleModel = if(sevenEighteen) packet.readBigSmart() else packet.readUnsignedShort()
            25 -> primaryFemaleModel = if(sevenEighteen) packet.readBigSmart() else packet.readUnsignedShort()
            26 -> secondaryFemaleModel = if(sevenEighteen) packet.readBigSmart() else packet.readUnsignedShort()
            in 30..34 -> floorOptions[opcode - 30] = packet.readString()
            in 35..39 -> options[opcode - 35] = packet.readString()
            40 -> {
                val length = packet.readUnsignedByte()
                originalColours = ShortArray(length)
                modifiedColours = ShortArray(length)
                repeat(length) { count ->
                    originalColours!![count] = packet.readShort().toShort()
                    modifiedColours!![count] = packet.readShort().toShort()
                }
            }
            41 -> {
                val length = packet.readUnsignedByte()
                originalTextureColours = ShortArray(length)
                modifiedTextureColours = ShortArray(length)
                repeat (length) { count ->
                    originalTextureColours!![count] = packet.readShort().toShort()
                    modifiedTextureColours!![count] = packet.readShort().toShort()
                }
            }
            42 -> {
                val length = packet.readUnsignedByte()
                recolourPalette = ByteArray(length)
                repeat(length) { count ->
                    recolourPalette!![count] = packet.readByte().toByte()
                }
            }
            65 -> unnoted = true
            78 -> tertiaryMaleModel = if(sevenEighteen) packet.readBigSmart() else packet.readShort()
            79 -> tertiaryFemaleModel = if(sevenEighteen) packet.readBigSmart() else packet.readShort()
            90 -> primaryMaleDialogueHead = if(sevenEighteen) packet.readBigSmart() else packet.readShort()
            91 -> primaryFemaleDialogueHead = if(sevenEighteen) packet.readBigSmart() else packet.readShort()
            92 -> secondaryMaleDialogueHead = if(sevenEighteen) packet.readBigSmart() else packet.readShort()
            93 -> secondaryFemaleDialogueHead = if(sevenEighteen) packet.readBigSmart() else packet.readShort()
            95 -> spriteCameraYaw = packet.readShort()
            96 -> dummyItem = packet.readUnsignedByte()
            97 -> noteId = packet.readShort()
            98 -> notedTemplateId = packet.readShort()
            in 100..109 -> {
                if (stackIds == null) {
                    stackAmounts = IntArray(10)
                    stackIds = IntArray(10)
                }
                stackIds!![opcode - 100] = packet.readShort()
                stackAmounts!![opcode - 100] = packet.readShort()
            }
            110 -> groundScaleX = packet.readShort()
            111 -> groundScaleY = packet.readShort()
            112 -> groundScaleZ = packet.readShort()
            113 -> ambience = packet.readByte()
            114 -> diffusion = packet.readByte() * 5
            115 -> team = packet.readUnsignedByte()
            121 -> lendId = packet.readShort()
            122 -> lendTemplateId = packet.readShort()
            125 -> {
                maleWieldX = packet.readByte() shl 2
                maleWieldY = packet.readByte() shl 2
                maleWieldZ = packet.readByte() shl 2
            }
            126 -> {
                femaleWieldX = packet.readByte() shl 2
                femaleWieldY = packet.readByte() shl 2
                femaleWieldZ = packet.readByte() shl 2
            }
            127 -> {
                primaryCursorOpcode = packet.readUnsignedByte()
                primaryCursor = packet.readShort()
            }
            128 -> {
                secondaryCursorOpcode = packet.readUnsignedByte()
                secondaryCursor = packet.readShort()
            }
            129 -> {
                primaryInterfaceCursorOpcode = packet.readUnsignedByte()
                primaryInterfaceCursor = packet.readShort()
            }
            130 -> {
                secondaryInterfaceCursorOpcode = packet.readUnsignedByte()
                secondaryInterfaceCursor = packet.readShort()
            }
            132 -> {
                val length = packet.readUnsignedByte()
                campaigns = IntArray(length)
                repeat(length) { count ->
                    campaigns!![count] = packet.readShort()
                }
            }
            134 -> pickSizeShift = packet.readUnsignedByte()
            139 -> bindId = packet.readShort()
            140 -> bindTemplateId = packet.readShort()
            249 -> {
                val length = packet.readUnsignedByte()
                if (params == null) {
                    val initialCapacity = calculateCapacity(length)
                    params = HashMap(initialCapacity)
                }
                repeat(length) {
                    val isString = packet.readUnsignedBoolean()
                    val id = packet.readMedium()
                    params!![id.toLong()] = if (isString) {
                        packet.readString()
                    } else {
                        packet.readInt()
                    }
                }
            }
        }
    }

    fun getEquipOption(optionId: Int): String? {
        return params?.getOrDefault(528L + optionId, null) as? String
    }

    fun toLend(itemdefinition_5_: ItemDefinition, itemdefinition_6_: ItemDefinition) {
        modifiedColours = itemdefinition_5_.modifiedColours
        primaryMaleDialogueHead = itemdefinition_5_.primaryMaleDialogueHead
        secondaryMaleDialogueHead = itemdefinition_5_.secondaryMaleDialogueHead
        tertiaryMaleModel = itemdefinition_5_.tertiaryMaleModel
        team = itemdefinition_5_.team
        params = itemdefinition_5_.params
        members = itemdefinition_5_.members
        modifiedTextureColours = itemdefinition_5_.modifiedTextureColours
        maleWieldY = itemdefinition_5_.maleWieldY
        secondaryFemaleModel = itemdefinition_5_.secondaryFemaleModel
        spriteCameraYaw = itemdefinition_6_.spriteCameraYaw
        floorOptions = itemdefinition_5_.floorOptions
        secondaryFemaleDialogueHead = itemdefinition_5_.secondaryFemaleDialogueHead
        recolourPalette = itemdefinition_5_.recolourPalette
        femaleWieldY = itemdefinition_5_.femaleWieldY
        spritePitch = itemdefinition_6_.spritePitch
        primaryFemaleModel = itemdefinition_5_.primaryFemaleModel
        modelId = itemdefinition_6_.modelId
        options = arrayOfNulls(5)
        spriteCameraRoll = itemdefinition_6_.spriteCameraRoll
        spriteTranslateY = itemdefinition_6_.spriteTranslateY
        originalTextureColours = itemdefinition_5_.originalTextureColours
        femaleWieldX = itemdefinition_5_.femaleWieldX
        secondaryMaleModel = itemdefinition_5_.secondaryMaleModel
        cost = 0
        maleWieldZ = itemdefinition_5_.maleWieldZ
        originalColours = itemdefinition_5_.originalColours
        spriteTranslateX = itemdefinition_6_.spriteTranslateX
        femaleWieldZ = itemdefinition_5_.femaleWieldZ
        primaryFemaleDialogueHead = itemdefinition_5_.primaryFemaleDialogueHead
        spriteScale = itemdefinition_6_.spriteScale
        name = itemdefinition_5_.name
        tertiaryFemaleModel = itemdefinition_5_.tertiaryFemaleModel
        primaryMaleModel = itemdefinition_5_.primaryMaleModel
        maleWieldX = itemdefinition_5_.maleWieldX
        System.arraycopy(itemdefinition_5_.options, 0, options, 0, 4)
        options[4] = "Discard"
    }

    fun toNote(itemdefinition_7_: ItemDefinition, itemdefinition_8_: ItemDefinition) {
        spriteTranslateY = itemdefinition_7_.spriteTranslateY
        originalColours = itemdefinition_7_.originalColours
        cost = itemdefinition_8_.cost
        name = itemdefinition_8_.name
        modifiedTextureColours = itemdefinition_7_.modifiedTextureColours
        spriteCameraRoll = itemdefinition_7_.spriteCameraRoll
        spriteCameraYaw = itemdefinition_7_.spriteCameraYaw
        originalTextureColours = itemdefinition_7_.originalTextureColours
        modelId = itemdefinition_7_.modelId
        spriteScale = itemdefinition_7_.spriteScale
        recolourPalette = itemdefinition_7_.recolourPalette
        stackable = 1
        spritePitch = itemdefinition_7_.spritePitch
        spriteTranslateX = itemdefinition_7_.spriteTranslateX
        members = itemdefinition_8_.members
        modifiedColours = itemdefinition_7_.modifiedColours
    }

    fun toBind(itemdefinition_83_: ItemDefinition?, itemdefinition_84_: ItemDefinition?) {
        cost = 0
        tertiaryMaleModel = itemdefinition_84_!!.tertiaryMaleModel
        stackable = itemdefinition_84_.stackable
        members = itemdefinition_84_.members
        recolourPalette = itemdefinition_84_.recolourPalette
        spriteTranslateY = itemdefinition_83_!!.spriteTranslateY
        team = itemdefinition_84_.team
        secondaryMaleModel = itemdefinition_84_.secondaryMaleModel
        options = arrayOfNulls(5)
        floorOptions = itemdefinition_84_.floorOptions
        maleWieldY = itemdefinition_84_.maleWieldY
        primaryMaleDialogueHead = itemdefinition_84_.primaryMaleDialogueHead
        femaleWieldY = itemdefinition_84_.femaleWieldY
        name = itemdefinition_84_.name
        spriteScale = itemdefinition_83_.spriteScale
        originalColours = itemdefinition_84_.originalColours
        secondaryFemaleDialogueHead = itemdefinition_84_.secondaryFemaleDialogueHead
        params = itemdefinition_84_.params
        primaryFemaleModel = itemdefinition_84_.primaryFemaleModel
        spritePitch = itemdefinition_83_.spritePitch
        spriteCameraRoll = itemdefinition_83_.spriteCameraRoll
        femaleWieldX = itemdefinition_84_.femaleWieldX
        secondaryMaleDialogueHead = itemdefinition_84_.secondaryMaleDialogueHead
        tertiaryFemaleModel = itemdefinition_84_.tertiaryFemaleModel
        modifiedTextureColours = itemdefinition_84_.modifiedTextureColours
        maleWieldX = itemdefinition_84_.maleWieldX
        primaryFemaleDialogueHead = itemdefinition_84_.primaryFemaleDialogueHead
        modelId = itemdefinition_83_.modelId
        modifiedColours = itemdefinition_84_.modifiedColours
        secondaryFemaleModel = itemdefinition_84_.secondaryFemaleModel
        spriteTranslateX = itemdefinition_83_.spriteTranslateX
        spriteCameraYaw = itemdefinition_83_.spriteCameraYaw
        primaryMaleModel = itemdefinition_84_.primaryMaleModel
        femaleWieldZ = itemdefinition_84_.femaleWieldZ
        maleWieldZ = itemdefinition_84_.maleWieldZ
        originalTextureColours = itemdefinition_84_.originalTextureColours
        System.arraycopy(itemdefinition_84_.options, 0, options, 0, 4)
        options[4] = "Discard"
    }

    companion object {
        var sevenEighteen = false
    }
}