package worlds.gregs.hestia.network.update.codec

import worlds.gregs.hestia.game.update.block.UpdateBlock
import kotlin.reflect.KClass

/**
 * Container for [UpdateBlockEncoder]s
 */
abstract class UpdateBlockCodec {

    private val encoders = HashMap<KClass<*>, UpdateBlockEncoder<*>>()

    @Suppress("UNCHECKED_CAST")
    fun <T : UpdateBlock> get(clazz: KClass<T>): UpdateBlockEncoder<T>? {
        return encoders[clazz] as? UpdateBlockEncoder<T>
    }

    fun <T : UpdateBlock> bind(type: KClass<T>, encoder: UpdateBlockEncoder<T>) {
        if(encoders.contains(type)) {
            throw IllegalArgumentException("Cannot have duplicate encoders $type $encoder")
        }
        encoders[type] = encoder
    }

    inline fun <reified T : UpdateBlock> bind(encoder: UpdateBlockEncoder<T>) {
        bind(T::class, encoder)
    }

}