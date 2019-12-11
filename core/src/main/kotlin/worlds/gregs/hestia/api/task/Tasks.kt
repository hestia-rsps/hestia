package worlds.gregs.hestia.api.task

import net.mostlyoriginal.api.system.core.PassiveSystem

abstract class Tasks : PassiveSystem() {
    /**
     * Resumes the current task for [entityId]
     * @param entityId The entities who's task to resume
     */
    abstract fun resume(entityId: Int)

    /**
     * Clears the queue for [entityId]
     * @param entityId The entities who's tasks to clear
     */
    abstract fun clear(entityId: Int)
}