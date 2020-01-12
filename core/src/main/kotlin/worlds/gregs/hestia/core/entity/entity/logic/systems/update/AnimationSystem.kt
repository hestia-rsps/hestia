package worlds.gregs.hestia.core.entity.entity.logic.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.update.model.components.ActiveAnimation
import worlds.gregs.hestia.core.display.update.model.components.anim.FirstAnimation
import worlds.gregs.hestia.core.display.update.model.components.anim.FourthAnimation
import worlds.gregs.hestia.core.display.update.model.components.anim.SecondAnimation
import worlds.gregs.hestia.core.display.update.model.components.anim.ThirdAnimation
import worlds.gregs.hestia.core.entity.entity.model.events.Animation
import worlds.gregs.hestia.service.cache.definition.systems.AnimationDefinitionSystem
import kotlin.math.roundToInt

class AnimationSystem : PassiveSystem() {
    private lateinit var firstAnimationMapper: ComponentMapper<FirstAnimation>
    private lateinit var secondAnimationMapper: ComponentMapper<SecondAnimation>
    private lateinit var thirdAnimationMapper: ComponentMapper<ThirdAnimation>
    private lateinit var fourthAnimationMapper: ComponentMapper<FourthAnimation>

    private lateinit var maps: List<ComponentMapper<out ActiveAnimation>>
    private lateinit var definitions: AnimationDefinitionSystem

    override fun initialize() {
        super.initialize()
        maps = listOf(firstAnimationMapper, secondAnimationMapper, thirdAnimationMapper, fourthAnimationMapper)
    }

    @Subscribe
    fun inserted(event: Animation) {
        val entityId = event.entity
        //Check for existing animations
        maps.asSequence().forEach {
            if (!it.has(entityId)) {
                //Create new animation
                it.create(entityId).apply {
                    this.id = event.id
                    this.speed = event.speed
                }
                //Calculate time
                val definition = definitions.get(event.id)
                val time = definition.getTime()
                event.ticks = (time / 600.0).roundToInt()
                return
            }
        }
    }
}