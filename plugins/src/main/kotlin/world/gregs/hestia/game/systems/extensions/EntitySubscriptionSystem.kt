package world.gregs.hestia.game.systems.extensions

import com.artemis.Aspect
import com.artemis.EntitySystem

abstract class EntitySubscriptionSystem(aspect: Aspect.Builder) : EntitySystem(aspect) {

    override fun checkProcessing(): Boolean {
        return false
    }

    override fun processSystem() {
        //Do nothing!
    }

}