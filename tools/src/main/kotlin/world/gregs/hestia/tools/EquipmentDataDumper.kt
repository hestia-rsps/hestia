package world.gregs.hestia.tools

import com.displee.cache.CacheLibrary
import world.gregs.hestia.cache.Indices
import world.gregs.hestia.cache.definition.Definition
import world.gregs.hestia.cache.definition.DefinitionReader
import world.gregs.hestia.io.Reader
import java.io.DataOutputStream
import java.io.File
import java.util.HashMap
import java.util.concurrent.ConcurrentHashMap

/**
 * Extracts 700+ item equip info and write's it to a file.
 */
object EquipmentDataDumper {
    private class ItemDefinition : Definition {
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

        override fun readValues(opcode: Int, buffer: Reader, member: Boolean) {
            when (opcode) {
                1 -> modelId = buffer.readBigSmart()
                2 -> name = buffer.readString()
                4 -> spriteScale = buffer.readShort()
                5 -> spritePitch = buffer.readShort()
                6 -> spriteCameraRoll = buffer.readShort()
                7 -> {
                    spriteTranslateX = buffer.readShort()
                    if (spriteTranslateX > 32767) {
                        spriteTranslateX -= 65536
                    }
                }
                8 -> {
                    spriteTranslateY = buffer.readShort()
                    if (spriteTranslateY > 32767) {
                        spriteTranslateY -= 65536
                    }
                }
                11 -> stackable = 1
                12 -> cost = buffer.readInt()
                13 -> equipSlot = buffer.readUnsignedByte()//718 only
                14 -> equipType = buffer.readUnsignedByte()//718 only
                16 -> members = true
                18 -> multiStackSize = buffer.readShort()
                23 -> primaryMaleModel = buffer.readBigSmart()
                24 -> secondaryMaleModel = buffer.readBigSmart()
                25 -> primaryFemaleModel = buffer.readBigSmart()
                26 -> secondaryFemaleModel = buffer.readBigSmart()
                in 30..34 -> floorOptions[opcode - 30] = buffer.readString()
                in 35..39 -> options[opcode - 35] = buffer.readString()
                40 -> {
                    val length = buffer.readUnsignedByte()
                    originalColours = ShortArray(length)
                    modifiedColours = ShortArray(length)
                    repeat(length) { count ->
                        originalColours!![count] = buffer.readShort().toShort()
                        modifiedColours!![count] = buffer.readShort().toShort()
                    }
                }
                41 -> {
                    val length = buffer.readUnsignedByte()
                    originalTextureColours = ShortArray(length)
                    modifiedTextureColours = ShortArray(length)
                    repeat(length) { count ->
                        originalTextureColours!![count] = buffer.readShort().toShort()
                        modifiedTextureColours!![count] = buffer.readShort().toShort()
                    }
                }
                42 -> {
                    val length = buffer.readUnsignedByte()
                    recolourPalette = ByteArray(length)
                    repeat(length) { count ->
                        recolourPalette!![count] = buffer.readByte().toByte()
                    }
                }
                65 -> unnoted = true
                78 -> tertiaryMaleModel = buffer.readBigSmart()
                79 -> tertiaryFemaleModel = buffer.readBigSmart()
                90 -> primaryMaleDialogueHead = buffer.readBigSmart()
                91 -> primaryFemaleDialogueHead = buffer.readBigSmart()
                92 -> secondaryMaleDialogueHead = buffer.readBigSmart()
                93 -> secondaryFemaleDialogueHead = buffer.readBigSmart()
                95 -> spriteCameraYaw = buffer.readShort()
                96 -> dummyItem = buffer.readUnsignedByte()
                97 -> noteId = buffer.readShort()
                98 -> notedTemplateId = buffer.readShort()
                in 100..109 -> {
                    if (stackIds == null) {
                        stackAmounts = IntArray(10)
                        stackIds = IntArray(10)
                    }
                    stackIds!![opcode - 100] = buffer.readShort()
                    stackAmounts!![opcode - 100] = buffer.readShort()
                }
                110 -> groundScaleX = buffer.readShort()
                111 -> groundScaleY = buffer.readShort()
                112 -> groundScaleZ = buffer.readShort()
                113 -> ambience = buffer.readByte()
                114 -> diffusion = buffer.readByte() * 5
                115 -> team = buffer.readUnsignedByte()
                121 -> lendId = buffer.readShort()
                122 -> lendTemplateId = buffer.readShort()
                125 -> {
                    maleWieldX = buffer.readByte() shl 2
                    maleWieldY = buffer.readByte() shl 2
                    maleWieldZ = buffer.readByte() shl 2
                }
                126 -> {
                    femaleWieldX = buffer.readByte() shl 2
                    femaleWieldY = buffer.readByte() shl 2
                    femaleWieldZ = buffer.readByte() shl 2
                }
                127 -> {
                    primaryCursorOpcode = buffer.readUnsignedByte()
                    primaryCursor = buffer.readShort()
                }
                128 -> {
                    secondaryCursorOpcode = buffer.readUnsignedByte()
                    secondaryCursor = buffer.readShort()
                }
                129 -> {
                    primaryInterfaceCursorOpcode = buffer.readUnsignedByte()
                    primaryInterfaceCursor = buffer.readShort()
                }
                130 -> {
                    secondaryInterfaceCursorOpcode = buffer.readUnsignedByte()
                    secondaryInterfaceCursor = buffer.readShort()
                }
                132 -> {
                    val length = buffer.readUnsignedByte()
                    campaigns = IntArray(length)
                    repeat(length) { count ->
                        campaigns!![count] = buffer.readShort()
                    }
                }
                134 -> pickSizeShift = buffer.readUnsignedByte()
                139 -> bindId = buffer.readShort()
                140 -> bindTemplateId = buffer.readShort()
                249 -> {
                    val length = buffer.readUnsignedByte()
                    if (params == null) {
                        val initialCapacity = calculateCapacity(length)
                        params = HashMap(initialCapacity)
                    }
                    repeat(length) {
                        val isString = buffer.readUnsignedBoolean()
                        val id = buffer.readMedium()
                        params!![id.toLong()] = if (isString) {
                            buffer.readString()
                        } else {
                            buffer.readInt()
                        }
                    }
                }
            }
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
    }
    private class ItemDefinitionReader(cacheStore: CacheLibrary) : DefinitionReader<ItemDefinition> {

