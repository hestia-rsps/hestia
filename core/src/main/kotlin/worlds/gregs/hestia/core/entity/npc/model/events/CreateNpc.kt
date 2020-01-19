package worlds.gregs.hestia.core.entity.npc.model.events

import worlds.gregs.hestia.artemis.InstantEvent

data class CreateNpc(val npcId: Int, val x: Int, val y: Int, val plane: Int = 0) : InstantEvent