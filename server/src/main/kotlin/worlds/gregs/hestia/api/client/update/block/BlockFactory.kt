package worlds.gregs.hestia.api.client.update.block

import com.artemis.Aspect
import com.artemis.BaseEntitySystem

/**
 *  A passive system used to create [UpdateBlock]s
 *  @param aspect The aspect which an entity must have to update this block
 *  @param added Whether the block should be updated immediately when added
 *  @param mob Whether the factory is for a mob or player block
 *  @param flag The block encode flag
 */
abstract class BlockFactory<T : UpdateBlock>(aspect: Aspect.Builder, open val added: Boolean = false, open val mob: Boolean = false, internal open val flag: Int) : BaseEntitySystem(aspect) {

    /**
     * Creates update data block
     * @param player The player entity id
     * @param other The other entity id
     * @return The factories [UpdateBlock]
     */
    abstract fun create(player: Int, other: Int): T

    override fun processSystem() {
        // do nothing!
    }

    override fun checkProcessing(): Boolean {
        isEnabled = false
        return false
    }

}