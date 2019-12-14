package worlds.gregs.hestia.core.display.update.logic.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.update.api.BlockFactory
import worlds.gregs.hestia.core.display.update.model.components.Renderable
import worlds.gregs.hestia.core.display.update.model.components.UpdateClanMember
import worlds.gregs.hestia.game.update.blocks.player.ClanMemberBlock
import worlds.gregs.hestia.artemis.Aspect

class ClanMemberPlayerBlockFactory(flag: Int) : BlockFactory<ClanMemberBlock>(Aspect.all(Renderable::class, UpdateClanMember::class), flag = flag) {

    private lateinit var clanMemberMapper: ComponentMapper<UpdateClanMember>

    override fun create(player: Int, other: Int): ClanMemberBlock {
        val clanMember = clanMemberMapper.get(other)
        return ClanMemberBlock(flag, clanMember.inSameClanChat)
    }

}