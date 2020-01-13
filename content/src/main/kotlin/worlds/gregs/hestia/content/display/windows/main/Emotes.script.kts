package worlds.gregs.hestia.content.display.windows.main

import worlds.gregs.hestia.core.display.window.api.Variable
import worlds.gregs.hestia.core.display.window.api.Windows.Companion.Emotes
import worlds.gregs.hestia.core.display.window.model.events.variable.SendVariable
import worlds.gregs.hestia.core.display.window.model.events.WindowOpened
import worlds.gregs.hestia.core.display.window.model.variable.BitwiseVariable
import worlds.gregs.hestia.core.display.window.model.variable.StringMapVariable
import worlds.gregs.hestia.network.client.encoders.messages.WidgetSettings

StringMapVariable(465, Variable.Type.VARP, true, mapOf(
        0 to "locked",
        7 to "unlocked"
)).register("lost_tribe_emotes")

StringMapVariable(1085, Variable.Type.VARP, true, mapOf(
        0 to "locked",
        249852 to "unlocked"
)).register("zombie_hand_emote")

BitwiseVariable(802, Variable.Type.VARP, true, values = listOf(
        "Flap",
        "Slap Head",
        "Idea",
        "Stomp"
)).register("stronghold_of_security_emotes")

BitwiseVariable(313, Variable.Type.VARP, true, values = listOf(
        "Glass Wall",
        "Glass Box",
        "Climb Rope",
        "Lean",
        "Scared",
        "Zombie Dance",
        "Zombie Walk",
        "Bunny-hop",
        "Skillcape",
        "Snowman Dance",
        "Air Guitar",
        "Safety First",
        "Explore",
        "Trick",
        "Give Thanks",
        "Freeze"
)).register("event_emotes")

on<WindowOpened> {
    where { target == Emotes }
    then {
        for (widget in 11..14) {
            entity send WidgetSettings(Emotes, widget, -1, 190, 2150)
        }

        entity perform SendVariable("lost_tribe_emotes")
        entity perform SendVariable("stronghold_of_security_emotes")
        entity perform SendVariable("zombie_hand_emote")
        entity perform SendVariable("event_emotes")
    }
}