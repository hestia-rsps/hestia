package worlds.gregs.hestia.core.entity.item.floor.model.events

import worlds.gregs.hestia.artemis.InstantEvent

data class CreateFloorItemSpawn(val id: Int, val amount: Int, val x: Int, val y: Int, val plane: Int, val delay: Int) : InstantEvent