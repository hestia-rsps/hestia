package worlds.gregs.hestia.game.plugins.dialogue.systems.dialogues

import worlds.gregs.hestia.game.dialogue.DialogueName
import worlds.gregs.hestia.game.dialogue.DialogueScript
import worlds.gregs.hestia.game.dialogue.Dialogues
import worlds.gregs.hestia.game.dialogue.redirectId
import worlds.gregs.hestia.game.plugins.dialogue.systems.types.*

@DialogueName("Man")
class ManDialogue : DialogueScript {

    override val dialogue: Dialogues.() -> Unit = {
        mob("This is a mob statement.") id 0 anim 9878
        player("Hi")
        mob("Hello") id 0 title "Not a noob"
        mob("I like to think that sometimes I'm lots of lines") title "Noob" id 0
        options("Choose at will") {
            option("First choice") {
                mob("First") id 100
                player("Very well then")
                action {
                    println("Give item")
                }
                item("The mob gives you an item") id 11694
            }

            option("Second choice") {
                mob("Second") id 100
                redirect(0)
            }
            option("Third choice") {
                mob("Second") id 100
                redirect(0)
            }
            option("Fourth choice") {
                mob("Second") id 100
                redirect(0)
            }
            option("Five choice") {
                mob("Second") id 100
                redirect(0)
            }
        } redirectId 0
    }
}