package world.gregs.hestia.game

import org.slf4j.LoggerFactory

class TickTask(var running: Boolean, val action: TickTask.() -> Unit) : Runnable {
    var tick = 0

    override fun run() {
        try {
            action.invoke(this)
            tick++
        } catch (t: Throwable) {
            logger.error("Tick task", t)
            stop()
        }
    }

    fun stop() {
        running = false
    }

    companion object {
        private val logger = LoggerFactory.getLogger(TickTask::class.java)
    }
}