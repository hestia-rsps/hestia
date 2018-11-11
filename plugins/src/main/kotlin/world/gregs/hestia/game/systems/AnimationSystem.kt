package world.gregs.hestia.game.systems

import com.artemis.ComponentMapper
import world.gregs.hestia.game.events.Animate
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import world.gregs.hestia.game.component.update.anim.*

class AnimationSystem : PassiveSystem() {
    private lateinit var firstAnimationMapper: ComponentMapper<FirstAnimation>
    private lateinit var secondAnimationMapper: ComponentMapper<SecondAnimation>
    private lateinit var thirdAnimationMapper: ComponentMapper<ThirdAnimation>
    private lateinit var fourthAnimationMapper: ComponentMapper<FourthAnimation>

    private lateinit var maps: List<ComponentMapper<out Animation>>

    override fun initialize() {
        super.initialize()
        maps = listOf(firstAnimationMapper, secondAnimationMapper, thirdAnimationMapper, fourthAnimationMapper)
    }

    @Subscribe
    fun inserted(event: Animate) {
        val entityId = event.entityId
        //Check for existing animations
        maps.asSequence().forEach {
            if(!it.has(entityId)) {
                //Create new animation
                it.create(entityId).apply {
                    this.id = event.id
                    this.speed = event.speed
                }
                return
            }
        }
    }
}