package worlds.gregs.hestia.game.client.update.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.api.client.update.components.Damage
import worlds.gregs.hestia.game.client.update.block.blocks.HitsBlock
import worlds.gregs.hestia.services.Aspect

open class HitsBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<HitsBlock>(Aspect.all(Renderable::class, Damage::class), flag = flag, mob = mob) {

    private lateinit var damageMapper: ComponentMapper<Damage>

    override fun create(player: Int, other: Int): HitsBlock {
        return HitsBlock(flag, damageMapper.get(other), player, other)
    }

}