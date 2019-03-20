package worlds.gregs.hestia.game.update.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.update.components.Renderable
import worlds.gregs.hestia.game.update.block.BlockFactory
import worlds.gregs.hestia.game.update.block.blocks.player.AppearanceBlock
import worlds.gregs.hestia.game.update.components.Appearance
import worlds.gregs.hestia.game.update.components.AppearanceData
import worlds.gregs.hestia.services.Aspect

class AppearancePlayerBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<AppearanceBlock>(Aspect.all(Renderable::class, Appearance::class), true, flag = flag, mob = mob) {

    private lateinit var appearanceDataMapper: ComponentMapper<AppearanceData>

    override fun create(player: Int, other: Int): AppearanceBlock {
        val data = appearanceDataMapper.get(other)?.data
        return AppearanceBlock(flag, data ?: byteArrayOf())
    }

}