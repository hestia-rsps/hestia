package world.gregs.hestia.game.systems

import com.artemis.ComponentMapper
import com.artemis.systems.IteratingSystem
import world.gregs.hestia.game.component.map.Position
import world.gregs.hestia.game.component.map.ViewDistance
import world.gregs.hestia.game.component.map.Viewport
import world.gregs.hestia.services.Aspect

class ViewDistanceSystem : IteratingSystem(Aspect.all(Viewport::class, ViewDistance::class)) {

    private lateinit var positionMapper: ComponentMapper<Position>
    private lateinit var viewDistanceMapper: ComponentMapper<ViewDistance>
    private lateinit var viewportMapper: ComponentMapper<Viewport>

    override fun process(entityId: Int) {
        val viewDistance = viewDistanceMapper.get(entityId)
        val viewport = viewportMapper.get(entityId)

        //Players
        val locals = viewport.localPlayers().size

        //If there's too many local players, decrease the view distance
        if(locals > MAXIMUM_LOCAL_PLAYERS) {
            //Cap minimum view distance
            if(viewDistance.distance > MIN_VIEW_DISTANCE) {
                viewDistance.distance--
            }
        } else if(viewDistance.distance < MAX_VIEW_DISTANCE) {//If view distance has been decreased
            if(locals < MAXIMUM_LOCAL_PLAYERS) {//If there is room for more local players
                val position = positionMapper.get(entityId)
                //Calculate how many global players would be added from increasing view distance
                //This check prevents players from "flashing" in/out of view and keeps viewports square
                val globals = viewport.globalPlayers().count { position.withinDistance(positionMapper.get(entityId), viewDistance.distance + 1) }
                //If that total is less than max, then increase the view distance
                if (locals + globals < MAXIMUM_LOCAL_PLAYERS) {
                    viewDistance.distance++
                }
            }
        }


        //Mobs
        /*val mobs = viewport.localMobs().size
        if(mobs > MAXIMUM_MOBS) {
            //Cap minimum view distance
            if(viewDistance.distance > MIN_VIEW_DISTANCE) {
                viewDistance.distance--
            }
        }*/
    }

    companion object {
        const val DEFAULT_VIEW_DISTANCE = 15
        const val MAXIMUM_LOCAL_PLAYERS = 255
        private const val MAX_VIEW_DISTANCE = DEFAULT_VIEW_DISTANCE
        private const val MIN_VIEW_DISTANCE = 1
        const val MAXIMUM_MOBS = 255
    }
}