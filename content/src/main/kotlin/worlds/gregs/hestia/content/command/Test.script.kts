package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Command

on<Command> {
    where { prefix == "test" }
    task {
        onCancel { closeDialogue() }
        val (_, _, content) = event(this)
        val es = world system EventSystem::class
        entity type 101 name "Bob" message "Challenge 1"//14
        entity type 105 name "Bob" message "Challenge 2"
        entity type 106 name "Bob" message "Challenge 3"
        entity type 107 name "Bob" message "Challenge 4"//Clan
        entity type 108 name "Bob" message "Alliance"//50
//        option("This is a really long title which is more than normal.", listOf("one", "two", "three", "four", "five"))
//        option("This is a really long title which is more than normal.", listOf("one", "two", "three", "four"))
//        option("This is a really long title which is more than normal.", listOf("one", "two", "three"))
//        option("This is a really long title which is more than normal.", listOf("one", "two"))
//        player("Hey", 9760)
//        mob("Hello", 1, content.toInt(), "Hans")
//        closeDialogue()
    }
}