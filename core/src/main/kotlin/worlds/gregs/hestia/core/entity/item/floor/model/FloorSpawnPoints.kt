package worlds.gregs.hestia.core.entity.item.floor.model

import worlds.gregs.hestia.core.entity.item.container.model.Items

enum class FloorSpawnPoints(val type: Int, val amount: Int, val delay: Int, val x: Int, val y: Int, val plane: Int = 0) {
    COINS(Items.COINS, 15, 5, 3088, 3497)
}