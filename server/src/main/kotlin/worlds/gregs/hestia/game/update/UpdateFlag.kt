package worlds.gregs.hestia.game.update

import com.artemis.EntitySubscription

data class UpdateFlag(val mask: Int, val subscription: EntitySubscription, val unit: UpdateEncoder, val added: Boolean)