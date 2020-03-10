package worlds.gregs.hestia.core.entity.item.container.logic

import arrow.core.andThen
import worlds.gregs.hestia.core.entity.item.container.api.Composition

data class CompositionBuilder(var composition: Composition? = null) {
    fun build() = composition!!
}

operator fun CompositionBuilder.plusAssign(composition: Composition) {
    this.composition = if(this.composition == null) {
        composition
    } else {
        this.composition!! andThen composition
    }
}