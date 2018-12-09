package worlds.gregs.hestia.game.plugins.entity.systems.sync

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import worlds.gregs.hestia.game.plugins.core.components.Renderable
import worlds.gregs.hestia.game.plugins.entity.components.update.*
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.FirstAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.FourthAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.SecondAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.anim.ThirdAnimation
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Facing
import worlds.gregs.hestia.game.plugins.entity.components.update.direction.Watching
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.FirstGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.FourthGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.SecondGraphic
import worlds.gregs.hestia.game.plugins.entity.components.update.gfx.ThirdGraphic
import worlds.gregs.hestia.game.plugins.movement.components.types.Moving
import worlds.gregs.hestia.game.plugins.movement.components.types.RunStep
import worlds.gregs.hestia.game.plugins.movement.components.types.WalkStep
import worlds.gregs.hestia.services.Aspect

class PostEntitySyncSystem : IteratingSystem(Aspect.all(Renderable::class)) {

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
    private lateinit var forceMovementMapper: ComponentMapper<ForceMovement>
    private lateinit var facingMapper: ComponentMapper<Facing>
    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var walkMapper: ComponentMapper<WalkStep>
    private lateinit var runMapper: ComponentMapper<RunStep>
    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var batchAnimationsMapper: ComponentMapper<BatchAnimations>
    private lateinit var colourOverlayMapper: ComponentMapper<ColourOverlay>
    private lateinit var timeBarMapper: ComponentMapper<TimeBar>
    private lateinit var watchingMapper: ComponentMapper<Watching>

    override fun process(entityId: Int) {
        firstAnimationMapper.remove(entityId)
        secondAnimationMapper.remove(entityId)
        thirdAnimationMapper.remove(entityId)
        fourthAnimationMapper.remove(entityId)
        firstGraphicMapper.remove(entityId)
        secondGraphicMapper.remove(entityId)
        thirdGraphicMapper.remove(entityId)
        fourthGraphicMapper.remove(entityId)
        damageMapper.remove(entityId)
        forceChatMapper.remove(entityId)
        forceMovementMapper.remove(entityId)
        facingMapper.remove(entityId)
        transformMapper.remove(entityId)
        walkMapper.remove(entityId)
        runMapper.remove(entityId)
        movingMapper.remove(entityId)
        batchAnimationsMapper.remove(entityId)
        colourOverlayMapper.remove(entityId)
        timeBarMapper.remove(entityId)
        watchingMapper.remove(entityId)
    }
}