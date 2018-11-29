package worlds.gregs.hestia.game.plugins.client.systems.update.update.flag

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.plugins.client.components.update.list.GlobalPlayers
import worlds.gregs.hestia.game.plugins.client.components.update.stage.EntityUpdates
import worlds.gregs.hestia.game.plugins.client.systems.update.bases.flag.BaseUpdateFlagSystem
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
import worlds.gregs.hestia.game.plugins.movement.components.types.Run
import worlds.gregs.hestia.game.plugins.movement.components.types.Walk
import worlds.gregs.hestia.network.update.player.*
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.exclude
import worlds.gregs.hestia.services.one

class PlayerUpdateFlagSystem : BaseUpdateFlagSystem(GlobalPlayers::class) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>
    private lateinit var entityUpdatesMapper: ComponentMapper<EntityUpdates>
    private lateinit var globalPlayersMapper: ComponentMapper<GlobalPlayers>

    override fun process(entityId: Int) {
        val updates = entityUpdatesMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)
        check(updates, viewport.localPlayers())
        check(entityId, updates, globalPlayersMapper)
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
    private lateinit var batchAnimationsMapper: ComponentMapper<BatchAnimations>
    private lateinit var colourOverlayMapper: ComponentMapper<ColourOverlay>
    private lateinit var timeBarMapper: ComponentMapper<TimeBar>

    override fun initialize() {
        super.initialize()
        insert(
                //Batch animation masks
                create(0x8000, Aspect.all(Renderable::class, BatchAnimations::class), PlayerBatchAnimationMask(batchAnimationsMapper)),
                //Animation
                create(0x40, Aspect.all(Renderable::class).one(FirstAnimation::class, SecondAnimation::class, ThirdAnimation::class, FourthAnimation::class), PlayerAnimMask(firstAnimationMapper, secondAnimationMapper, thirdAnimationMapper, fourthAnimationMapper), true),
                //Third Graphic
                create(0x40000, Aspect.all(Renderable::class, ThirdGraphic::class), PlayerGraphicMask(thirdGraphicMapper)),
                //Colour overlay
                create(0x20000, Aspect.all(Renderable::class, ColourOverlay::class), PlayerColourOverlayMask(colourOverlayMapper)),
                //Time Bar
                create(0x2000, Aspect.all(Renderable::class, TimeBar::class), PlayerTimeBarMask(timeBarMapper)),
                //Fourth Graphic
                create(0x80000, Aspect.all(Renderable::class, FourthGraphic::class), PlayerGraphicMask(fourthGraphicMapper)),
                //Hits
                create(0x4, Aspect.all(Renderable::class, Damage::class), HitsMask(damageMapper, false)),
                //Force Chat
                create(0x4000, Aspect.all(Renderable::class, ForceChat::class), ForceChatMask(forceChatMapper)),
                //Watch Entity
                create(0x10, Aspect.all(Renderable::class, Watching::class), PlayerWatchEntityMask(watchingMapper)),
                //Force Movement
                create(0x1000, Aspect.all(Renderable::class, Position::class, ForceMovement::class), PlayerForceMovementMask(positionMapper, forceMovementMapper)),
                //Face Direction
                create(0x20, Aspect.all(Renderable::class, Facing::class).exclude(Run::class, Walk::class), PlayerFacingMask(faceMapper), true),
                //First Graphic
                create(0x2, Aspect.all(Renderable::class, FirstGraphic::class), PlayerGraphicMask(firstGraphicMapper)),
                //Second Graphic
                create(0x100, Aspect.all(Renderable::class, SecondGraphic::class), PlayerGraphicMask(secondGraphicMapper))
        )
    }
}