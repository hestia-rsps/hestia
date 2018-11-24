package worlds.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class BatchAnimations : Component()

fun Entity.batchAnim() {
    edit().add(BatchAnimations())
}