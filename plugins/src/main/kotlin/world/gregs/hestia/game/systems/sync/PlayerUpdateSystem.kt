package world.gregs.hestia.game.systems.sync

import com.artemis.ComponentMapper
import world.gregs.hestia.game.component.*
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.component.update.anim.FirstAnimation
import world.gregs.hestia.game.component.update.anim.FourthAnimation
import world.gregs.hestia.game.component.update.anim.SecondAnimation
import world.gregs.hestia.game.component.update.anim.ThirdAnimation
import world.gregs.hestia.game.component.update.direction.Facing
import world.gregs.hestia.game.component.update.direction.Watching
import world.gregs.hestia.game.component.update.gfx.FirstGraphic
import world.gregs.hestia.game.component.update.gfx.FourthGraphic
import world.gregs.hestia.game.component.update.gfx.SecondGraphic
import world.gregs.hestia.game.component.update.gfx.ThirdGraphic
import world.gregs.hestia.game.component.movement.*
import world.gregs.hestia.game.component.update.AppearanceData
import world.gregs.hestia.game.component.update.Damage
import world.gregs.hestia.game.component.update.ForceChat
import world.gregs.hestia.game.component.update.ForceMovement
import world.gregs.hestia.network.update.player.*
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.services.exclude
import world.gregs.hestia.services.one
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.update.Appearance

abstract class PlayerUpdateSystem(aspect: com.artemis.Aspect.Builder): SynchronizeSystem(aspect) {

    //Flags
    private lateinit var firstAnimationMapper: ComponentMapper<FirstAnimation>
    private lateinit var secondAnimationMapper: ComponentMapper<SecondAnimation>
    private lateinit var thirdAnimationMapper: ComponentMapper<ThirdAnimation>
    private lateinit var fourthAnimationMapper: ComponentMapper<FourthAnimation>
    private lateinit var firstGraphicMapper: ComponentMapper<FirstGraphic>
    private lateinit var secondGraphicMapper: ComponentMapper<SecondGraphic>
    private lateinit var thirdGraphicMapper: ComponentMapper<ThirdGraphic>
    private lateinit var fourthGraphicMapper: ComponentMapper<FourthGraphic>
    private lateinit var walkingMapper: ComponentMapper<Walking>
    private lateinit var runningMapper: ComponentMapper<Running>
    private lateinit var damageMapper: ComponentMapper<Damage>
    private lateinit var appearanceDataMapper: ComponentMapper<AppearanceData>
    private lateinit var forceChatMapper: ComponentMapper<ForceChat>
    private lateinit var watchingMapper: ComponentMapper<Watching>
    private lateinit var forceMovementMapper: ComponentMapper<ForceMovement>
    private lateinit var facingMapper: ComponentMapper<Facing>

    override fun getLocals(viewport: Viewport): MutableList<Int> {
        return viewport.localPlayers()
    }

    override fun getGlobals(viewport: Viewport): MutableList<Int> {
        return viewport.globalPlayers()
    }

    override fun initialize() {
        super.initialize()
        flags = listOf(
                //Animation
                create(0x40, Aspect.all(Renderable::class).one(FirstAnimation::class, SecondAnimation::class, ThirdAnimation::class, FourthAnimation::class), PlayerAnimMask(firstAnimationMapper, secondAnimationMapper, thirdAnimationMapper, fourthAnimationMapper), true),
                //Third Graphic
                create(0x40000, Aspect.all(Renderable::class, ThirdGraphic::class), PlayerGraphicMask(thirdGraphicMapper)),
                //Move Type
                create(0x200, Aspect.all(Renderable::class).one(Moving::class, Walking::class), PlayerMoveTypeMask(walkingMapper, runningMapper, movingMapper), true),
                //Fourth Graphic
                create(0x80000, Aspect.all(Renderable::class, FourthGraphic::class), PlayerGraphicMask(fourthGraphicMapper)),
                //Hits
                create(0x4, Aspect.all(Renderable::class, Damage::class), HitsMask(damageMapper, false)),
                //Appearance
                create(0x8, Aspect.all(Renderable::class, Appearance::class), PlayerAppearanceMask(appearanceDataMapper), true),
                //Force Chat
                create(0x4000, Aspect.all(Renderable::class, ForceChat::class), ForceChatMask(forceChatMapper)),
                //Movement Type
                create(0x1, Aspect.all(Renderable::class, UpdateMovement::class), PlayerMovementMask(runningMapper), true),
                //Watch Entity
                create(0x10, Aspect.all(Renderable::class, Watching::class), PlayerWatchEntityMask(watchingMapper)),
                //Force Movement
                create(0x1000, Aspect.all(Renderable::class, Position::class, ForceMovement::class), PlayerForceMovementMask(positionMapper, forceMovementMapper)),
                //Face Direction
                create(0x20, Aspect.all(Renderable::class, Facing::class).exclude(Run::class, Walk::class), PlayerFacingMask(facingMapper), true),
                //First Graphic
                create(0x2, Aspect.all(Renderable::class, FirstGraphic::class), PlayerGraphicMask(firstGraphicMapper)),
                //Second Graphic
                create(0x100, Aspect.all(Renderable::class, SecondGraphic::class), PlayerGraphicMask(secondGraphicMapper))
        )
    }
}