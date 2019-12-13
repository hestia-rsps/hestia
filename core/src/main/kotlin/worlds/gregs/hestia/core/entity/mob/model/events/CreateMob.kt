package worlds.gregs.hestia.core.entity.mob.model.events

import worlds.gregs.hestia.artemis.InstantEvent

data class CreateMob(val mobId: Int, val x: Int, val y: Int, val plane: Int = 0) : InstantEvent