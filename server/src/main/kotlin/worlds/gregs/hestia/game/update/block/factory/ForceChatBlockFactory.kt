package worlds.gregs.hestia.game.update.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.ForceChat
import worlds.gregs.hestia.game.update.block.blocks.ForceChatBlock
import worlds.gregs.hestia.services.Aspect

open class ForceChatBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<ForceChatBlock>(Aspect.all(Renderable::class, ForceChat::class), flag = flag, mob = mob) {

    private lateinit var forceChatMapper: ComponentMapper<ForceChat>

    override fun create(player: Int, other: Int): ForceChatBlock {
        return ForceChatBlock(flag, forceChatMapper.get(other)?.message
                ?: "")
    }

}