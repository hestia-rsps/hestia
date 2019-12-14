package worlds.gregs.hestia.core.display.update.logic.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.DisplayName
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.UpdateDisplayName
import worlds.gregs.hestia.game.update.blocks.mob.NameBlock
import worlds.gregs.hestia.artemis.Aspect

class NameMobBlockFactory(flag: Int) : BlockFactory<NameBlock>(Aspect.all(Renderable::class, UpdateDisplayName::class), true, flag = flag, mob = true) {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>

    override fun create(player: Int, other: Int): NameBlock {
        return NameBlock(flag, displayNameMapper.get(other)?.name
                ?: "")
    }

}