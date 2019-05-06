package worlds.gregs.hestia.game.client.update.block.factory.mob

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.api.client.update.components.DisplayName
import worlds.gregs.hestia.api.client.update.components.UpdateDisplayName
import worlds.gregs.hestia.game.client.update.block.blocks.mob.NameBlock
import worlds.gregs.hestia.services.Aspect

class NameMobBlockFactory(flag: Int) : BlockFactory<NameBlock>(Aspect.all(Renderable::class, UpdateDisplayName::class), true, flag = flag, mob = true) {

    private lateinit var displayNameMapper: ComponentMapper<DisplayName>

    override fun create(player: Int, other: Int): NameBlock {
        return NameBlock(flag, displayNameMapper.get(other)?.name
                ?: "")
    }

}