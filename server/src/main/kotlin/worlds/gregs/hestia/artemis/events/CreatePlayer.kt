package worlds.gregs.hestia.artemis.events

import world.gregs.hestia.core.network.Session
import worlds.gregs.hestia.artemis.InstantEvent

data class CreatePlayer(val session: Session, val name: String, val displayMode: Int, val screenWidth: Int, val screenHeight: Int) : InstantEvent