package worlds.gregs.hestia.game.component.update

import com.artemis.Component
import com.artemis.Entity
import com.artemis.annotations.PooledWeaver

@PooledWeaver
class ForceChat : Component() {
    var message: String? = null
}

fun Entity.force(message: String) {
    val chat = ForceChat()
    chat.message = message
    edit().add(chat)
}