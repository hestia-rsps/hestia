package worlds.gregs.hestia.game.plugins.player.systems.update

import com.artemis.ComponentMapper
import com.artemis.annotations.Wire
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.client.PlayerUpdateFlags
import worlds.gregs.hestia.api.core.components.Renderable
import worlds.gregs.hestia.api.movement.types.Move
import worlds.gregs.hestia.api.movement.types.Run
import worlds.gregs.hestia.api.movement.types.Walk
import worlds.gregs.hestia.game.plugins.player.component.update.*
import worlds.gregs.hestia.network.update.player.*
import worlds.gregs.hestia.services.Aspect

@Wire(failOnNull = false)
class PlayerUpdateFlagInserts : PassiveSystem() {

    private var flags: PlayerUpdateFlags? = null

    //Flags
    private lateinit var appearanceDataMapper: ComponentMapper<AppearanceData>
    private lateinit var clanMemberMapper: ComponentMapper<UpdateClanMember>
    private lateinit var miniMapDotMapper: ComponentMapper<PlayerMiniMapDot>
    private lateinit var updateUnknownMapper: ComponentMapper<UpdateUnknown>
    private var run: Run? = null
    private var walk: Walk? = null
    private var move: Move? = null

    override fun initialize() {
        super.initialize()

        val flags = flags ?: return

        //Move Type
        flags.insertAfter(0x20000, flags.create(0x200, Aspect.all(Renderable::class, UpdateMoveType::class), PlayerMoveTypeMask(walk, run, move), true))
        //Clan chat member
        flags.insertAfter(0x80000, flags.create(0x100000, Aspect.all(Renderable::class, UpdateClanMember::class), ClanMemberMask(clanMemberMapper)))
        //Hidden weapon rotation/direction
        flags.insertAfter(0x4, flags.create(0x10000, Aspect.all(Renderable::class, UpdateUnknown::class), UnknownMask(updateUnknownMapper)))
        //Appearance
        flags.insertAfter(0x10000, flags.create(0x8, Aspect.all(Renderable::class, Appearance::class), PlayerAppearanceMask(appearanceDataMapper), true))
        //Changes other players mini-map dot from white to a "p"
        flags.insertAfter(0x4000, flags.create(0x400, Aspect.all(Renderable::class, PlayerMiniMapDot::class), PlayerMiniMapMask(miniMapDotMapper)))
        //Movement Type
        flags.insertAfter(0x400, flags.create(0x1, Aspect.all(Renderable::class, UpdateMovement::class), PlayerMovementMask(run), true))
    }
}