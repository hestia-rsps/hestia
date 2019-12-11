package worlds.gregs.hestia.game.client.update.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.game.client.update.block.blocks.ColourOverlayBlock
import worlds.gregs.hestia.api.client.update.components.ColourOverlay
import worlds.gregs.hestia.services.Aspect

open class ColourOverlayBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<ColourOverlayBlock>(Aspect.all(Renderable::class, ColourOverlay::class), flag = flag, mob = mob) {

    private lateinit var colourOverlayMapper: ComponentMapper<ColourOverlay>

    override fun create(player: Int, other: Int): ColourOverlayBlock {
        val colour = colourOverlayMapper.get(other)
        return ColourOverlayBlock(flag, colour.delay, colour.duration, colour.colour)
    }

}