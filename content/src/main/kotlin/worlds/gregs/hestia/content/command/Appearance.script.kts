package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.update.model.components.Transform
import worlds.gregs.hestia.core.entity.entity.model.events.Animation
import worlds.gregs.hestia.core.entity.entity.model.events.Graphic
import worlds.gregs.hestia.core.entity.entity.model.events.Hit
import worlds.gregs.hestia.core.entity.player.model.events.UpdateAppearance
import worlds.gregs.hestia.game.update.blocks.Marker

on<Command> {
    where { prefix == "anim" }
    then {
        entity perform Animation(content.toInt())//863
        isCancelled = true
    }
}

on<Command> {
    where { prefix == "gfx" || prefix == "graphic" }
    then {
        entity perform Graphic(content.toInt())//93
        isCancelled = true
    }
}

on<Command> {
    where { prefix == "transform" || prefix == "pnpc" || prefix == "morph" }
    then {
        val id = content.toInt()
        if (id > 0) {
            (entity create Transform::class).mobId = id
        }
        entity perform UpdateAppearance()
        isCancelled = true
    }
}

on<Command> {
    where { prefix == "hit" }
    then {
        repeat(5) {
            entity perform Hit(10, Marker.MELEE)
        }
        isCancelled = true
    }
}