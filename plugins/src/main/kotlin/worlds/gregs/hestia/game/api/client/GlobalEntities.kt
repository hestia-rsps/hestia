package worlds.gregs.hestia.game.api.client

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class GlobalEntities : PassiveSystem() {

    abstract fun addPlayers(entityId: Int, players: List<Int>)

    abstract fun addMobs(entityId: Int, mobs: List<Int>)

}