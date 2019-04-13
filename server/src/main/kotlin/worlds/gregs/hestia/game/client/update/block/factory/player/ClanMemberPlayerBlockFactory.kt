package worlds.gregs.hestia.game.client.update.block.factory.player

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.api.client.update.components.UpdateClanMember
import worlds.gregs.hestia.game.client.update.block.blocks.player.ClanMemberBlock
import worlds.gregs.hestia.services.Aspect

class ClanMemberPlayerBlockFactory(flag: Int) : BlockFactory<ClanMemberBlock>(Aspect.all(Renderable::class, UpdateClanMember::class), flag = flag) {

    private lateinit var clanMemberMapper: ComponentMapper<UpdateClanMember>

    override fun create(player: Int, other: Int): ClanMemberBlock {
        val clanMember = clanMemberMapper.get(other)
        return ClanMemberBlock(flag, clanMember.inSameClanChat)
    }

}