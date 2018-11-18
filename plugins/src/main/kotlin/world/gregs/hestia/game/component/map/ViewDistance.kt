package world.gregs.hestia.game.component.map

import com.artemis.Component
import world.gregs.hestia.game.systems.ViewDistanceSystem

class ViewDistance : Component() {
    var distance = ViewDistanceSystem.DEFAULT_VIEW_DISTANCE
}