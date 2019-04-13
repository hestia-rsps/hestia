package worlds.gregs.hestia.artemis.events

import worlds.gregs.hestia.artemis.InstantEvent

data class CreateBot(val name: String, val x: Int, val y: Int, val plane: Int = 0) : InstantEvent