package worlds.gregs.hestia.game

import com.google.common.util.concurrent.ThreadFactoryBuilder
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Phaser
import kotlin.system.measureNanoTime

class SomeTask {
    val durationMS = random.nextInt(1000).toLong()

    companion object {
        val random = Random()
    }
}

suspend fun compute(task: SomeTask) {
    delay(task.durationMS)
//    println("done ${task.durationMS}")
}

fun computeNotSuspend(task: SomeTask) {
    Thread.sleep(task.durationMS)
//    println("done ${task.durationMS}")
}

class SyncTask(private val phaser: Phaser, private val task: SomeTask) : Runnable {
    override fun run() {
        Thread.sleep(task.durationMS)
//        println("done ${task.durationMS}")
        phaser.arriveAndDeregister()
    }
}

fun main(args: Array<String>) {
    val n = 100
    val tasks = List(n) { SomeTask() }

    println("Longest: ${tasks.maxBy { it.durationMS }?.durationMS}")

    val timeCoroutine = measureNanoTime {
        runBlocking {
            val deffered = tasks
                    .map { async { compute(it) } }
            deffered.forEach { it.await() }
        }
    }

    println("Coroutine ${timeCoroutine / 1000000} ms")

    val timePar = measureNanoTime {
        tasks.parallelStream().forEach { computeNotSuspend(it) }
    }
    println("Stream parallel ${timePar / 1000000} ms")

    val phaser = Phaser(1)
    val builder = ThreadFactoryBuilder()
    builder.setNameFormat("Synchronizer")
    builder.setPriority(Thread.MAX_PRIORITY)
    val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), builder.build())
    phaser.bulkRegister(tasks.size)
    tasks.forEach {
        executor.submit(SyncTask(phaser, it))
    }

    val total = measureNanoTime {
        phaser.arriveAndAwaitAdvance()
    }
    println("Phaser ${total / 1000000} ms")
}