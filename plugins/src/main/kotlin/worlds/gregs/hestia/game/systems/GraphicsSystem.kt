package worlds.gregs.hestia.game.systems

import com.artemis.ComponentMapper
import worlds.gregs.hestia.game.events.Graphic
import net.mostlyoriginal.api.event.common.Subscribe
import net.mostlyoriginal.api.system.core.PassiveSystem
import worlds.gregs.hestia.game.component.update.gfx.*

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