package worlds.gregs.hestia.core.display.update.model.components

import com.artemis.Component
import com.artemis.annotations.PooledWeaver
import worlds.gregs.hestia.game.update.blocks.Marker
import java.util.concurrent.ConcurrentLinkedQueue

@PooledWeaver
data class Damage(var hits: ConcurrentLinkedQueue<Marker> = ConcurrentLinkedQueue()) : Component()