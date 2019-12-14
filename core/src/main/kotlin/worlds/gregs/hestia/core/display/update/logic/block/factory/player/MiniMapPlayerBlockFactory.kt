package worlds.gregs.hestia.core.display.update.logic.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.PlayerMiniMapDot
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.game.update.blocks.player.MiniMapPlayerBlock
import worlds.gregs.hestia.service.Aspect

class MiniMapPlayerBlockFactory(flag: Int) : BlockFactory<MiniMapPlayerBlock>(Aspect.all(Renderable::class, PlayerMiniMapDot::class), flag = flag) {

    private lateinit var miniMapDotMapper: ComponentMapper<PlayerMiniMapDot>

    override fun create(player: Int, other: Int): MiniMapPlayerBlock {
        return MiniMapPlayerBlock(flag, miniMapDotMapper.get(other)?.p
                ?: false)
    }

}