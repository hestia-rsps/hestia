package worlds.gregs.hestia.content

import com.artemis.ComponentMapper
import net.mostlyoriginal.api.event.common.Event
import worlds.gregs.hestia.artemis.events.ButtonClick
import worlds.gregs.hestia.game.entity.components.Position

data class FakeEvent(val option: String, val name: String) : Event

fun option(option: String, name: String): (FakeEvent) -> Boolean {
    return { it.option == option && it.name == name}
}

fun test(string: String, block: () -> Unit) {

}

lateinit var positionMapper: ComponentMapper<Position>

system {

    initialize {
    }

    aspect all Position::class all Position::class one Position::class exclude Position::class

    on<FakeEvent> {
        conditional = { it.option == "Bank" && it.name == "Banker" }
        action = {
            println("Do something")
        }
    }

    on<FakeEvent> {
        where { it.option == "Talk-to" && it.name == "Banker" }
        then {
            println("Do something")
        }
    }

    on<ButtonClick> {
        then {
            println("Do something")
        }
    }

    subscribe(aspect = all(Position::class).one(Position::class)) {
        insert = {
            println("Here too")
        }
    }

    subscribe {
        insert {
        }
        aspect all Position::class
    }

    dispose {
        dialogue {
        }
    }
    /*subscribe {
        insert {
        }

        remove {

        }
    }*/
}
