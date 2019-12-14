package worlds.gregs.hestia.core.display.widget.api

import com.artemis.Entity
import worlds.gregs.hestia.artemis.SubscriptionSystem
import worlds.gregs.hestia.core.display.widget.model.components.Frame
import worlds.gregs.hestia.artemis.Aspect

abstract class Tabs : SubscriptionSystem(Aspect.all(GameFrame::class, Frame::class)) {
    abstract fun open(entity: Entity, frame: Frame)
}