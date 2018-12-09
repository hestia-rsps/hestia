package worlds.gregs.hestia.game.plugins.client.systems.update.update.flag

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.api.client.MobUpdateFlags
import worlds.gregs.hestia.game.plugins.client.components.NetworkSession
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalMobs
import worlds.gregs.hestia.game.plugins.client.components.update.stage.EntityUpdates
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.core.components.map.Position
import worlds.gregs.hestia.game.plugins.core.components.map.Viewport
import worlds.gregs.hestia.game.plugins.entity.components.update.*
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.FirstAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.FourthAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.SecondAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.ThirdAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Face
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Facing
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Watching
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.FirstGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.FourthGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.SecondGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.ThirdGraphic
import worlds.gregs.hestia.network.update.mob.*
import worlds.gregs.hestia.network.update.player.ForceChatMask
import worlds.gregs.hestia.network.update.player.HitsMask
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.one

class MobUpdateFlagSystem : MobUpdateFlags(Aspect.all(NetworkSession::class, Viewport::class).one(GlobalMobs::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var entityUpdatesMapper: ComponentMapper<EntityUpdates>
    private lateinit var globalMobsMapper: ComponentMapper<GlobalMobs>

    override fun process(entityId: Int) {
        val updates = entityUpdatesMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)
        check(updates, viewport.localMobs())
        check(entityId, updates, globalMobsMapper)
    }

    //Flags
    private lateinit var positionMapper: ComponentMapper<Position>
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
    private lateinit var faceMapper: ComponentMapper<Face>
    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var timeBarMapper: ComponentMapper<TimeBar>
    private lateinit var batchAnimationsMapper: ComponentMapper<BatchAnimations>
    private lateinit var colourOverlayMapper: ComponentMapper<ColourOverlay>
    private lateinit var typeMapper: ComponentMapper<Type>

    override fun initialize() {
        super.initialize()
        insert(
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
                //Transform
                create(0x20, Aspect.all(Renderable::class, Transform::class), MobTransformMask(transformMapper, typeMapper)),
                //Force Chat
                create(0x2, Aspect.all(Renderable::class, ForceChat::class), ForceChatMask(forceChatMapper)),
                //Face Direction
                create(0x8, Aspect.all(Renderable::class, Facing::class), MobFacingMask(positionMapper, faceMapper)),
                //0x2000 - Weapon hidden/rotation
                //0x10000 - Model change (not sure it actually does anything)
                //Force Movement
                create(0x400, Aspect.all(Renderable::class, Position::class, ForceMovement::class), MobForceMovementMask(positionMapper, forceMovementMapper)),
                //Animation
                create(0x10, Aspect.all(Renderable::class).one(FirstAnimation::class, SecondAnimation::class, ThirdAnimation::class, FourthAnimation::class), MobAnimMask(firstAnimationMapper, secondAnimationMapper, thirdAnimationMapper, fourthAnimationMapper)),
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