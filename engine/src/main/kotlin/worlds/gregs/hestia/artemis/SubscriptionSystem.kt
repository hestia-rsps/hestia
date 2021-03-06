package worlds.gregs.hestia.artemis

import com.artemis.Aspect
import com.artemis.BaseEntitySystem

abstract class SubscriptionSystem(aspect: Aspect.Builder) : BaseEntitySystem(aspect) {

    override fun checkProcessing(): Boolean {
        return false
    }

    override fun processSystem() {
        //Do nothing!
    }

}