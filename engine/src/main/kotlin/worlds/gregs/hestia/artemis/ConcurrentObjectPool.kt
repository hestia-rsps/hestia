package worlds.gregs.hestia.artemis

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger

class ConcurrentObjectPool<T>(private val type: Class<T>?, private val initialPoolSize: Int = 0, private val maximumPoolSize: Int = -1) {

    private val totalObjectsCount = AtomicInteger()
    private val pooledObjectsCount = AtomicInteger()
    private val queue = ConcurrentLinkedQueue<T>()
    private val waitSync = Object()

    init {
        if (initialPoolSize < 0) {
            throw IllegalArgumentException("initialPoolSize")
        }

        if (maximumPoolSize < -1 || maximumPoolSize != -1 && initialPoolSize > maximumPoolSize) {
            throw IllegalArgumentException("maximumPoolSize")
        }

        if (type != null) {
            try {
                type.getConstructor()
            } catch (e: NoSuchMethodException) {
                throw IllegalArgumentException("type", e)
            }

        } else {
            if (javaClass == ConcurrentObjectPool::class.java) {
                throw IllegalArgumentException("type")
            }
        }
        if (type != null) {
            initialize()
        }
    }

    fun getPooledObjectsCount(): Int {
        return pooledObjectsCount.get()
    }

    fun getTotalObjectsCount(): Int {
        return totalObjectsCount.get()
    }

    private fun initialize() {
        val itemList = LinkedList<T>()
        for (i in 0 until initialPoolSize) {
            try {
                itemList.add(obtain())
            } catch (e: InterruptedException) {
            }
        }

        for (item in itemList) {
            free(item)
        }
    }

    private fun create(): T {
        return if (type != null) {
            try {
                type.getConstructor().newInstance()
            } catch (e: InstantiationException) {
                throw UnsupportedOperationException(e)
            } catch (e: IllegalAccessException) {
                throw UnsupportedOperationException(e)
            }

        } else {
            throw UnsupportedOperationException()
        }
    }

    @Throws(InterruptedException::class)
    fun obtain(): T {
        var `object`: T = queue.poll()
        while (`object` == null) {
            if (maximumPoolSize <= 0 || this.totalObjectsCount.get() < maximumPoolSize) {
                var success = false
                try {
                    val totalObjectsCount = this.totalObjectsCount.incrementAndGet()
                    if (maximumPoolSize <= 0 || totalObjectsCount <= maximumPoolSize) {
                        `object` = create()
                        success = true
                        return `object`
                    }
                } finally {
                    if (!success) {
                        this.totalObjectsCount.decrementAndGet()
                        // Notify other sleeping takers, so that one of them can continue creating new object.
                        synchronized(waitSync) {
                            waitSync.notify()
                        }
                    }
                }
            }
            synchronized(waitSync) {
                if (this.pooledObjectsCount.get() <= 0) {
                    waitSync.wait()
                }
            }
            `object` = queue.poll()
        }
        this.pooledObjectsCount.decrementAndGet()
        return `object`
    }

    fun free(e: T) {
        queue.add(e)
        // Notify consumer when first object arrives
        if (this.pooledObjectsCount.incrementAndGet() <= 1) {
            synchronized(waitSync) {
                waitSync.notifyAll()
            }
        }
    }

    override fun toString(): String {
        return "PooledObjectsCount : ${getPooledObjectsCount()}, TotalObjectsCount : ${getTotalObjectsCount()}, MaxPoolSize : $maximumPoolSize"
    }
}