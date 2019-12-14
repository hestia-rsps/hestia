package worlds.gregs.hestia.core.display.update.logic.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Damage
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.game.update.blocks.HitsBlock
import worlds.gregs.hestia.service.Aspect

open class HitsBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<HitsBlock>(Aspect.all(Renderable::class, Damage::class), flag = flag, mob = mob) {

    private lateinit var damageMapper: ComponentMapper<Damage>

    override fun create(player: Int, other: Int): HitsBlock {
        return HitsBlock(flag, damageMapper.get(other).hits.toList(), player, other)
    }

}