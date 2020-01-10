package worlds.gregs.hestia.core.action.logic

import com.artemis.World
import net.mostlyoriginal.api.event.common.Event
import worlds.gregs.hestia.core.action.model.Action

data class ActionContext(val world: World, val entity: Int, val action: Action) : Event