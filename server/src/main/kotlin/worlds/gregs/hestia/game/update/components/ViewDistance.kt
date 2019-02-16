package worlds.gregs.hestia.game.update.components

import com.artemis.Component
import worlds.gregs.hestia.game.update.sync.SyncFactory

class ViewDistance : Component() {
    var distance = SyncFactory.DEFAULT_VIEW_DISTANCE
}