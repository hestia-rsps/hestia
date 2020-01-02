package worlds.gregs.hestia.core.entity.item.container.model

import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount

data class Item(val type: Int, var amount: Int) {

    constructor(type: Type, amount: Amount) : this(type.id, amount.amount)

}