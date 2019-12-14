package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.task.model.context.event

lateinit var es: EventSystem

on<Command> {
    where { prefix == "anim" }
    task {//863
        event(this) {
            entity animate content.toInt()
            isCancelled = true
        }
    }
}

on<Command> {
    where { prefix == "gfx" || prefix == "graphic" }
    task {//93
        event(this) {
            entity graphic content.toInt()
            isCancelled = true
        }
    }
}

on<Command> {
    where { prefix == "transform" || prefix == "pnpc" }
    task {
        event(this) {
            val id = content.toInt()
            if(id < 0) {
                entity transform id
            }
            entity.updateAppearance()
            isCancelled = true
        }
    }
}

on<Command> {
    where { prefix == "hit" }
    task {
        event(this) {
            repeat(5) {
                entity hit 10
            }
            isCancelled = true
        }
    }
}