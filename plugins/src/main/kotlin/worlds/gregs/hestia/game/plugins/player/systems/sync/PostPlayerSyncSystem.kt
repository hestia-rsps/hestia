package worlds.gregs.hestia.game.plugins.player.systems.sync

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.player.component.update.*
import worlds.gregs.hestia.services.Aspect

class PostPlayerSyncSystem : IteratingSystem(Aspect.all(Renderable::class)) {

    private lateinit var appearanceMapper: ComponentMapper<Appearance>
    private lateinit var updateMovementMapper: ComponentMapper<UpdateMovement>
    private lateinit var updateMoveTypeMapper: ComponentMapper<UpdateMoveType>
    private lateinit var clanMemberMapper: ComponentMapper<UpdateClanMember>
    private lateinit var unknownMapper: ComponentMapper<UpdateUnknown>
    private lateinit var miniMapDotMapper: ComponentMapper<PlayerMiniMapDot>

    override fun process(entityId: Int) {
        appearanceMapper.remove(entityId)
        updateMovementMapper.remove(entityId)
        updateMoveTypeMapper.remove(entityId)
        clanMemberMapper.remove(entityId)
        unknownMapper.remove(entityId)
        miniMapDotMapper.remove(entityId)
    }
}