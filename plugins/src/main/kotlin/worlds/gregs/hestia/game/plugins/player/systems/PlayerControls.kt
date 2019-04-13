package worlds.gregs.hestia.game.plugins.player.systems

import com.artemis.Entity
import worlds.gregs.hestia.api.client.update.components.UpdateClanMember

class PlayerControls
/**
 * Player clan chat
 */
fun Entity.updateClanChat(sameClan: Boolean) {
    edit().add(UpdateClanMember(sameClan))
}