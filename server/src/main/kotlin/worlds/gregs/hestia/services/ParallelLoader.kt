package worlds.gregs.hestia.services

import world.gregs.hestia.core.network.server.threads.LoaderThreadFactory
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ParallelLoader(private vararg val tasks: Runnable) {
    private val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), LoaderThreadFactory())

    fun block() {
        val futures = tasks.map { executor.submit(it) }.toList()

        executor.shutdown()
        executor.awaitTermination(15, TimeUnit.SECONDS)

        futures.forEach {
            try {
                it.get()
            } catch (cause: ExecutionException) {
                cause.printStackTrace()
            }
        }
    }
}