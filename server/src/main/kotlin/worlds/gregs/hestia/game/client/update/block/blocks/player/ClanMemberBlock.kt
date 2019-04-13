package worlds.gregs.hestia.game.client.update.block.blocks.player

import worlds.gregs.hestia.api.client.update.block.UpdateBlock

data class ClanMemberBlock(override val flag: Int, val sameClanChat: Boolean) : UpdateBlock