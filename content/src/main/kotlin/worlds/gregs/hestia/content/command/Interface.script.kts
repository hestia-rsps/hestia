package worlds.gregs.hestia.content.command

import com.artemis.ComponentMapper
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.widget.logic.systems.screen.CustomScreenWidgetSystem
import worlds.gregs.hestia.core.display.widget.model.components.screen.CustomScreenWidget

lateinit var customScreenWidgetMapper: ComponentMapper<CustomScreenWidget>
lateinit var customScreenWidgetSystem: CustomScreenWidgetSystem

on<Command> {
    where { prefix == "inter" }
    then { (entityId, _, content) ->
        val id = content.toInt()
        val component = customScreenWidgetMapper.get(entityId)
        if(component == null) {
            customScreenWidgetMapper.create(entityId).id = id
        } else {
            if(id == -1) {
                customScreenWidgetMapper.remove(entityId)
            } else {
                component.id = id
                customScreenWidgetSystem.open(entityId)
            }
        }
        isCancelled = true
    }
}