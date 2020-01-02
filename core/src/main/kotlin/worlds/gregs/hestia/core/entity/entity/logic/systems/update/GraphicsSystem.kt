package worlds.gregs.hestia.core.entity.entity.logic.systems.update

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.core.display.update.model.components.Graphics
import worlds.gregs.hestia.core.display.update.model.components.gfx.FirstGraphic
import worlds.gregs.hestia.core.display.update.model.components.gfx.FourthGraphic
import worlds.gregs.hestia.core.display.update.model.components.gfx.SecondGraphic
import worlds.gregs.hestia.core.display.update.model.components.gfx.ThirdGraphic
import worlds.gregs.hestia.core.entity.entity.model.events.Graphic
import worlds.gregs.hestia.core.task.api.Task
import worlds.gregs.hestia.core.task.api.dispatch
import worlds.gregs.hestia.core.task.api.entity

class GraphicsSystem : PassiveSystem() {
    private lateinit var firstGraphicMapper: ComponentMapper<FirstGraphic>
    private lateinit var secondGraphicMapper: ComponentMapper<SecondGraphic>
    private lateinit var thirdGraphicMapper: ComponentMapper<ThirdGraphic>
    private lateinit var fourthGraphicMapper: ComponentMapper<FourthGraphic>

    private lateinit var maps: List<ComponentMapper<out Graphics>>

    override fun initialize() {
        super.initialize()
        maps = listOf(firstGraphicMapper, secondGraphicMapper, thirdGraphicMapper, fourthGraphicMapper)
    }

    @Subscribe
    fun inserted(event: Graphic) {
        val entityId = event.entityId
        //Check for existing graphics
        maps.asSequence().forEach {
            if(!it.has(entityId)) {
                //Create new graphic
                it.create(entityId).apply {
                    this.id = event.id
                    this.delay = event.delay
                    this.height = event.height
                    this.rotation = event.rotation
                    this.forceRefresh = event.forceRefresh
                }
                return
            }
        }
    }
}

fun Task.graphic(id: Int, delay: Int = 0, height: Int = 0, rotation: Int = 0, forceRefresh: Boolean = false) {
    dispatch(Graphic(entity, id, delay, height, rotation, forceRefresh))
}