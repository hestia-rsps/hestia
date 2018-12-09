package worlds.gregs.hestia.game.plugins.player.systems

import com.artemis.Entity
import worlds.gregs.hestia.game.plugins.player.component.update.UpdateClanMember

class PlayerControls
/**
 * Player clan chat
 */
fun Entity.updateClanChat(sameClan: Boolean) {
    edit().add(UpdateClanMember(sameClan))
}