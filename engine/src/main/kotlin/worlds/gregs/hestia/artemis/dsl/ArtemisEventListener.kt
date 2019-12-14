package worlds.gregs.hestia.artemis.dsl

import net.mostlyoriginal.api.event.common.Event
import kotlin.reflect.KClass

data class ArtemisEventListener(val event: KClass<out Event>, val priority: Int, val skipCancelledEvents: Boolean, val conditional: ((Event) -> Boolean)?, val action: (Event) -> Unit)