        override val index = cacheStore.index(Indices.ITEMS)

        override val cache = ConcurrentHashMap<Int, ItemDefinition>()

        override fun create(id: Int, member: Boolean) = ItemDefinition().apply {
            this.id = id
            this.floorOptions = defaultGroundOptions.clone()
            this.options = defaultOptions.clone()

            readData(id ushr 8, id and 0xff)

            if (notedTemplateId != -1) {
                toNote(get(notedTemplateId), get(noteId))
            }
            if (lendTemplateId != -1) {
                toLend(get(lendId), get(lendTemplateId))
            }
            if (bindTemplateId != -1) {
                toBind(get(bindTemplateId), get(bindId))
            }
            if (!member && members) {
                name = "Members object"
                floorOptions = defaultGroundOptions
                options = defaultOptions
                campaigns = null
                team = 0
                unnoted = false
                params = null
            }
        }

        companion object {
            val defaultOptions = arrayOf(null, null, null, null, "Drop")
            private val defaultGroundOptions = arrayOf(null, null, "Take", null, null, "Examine")
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val store = CacheLibrary("${System.getProperty("user.home")}\\Downloads\\rs718_cache\\")
        val reader = ItemDefinitionReader(store)
        val itemCount = reader.size
        println(itemCount)
        var file = File("equipmentSlots.dat")
        var stream = DataOutputStream(file.outputStream())
        repeat(22323) { id ->
            val def = reader.get(id)
            if(def.equipSlot > 0) {
                stream.writeShort(id)
                stream.writeByte(def.equipSlot)
            }
        }
        stream.close()

        file = File("equipmentTypes.dat")
        stream = DataOutputStream(file.outputStream())
        repeat(22323) { id ->
            val def = reader.get(id)
            if(def.equipType > 0) {
                stream.writeShort(id)
                stream.writeByte(def.equipType)
            }
        }
        println("Done")
    }
}