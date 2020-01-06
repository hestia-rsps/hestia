package worlds.gregs.hestia.core.action

import com.artemis.World
import net.mostlyoriginal.api.event.common.Event

data class ActionContext(val world: World, val entity: Int, val action: Action) : Event