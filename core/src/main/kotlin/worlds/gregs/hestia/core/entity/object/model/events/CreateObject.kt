package worlds.gregs.hestia.core.entity.`object`.model.events

import worlds.gregs.hestia.artemis.InstantEvent

data class CreateObject(val objectId: Int, val x: Int, val y: Int, val plane: Int = 0, val type: Int, val rotation: Int) : InstantEvent