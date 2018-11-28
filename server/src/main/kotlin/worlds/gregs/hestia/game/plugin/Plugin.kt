package worlds.gregs.hestia.game.plugin

import com.artemis.ArtemisPlugin
import com.artemis.WorldConfigurationBuilder

interface Plugin : ArtemisPlugin {

    companion object {
        /*
        Highest first
        Lowest last
         */
        const val MOBILE_PRIORITY = 10000
        const val MOVE_PRIORITY = 9900
        const val NAVIGATION_PRIORITY = 9800
        const val WALK_PRIORITY = 9700
        const val RUN_PRIORITY = 9600
        const val SHIFT_PRIORITY = 9500

        const val PLAYER_INDEX_PRIORITY = WorldConfigurationBuilder.Priority.HIGHEST
        const val ENTITY_INDEX_PRIORITY = WorldConfigurationBuilder.Priority.HIGH
        const val VIEWPORT_PRIORITY = 9400
        const val LOGIN_DETAILS_PRIORITY = 9300
        const val MAP_REGION_PRIORITY = 9200
        const val INTERFACE_PRIORITY = 9100

        const val PRE_SYNC_PRIORITY = 9000

        const val UPDATE_GLOBAL_ENTITY_PRIORITY = 8900
        const val UPDATE_FLAG_PRIORITY = 8800
        const val UPDATE_DISPLAY_FLAG_PRIORITY = 8700
        const val UPDATE_DISPLAY_CHANGE_PRIORITY = 8600
        const val UPDATE_SYNC_PRIORITY = 8500
        const val UPDATE_UPDATE_PRIORITY = 8400
        const val UPDATE_CHANGE_PRIORITY = 8300
        const val UPDATE_FINISH_PRIORITY = 8200

        const val POST_UPDATE_PRIORITY = 8000
    }
}