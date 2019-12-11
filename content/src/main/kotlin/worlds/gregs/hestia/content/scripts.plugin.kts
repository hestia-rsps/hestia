package worlds.gregs.hestia.content

import worlds.gregs.hestia.content.dialogue.Dialog_script

val script = Dialog_script()

setup {
    script.build(this)
}

init { world, dispatcher ->
    script.build(world, dispatcher)
}