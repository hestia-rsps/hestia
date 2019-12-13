package worlds.gregs.hestia.core.display.update.logic.block.factory

import com.artemis.ComponentMapper
import world.gregs.hestia.core.services.int
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.gfx.SecondGraphic
import worlds.gregs.hestia.game.update.blocks.GraphicBlock
import worlds.gregs.hestia.service.Aspect

open class GraphicTwoBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<GraphicBlock>(Aspect.all(Renderable::class, SecondGraphic::class), flag = flag, mob = mob) {

    private lateinit var secondGraphicMapper: ComponentMapper<SecondGraphic>

    override fun create(player: Int, other: Int): GraphicBlock {
        val graphic = secondGraphicMapper.get(other)
        return GraphicBlock(flag, 2, graphic.id, (graphic.delay and 0xffff) or (graphic.height shl 16), (graphic.rotation and 0x7) or (/*graphic.slot*/0 shl 3) or (graphic.forceRefresh.int shl 7))
    }


}