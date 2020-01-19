package worlds.gregs.hestia.core.display.update.logic.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.artemis.Aspect
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.ColourOverlay
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.game.update.blocks.ColourOverlayBlock

open class ColourOverlayBlockFactory(flag: Int, npc: Boolean = false) : BlockFactory<ColourOverlayBlock>(Aspect.all(Renderable::class, ColourOverlay::class), flag = flag, npc = npc) {

    private lateinit var colourOverlayMapper: ComponentMapper<ColourOverlay>

    override fun create(player: Int, other: Int): ColourOverlayBlock {
        val colour = colourOverlayMapper.get(other)
        return ColourOverlayBlock(flag, colour.delay, colour.duration, colour.colour)
    }

}