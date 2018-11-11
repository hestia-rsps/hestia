package world.gregs.hestia.game.systems.sync

import com.artemis.ComponentMapper
import com.artemis.EntitySubscription
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.*
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.game.component.update.anim.FirstAnimation
import world.gregs.hestia.game.component.update.anim.FourthAnimation
import world.gregs.hestia.game.component.update.anim.SecondAnimation
import world.gregs.hestia.game.component.update.anim.ThirdAnimation
import world.gregs.hestia.game.component.update.direction.Facing
import world.gregs.hestia.game.component.update.gfx.FirstGraphic
import world.gregs.hestia.game.component.update.gfx.FourthGraphic
import world.gregs.hestia.game.component.update.gfx.SecondGraphic
import world.gregs.hestia.game.component.update.gfx.ThirdGraphic
import world.gregs.hestia.game.component.movement.*
import world.gregs.hestia.game.component.update.*
import world.gregs.hestia.services.Aspect
import world.gregs.hestia.game.component.entity.Player

class PostSyncSystem : IteratingSystem(Aspect.all(Renderable::class)) {

    private lateinit var viewportMapper: ComponentMapper<Viewport>

    //Flags
    private lateinit var appearanceMapper: ComponentMapper<Appearance>
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
    private lateinit var updateMovementMapper: ComponentMapper<UpdateMovement>
    private lateinit var transformMapper: ComponentMapper<Transform>
    private lateinit var updateDisplayNameMapper: ComponentMapper<UpdateDisplayName>
    private lateinit var updateCombatLevelMapper: ComponentMapper<UpdateCombatLevel>
    private lateinit var walkMapper: ComponentMapper<Walk>
    private lateinit var runMapper: ComponentMapper<Run>
    private lateinit var walkingMapper: ComponentMapper<Walking>
    private lateinit var movingMapper: ComponentMapper<Moving>
    private lateinit var playerSubscription: EntitySubscription

    override fun initialize() {
        super.initialize()
        playerSubscription = world.aspectSubscriptionManager.get(Aspect.all(Player::class))
    }

    override fun process(entityId: Int) {
        //Update global/local entities
        viewportMapper.get(entityId)?.update(playerSubscription.entities)
        viewportMapper.get(entityId)?.localMobs()?.clear()

        //Flags
        appearanceMapper.remove(entityId)
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
        updateMovementMapper.remove(entityId)
        transformMapper.remove(entityId)
        updateDisplayNameMapper.remove(entityId)
        updateCombatLevelMapper.remove(entityId)
        walkMapper.remove(entityId)
        runMapper.remove(entityId)
        walkingMapper.remove(entityId)
        movingMapper.remove(entityId)
    }
}