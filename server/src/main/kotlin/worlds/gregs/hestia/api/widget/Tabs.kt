package worlds.gregs.hestia.api.widget

import com.artemis.Entity
import worlds.gregs.hestia.api.SubscriptionSystem
import worlds.gregs.hestia.api.widget.components.Frame
import worlds.gregs.hestia.services.Aspect

abstract class Tabs : SubscriptionSystem(Aspect.all(GameFrame::class, Frame::class)) {
    abstract fun open(entity: Entity, frame: Frame)
}