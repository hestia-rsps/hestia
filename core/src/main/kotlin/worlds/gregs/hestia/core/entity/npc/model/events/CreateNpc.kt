package worlds.gregs.hestia.core.entity.npc.model.events

import worlds.gregs.hestia.artemis.InstantEvent
import worlds.gregs.hestia.core.display.update.model.Direction

data class CreateNpc(val npcId: Int, val x: Int, val y: Int, val plane: Int = 0, val direction: Direction = Direction.NONE) : InstantEvent