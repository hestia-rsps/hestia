package worlds.gregs.hestia.game.plugins.player.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.plugins.client.systems.update.update.flag.PlayerUpdateFlagSystem
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.movement.components.types.Moving
import worlds.gregs.hestia.game.plugins.movement.components.RunToggled
import worlds.gregs.hestia.game.plugins.movement.components.types.Running
import worlds.gregs.hestia.game.plugins.movement.components.types.Walking
import worlds.gregs.hestia.game.plugins.player.component.update.*
import worlds.gregs.hestia.network.update.player.*
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.one

class PlayerUpdateFlagInserts : PassiveSystem() {

    private lateinit var flagSystem: PlayerUpdateFlagSystem

    //Flags
    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var walkingMapper: ComponentMapper<Walking>
    private lateinit var runningMapper: ComponentMapper<Running>
    private lateinit var runToggledMapper: ComponentMapper<RunToggled>
    private lateinit var appearanceDataMapper: ComponentMapper<AppearanceData>
    private lateinit var clanMemberMapper: ComponentMapper<UpdateClanMember>
    private lateinit var miniMapDotMapper: ComponentMapper<PlayerMiniMapDot>
    private lateinit var updateUnknownMapper: ComponentMapper<UpdateUnknown>

    override fun initialize() {
        super.initialize()

        //Move Type
        flagSystem.insertAfter(0x20000, flagSystem.create(0x200, Aspect.all(Renderable::class).one(Moving::class, UpdateMoveType::class), PlayerMoveTypeMask(walkingMapper, runningMapper, movingMapper), true))
        //Clan chat member
        flagSystem.insertAfter(0x80000, flagSystem.create(0x100000, Aspect.all(Renderable::class, UpdateClanMember::class), ClanMemberMask(clanMemberMapper)))
        //Hidden weapon rotation/direction
        flagSystem.insertAfter(0x4, flagSystem.create(0x10000, Aspect.all(Renderable::class, UpdateUnknown::class), UnknownMask(updateUnknownMapper)))
        //Appearance
        flagSystem.insertAfter(0x10000, flagSystem.create(0x8, Aspect.all(Renderable::class, Appearance::class), PlayerAppearanceMask(appearanceDataMapper), true))
        //Changes other players mini-map dot from white to a "p"
        flagSystem.insertAfter(0x4000, flagSystem.create(0x400, Aspect.all(Renderable::class, PlayerMiniMapDot::class), PlayerMiniMapMask(miniMapDotMapper)))
        //Movement Type
        flagSystem.insertAfter(0x400, flagSystem.create(0x1, Aspect.all(Renderable::class, UpdateMovement::class), PlayerMovementMask(runToggledMapper), true))
    }
}