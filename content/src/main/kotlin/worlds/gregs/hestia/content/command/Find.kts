package worlds.gregs.hestia.content.command

import worlds.gregs.hestia.core.display.client.model.events.Chat
import worlds.gregs.hestia.core.display.client.model.events.Command
import worlds.gregs.hestia.core.display.dialogue.model.ChatType.Console
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

lateinit var definitions: ItemDefinitionSystem

on<Command> {
    where { prefix == "find" }
    then {
        val id = content.toIntOrNull()
        if(id == null) {
            val name = content.toLowerCase()
            var found = false
            for(id in definitions.reader.size downTo 0) {
                val def = definitions.get(id)
                if (def.notedTemplateId == -1 && def.name.toLowerCase().contains(name)) {
                    entity perform Chat("Found item with name [${def.name.toLowerCase()}] - id: $id", Console)
                    found = true
                }
            }
            if(!found) {
                entity perform Chat("No item with name [$content] has been found!", Console)
            }
        } else {
            val def = definitions.get(id)
            entity perform Chat("Found item with name [${def.name.toLowerCase()}] - id: $id", Console)
        }
        isCancelled = true
    }
}