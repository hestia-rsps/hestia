package worlds.gregs.hestia.game.plugins.entity.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.api.client.update.components.Animation
import worlds.gregs.hestia.artemis.events.Animate
import worlds.gregs.hestia.api.client.update.components.anim.FirstAnimation
import worlds.gregs.hestia.api.client.update.components.anim.FourthAnimation
import worlds.gregs.hestia.api.client.update.components.anim.SecondAnimation
import worlds.gregs.hestia.api.client.update.components.anim.ThirdAnimation

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