package worlds.gregs.hestia.artemis.events

import net.mostlyoriginal.api.event.common.Event

data class ContinueDialogue(val entityId: Int, val interfaceId: Int, val buttonId: Int, val component: Int) : Event