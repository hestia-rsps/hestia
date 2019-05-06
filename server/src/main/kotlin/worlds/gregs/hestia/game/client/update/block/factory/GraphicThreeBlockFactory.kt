package worlds.gregs.hestia.game.client.update.block.factory

import com.artemis.ComponentMapper
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.api.client.update.components.gfx.ThirdGraphic
import worlds.gregs.hestia.game.client.update.block.blocks.GraphicBlock
import worlds.gregs.hestia.services.Aspect

open class GraphicThreeBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<GraphicBlock>(Aspect.all(Renderable::class, ThirdGraphic::class), flag = flag, mob = mob) {

    private lateinit var thirdGraphicMapper: ComponentMapper<ThirdGraphic>

    override fun create(player: Int, other: Int): GraphicBlock {
        val graphic = thirdGraphicMapper.get(other)
        return GraphicBlock(flag, 3, graphic.id, (graphic.delay and 0xffff) or (graphic.height shl 16), (graphic.rotation and 0x7) or (/*graphic.slot*/0 shl 3) or (graphic.forceRefresh.int shl 7))
    }

}