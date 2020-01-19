package worlds.gregs.hestia.core.display.update.logic.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.ForceChat
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.game.update.blocks.ForceChatBlock

open class ForceChatBlockFactory(flag: Int, npc: Boolean = false) : BlockFactory<ForceChatBlock>(Aspect.all(Renderable::class, ForceChat::class), flag = flag, npc = npc) {

    private lateinit var forceChatMapper: ComponentMapper<ForceChat>

    override fun create(player: Int, other: Int): ForceChatBlock {
        return ForceChatBlock(flag, forceChatMapper.get(other)?.message ?: "")
    }

}