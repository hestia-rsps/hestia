package worlds.gregs.hestia.game.events

import net.mostlyoriginal.api.event.common.Event
import world.gregs.hestia.core.network.Session

data class CreatePlayer(val session: Session, val name: String, val displayMode: Int, val screenWidth: Int, val screenHeight: Int) : Event