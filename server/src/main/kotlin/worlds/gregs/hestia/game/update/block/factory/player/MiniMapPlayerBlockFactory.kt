package worlds.gregs.hestia.game.update.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.components.PlayerMiniMapDot
import worlds.gregs.hestia.game.update.block.blocks.player.MiniMapPlayerBlock
import worlds.gregs.hestia.services.Aspect

class MiniMapPlayerBlockFactory(flag: Int) : BlockFactory<MiniMapPlayerBlock>(Aspect.all(Renderable::class, PlayerMiniMapDot::class), flag = flag) {

    private lateinit var miniMapDotMapper: ComponentMapper<PlayerMiniMapDot>

    override fun create(player: Int, other: Int): MiniMapPlayerBlock {
        return MiniMapPlayerBlock(flag, miniMapDotMapper.get(other)?.p
                ?: false)
    }

}