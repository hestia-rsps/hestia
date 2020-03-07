package worlds.gregs.hestia.content.display.main

import worlds.gregs.hestia.core.action.model.InterfaceOption
import worlds.gregs.hestia.core.action.logic.systems.on
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.GraphicsOptions
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.Options
import worlds.gregs.hestia.core.display.interfaces.api.Interfaces.Companion.SoundOptions
import worlds.gregs.hestia.core.display.interfaces.model.events.request.OpenInterface

on(InterfaceOption, "Graphics Settings", id = Options) { _, _, _, _ ->
    entity perform OpenInterface(GraphicsOptions)
}

on(InterfaceOption, "Audio Settings", id = Options) { _, _, _, _ ->
    entity perform OpenInterface(SoundOptions)
}

on(InterfaceOption, "Toggle Number of Mouse Buttons", id = Options) { _, _, _, _ ->
}

on(InterfaceOption, "Toggle Profanity Filter", id = Options) { _, _, _, _ ->
}

on(InterfaceOption, "Toggle Chat Effects", id = Options) { _, _, _, _ ->
}

on(InterfaceOption, "Open Chat Setup", id = Options) { _, _, _, _ ->
}

on(InterfaceOption, "Toggle Accept Aid", id = Options) { _, _, _, _ ->
}

on(InterfaceOption, "Open House Options", id = Options) { _, _, _, _ ->
}

on(InterfaceOption, "Adventurerﾒs Log Options", id = Options) { _, _, _, _ ->
}