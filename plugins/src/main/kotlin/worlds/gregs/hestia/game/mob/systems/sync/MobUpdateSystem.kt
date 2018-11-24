package worlds.gregs.hestia.game.mob.systems.sync

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import worlds.gregs.hestia.game.component.*
import worlds.gregs.hestia.game.component.map.Viewport
import worlds.gregs.hestia.game.component.update.anim.FirstAnimation
import worlds.gregs.hestia.game.component.update.anim.FourthAnimation
import worlds.gregs.hestia.game.component.update.anim.SecondAnimation
import worlds.gregs.hestia.game.component.update.anim.ThirdAnimation
import worlds.gregs.hestia.game.component.update.direction.Facing
import worlds.gregs.hestia.game.component.update.direction.Watching
import worlds.gregs.hestia.game.component.update.gfx.FirstGraphic
import worlds.gregs.hestia.game.component.update.gfx.FourthGraphic
import worlds.gregs.hestia.game.component.update.gfx.SecondGraphic
import worlds.gregs.hestia.game.component.update.gfx.ThirdGraphic
import worlds.gregs.hestia.game.component.movement.Run
import worlds.gregs.hestia.game.component.movement.Walk
import worlds.gregs.hestia.game.component.update.*
import worlds.gregs.hestia.network.update.mob.*
import worlds.gregs.hestia.network.update.player.*
import worlds.gregs.hestia.game.component.entity.Type
import worlds.gregs.hestia.game.component.map.Position
import worlds.gregs.hestia.game.component.update.appearance.CombatLevel
import worlds.gregs.hestia.services.*
import worlds.gregs.hestia.game.systems.sync.SynchronizeSystem

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
    private lateinit var timeBarMapper: ComponentMapper<TimeBar>
    private lateinit var modelChangeMapper: ComponentMapper<MobModelChange>
    private lateinit var batchAnimationsMapper: ComponentMapper<BatchAnimations>
    private lateinit var colourOverlayMapper: ComponentMapper<ColourOverlay>
    private lateinit var typeMapper: ComponentMapper<Type>
    private lateinit var mobSubscription: EntitySubscription

    override fun getLocals(entityId: Int, viewport: Viewport): MutableList<Int> {
        return viewport.localMobs()
    }

    override fun getGlobals(entityId: Int, viewport: Viewport): List<Int> {
        return world.getSystem(worlds.gregs.hestia.game.mob.systems.sync.MobChunkSystem::class)
                .get(positionMapper.get(entityId))
                .filterNot { viewport.localMobs().contains(it) }
                .sorted()//Not required for mobs but makes loading nicer rather than in square chunks
    }

    override fun initialize() {
        super.initialize()
        mobSubscription = world.aspectSubscriptionManager.get(Aspect.all(worlds.gregs.hestia.game.mob.component.Mob::class))
        flags = listOf(
                //Third Graphic
                create(0x100000, Aspect.all(Renderable::class, ThirdGraphic::class), MobGraphicMask(thirdGraphicMapper)),
                //Watch Entity
                create(0x1, Aspect.all(Renderable::class, Watching::class), MobWatchEntityMask(watchingMapper)),
                //Fourth Graphic
                create(0x20000, Aspect.all(Renderable::class, FourthGraphic::class), MobGraphicMask(fourthGraphicMapper)),
                //Hits
                create(0x40, Aspect.all(Renderable::class, Damage::class), HitsMask(damageMapper, true)),
                //Time bar
                create(0x100, Aspect.all(Renderable::class, TimeBar::class), MobTimeBarMask(timeBarMapper)),
                //Name
                create(0x40000, Aspect.all(Renderable::class, UpdateDisplayName::class), MobNameMask(displayNameMapper), true),
                //Transform
                create(0x20, Aspect.all(Renderable::class, Transform::class), MobTransformMask(transformMapper, typeMapper)),
                //Force Chat
                create(0x2, Aspect.all(Renderable::class, ForceChat::class), ForceChatMask(forceChatMapper)),
                //Face Direction
                create(0x8, Aspect.all(Renderable::class, Facing::class).exclude(Run::class, Walk::class), MobFacingMask(positionMapper, facingMapper)),
                //Combat level
                create(0x80000, Aspect.all(Renderable::class, UpdateCombatLevel::class), MobCombatLevelMask(combatLevelMapper), true),
                //0x2000 - Weapon hidden/rotation
                //0x10000 - Model change (not sure it actually does anything)
                //Force Movement
                create(0x400, Aspect.all(Renderable::class, Position::class, ForceMovement::class), MobForceMovementMask(positionMapper, forceMovementMapper)),
                //Animation
                create(0x10, Aspect.all(Renderable::class).one(FirstAnimation::class, SecondAnimation::class, ThirdAnimation::class, FourthAnimation::class), MobAnimMask(firstAnimationMapper, secondAnimationMapper, thirdAnimationMapper, fourthAnimationMapper)),
                //Model change
                create(0x800, Aspect.all(Renderable::class, MobModelChange::class), MobModelChangeMask(modelChangeMapper)),
                //Batch animations
                create(0x4000, Aspect.all(Renderable::class, BatchAnimations::class), MobBatchAnimationMask(batchAnimationsMapper)),
                //Second Graphic
                create(0x1000, Aspect.all(Renderable::class, SecondGraphic::class), MobGraphicMask(secondGraphicMapper)),
                //First Graphic
                create(0x4, Aspect.all(Renderable::class, FirstGraphic::class), MobGraphicMask(firstGraphicMapper)),
                //Colour overlay
                create(0x200, Aspect.all(Renderable::class, ColourOverlay::class), MobColourOverlayMask(colourOverlayMapper))
        )
    }

}