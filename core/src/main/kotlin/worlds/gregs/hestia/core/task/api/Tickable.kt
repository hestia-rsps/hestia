package worlds.gregs.hestia.core.task.api

interface Tickable : TaskSuspension<Unit> {
    var ticks: Int
}