package worlds.gregs.hestia.core.plugins

import worlds.gregs.hestia.core.plugins.queue.systems.TickQueueSystem

setup {
    with(TickQueueSystem())
}