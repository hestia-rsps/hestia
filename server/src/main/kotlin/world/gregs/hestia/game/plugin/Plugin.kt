package world.gregs.hestia.game.plugin

import com.artemis.ArtemisPlugin

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

        const val PRE_SYNC_PRIORITY = 9100

        const val UPDATE_PRIORITY = 9000
        const val SYNC_PRIORITY = 8900
        const val MOB_UPDATE_PRIORITY = 8800
        const val MOB_SYNC_PRIORITY = 8700
        const val POST_SYNC_PRIORITY = 8600

    }
}