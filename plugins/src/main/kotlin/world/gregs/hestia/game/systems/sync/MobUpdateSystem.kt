package world.gregs.hestia.game.systems.sync

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
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
import world.gregs.hestia.game.component.movement.Run
import world.gregs.hestia.game.component.movement.Walk
import world.gregs.hestia.game.component.update.*
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.services.exclude
import world.gregs.hestia.services.toArray
import world.gregs.hestia.network.update.mob.*
import world.gregs.hestia.network.update.player.*
import world.gregs.hestia.game.component.entity.Mob
import world.gregs.hestia.services.one
import world.gregs.hestia.game.component.map.Position

abstract class MobUpdateSystem(aspect: com.artemis.Aspect.Builder) : SynchronizeSystem(aspect) {
    //Flags
    private lateinit var firstAnimationMapper: ComponentMapper<FirstAnimation>
    private lateinit var secondAnimationMapper: ComponentMapper<SecondAnimation>
    private lateinit var thirdAnimationMapper: ComponentMapper<ThirdAnimation>
    private lateinit var fourthAnimationMapper: ComponentMapper<FourthAnimation>
    private lateinit var firstGraphicMapper: ComponentMapper<FirstGraphic>
    private lateinit var secondGraphicMapper: ComponentMapper<SecondGraphic>
    private lateinit var thirdGraphicMapper: ComponentMapper<ThirdGraphic>
    private lateinit var fourthGraphicMapper: ComponentMapper<FourthGraphic>
    private lateinit var damageMapper: ComponentMapper<Damage>
    private lateinit var forceChatMapper: ComponentMapper<ForceChat>
    private lateinit var watchingMapper: ComponentMapper<Watching>
    private lateinit var forceMovementMapper: ComponentMapper<ForceMovement>
    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var displayNameMapper: ComponentMapper<DisplayName>
    private lateinit var combatLevelMapper: ComponentMapper<CombatLevel>
    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var mobSubscription: EntitySubscription

    override fun getLocals(viewport: Viewport): MutableList<Int> {
        return viewport.localMobs()
    }

    override fun getGlobals(viewport: Viewport): MutableList<Int> {
        return mobSubscription.entities.toArray()
                .filterNot { viewport.localMobs().contains(it) }
                .toMutableList()
    }

    override fun initialize() {
        super.initialize()
        mobSubscription = world.aspectSubscriptionManager.get(Aspect.all(Mob::class))
        flags = listOf(
                //Third Graphic
                create(0x100000, Aspect.all(Renderable::class, ThirdGraphic::class), MobGraphicMask(thirdGraphicMapper)),
                //Watch Entity
                create(0x1, Aspect.all(Renderable::class, Watching::class), MobWatchEntityMask(watchingMapper)),
                //Fourth Graphic
                create(0x20000, Aspect.all(Renderable::class, FourthGraphic::class), MobGraphicMask(fourthGraphicMapper)),
                //Hits
                create(0x40, Aspect.all(Renderable::class, Damage::class), HitsMask(damageMapper, true)),
                //Name
                create(0x40000, Aspect.all(Renderable::class).one(UpdateDisplayName::class), MobNameMask(displayNameMapper), true),
                //Transform
                create(0x20, Aspect.all(Renderable::class).one(Transform::class), MobTransformMask(transformMapper)),
                //Force Chat
                create(0x2, Aspect.all(Renderable::class, ForceChat::class), ForceChatMask(forceChatMapper)),
                //Face Direction
                create(0x8, Aspect.all(Renderable::class, Facing::class).exclude(Run::class, Walk::class), MobFacingMask(positionMapper, facingMapper)),
                //Force Movement
                create(0x400, Aspect.all(Renderable::class, Position::class, ForceMovement::class), MobForceMovementMask(positionMapper, forceMovementMapper)),
                //Combat level
                create(0x80000, Aspect.all(Renderable::class, UpdateCombatLevel::class), MobCombatLevelMask(combatLevelMapper), true),
                //Animation
                create(0x10, Aspect.all(Renderable::class).one(FirstAnimation::class, SecondAnimation::class, ThirdAnimation::class, FourthAnimation::class), MobAnimMask(firstAnimationMapper, secondAnimationMapper, thirdAnimationMapper, fourthAnimationMapper)),
                //Second Graphic
                create(0x1000, Aspect.all(Renderable::class, SecondGraphic::class), MobGraphicMask(secondGraphicMapper)),
                //First Graphic
                create(0x4, Aspect.all(Renderable::class, FirstGraphic::class), MobGraphicMask(firstGraphicMapper))
        )
    }

}