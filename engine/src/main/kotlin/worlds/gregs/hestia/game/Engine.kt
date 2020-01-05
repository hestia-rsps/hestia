package worlds.gregs.hestia.game

import org.slf4j.LoggerFactory

abstract class Engine : Thread("Game Engine") {
    var isRunning: Boolean = false
    var currentTime = 0L
    var sleepTime = 0L

    init {
        priority = MAX_PRIORITY
    }

    override fun start() {
        super.start()
        isRunning = true
    }

    abstract fun tick(time: Long, delta: Float)

    override fun run() {
        while (isRunning) {
            currentTime = System.currentTimeMillis()
            try {
                tick(currentTime, (currentTime - LAST_CYCLE_CTM).toFloat())
                ticks++
            } catch (e: Throwable) {
                logger.error(name, e)
            }

            LAST_CYCLE_CTM = System.currentTimeMillis()
            sleepTime = WORLD_CYCLE_TIME + currentTime - LAST_CYCLE_CTM

            if (sleepTime <= 0) {
                continue
            }

            try {
                sleep(sleepTime)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        var ticks: Int = 0
            private set
        private var LAST_CYCLE_CTM: Long = 0
        private const val WORLD_CYCLE_TIME = 600
        private val logger = LoggerFactory.getLogger(Engine::class.java)
    }
}