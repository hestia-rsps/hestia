package worlds.gregs.hestia.game.plugins.dialogue.systems

import worlds.gregs.hestia.game.dialogue.Dialogue
import worlds.gregs.hestia.game.dialogue.Dialogues
import worlds.gregs.hestia.game.dialogue.faces.DialogueLinear
import worlds.gregs.hestia.game.plugins.dialogue.systems.types.BaseDialogue
import worlds.gregs.hestia.game.plugins.dialogue.systems.types.OptionsDialogue
import worlds.gregs.hestia.game.plugins.dialogue.systems.types.RedirectDialogue

class DialogueFactory {

    fun dialogue(name: String, build: (Dialogues.() -> Unit)): List<Dialogue> {
        return build(BaseDialogue(build), name)
    }

    fun build(base: BaseDialogue, name: String): List<Dialogue> {
        val all = collect(base)

        redirectCheck(all, name)

        assign(all)

        return all
    }

    /**
     * Checks dialogues for redirect formatting issues
     * @throws IllegalArgumentException
     */
    @Throws(IllegalArgumentException::class)
    private fun redirectCheck(all: List<Dialogue>, name: String) {
        //Quick redirect check
        val manualIds = all.asSequence().filter { it.id != -1 }
        val redirects = all.filterIsInstance<OptionsDialogue>().flatMap { it.options.asSequence().filterIsInstance<RedirectDialogue>().toList() }//TODO fix
        if (manualIds.count() < redirects.count()) {
            throw IllegalArgumentException("Dialogue script $name must have id's set for all redirects.")
        }

        //More lengthy redirect check
        val ids = manualIds.map { it.id }
        val broken = redirects.firstOrNull { !ids.contains(it.id) }
        if (broken != null) {
            throw IllegalArgumentException("No dialogue id found for redirect: ${broken.id} script: $name.")
        }

        //Duplication check
        val distinct = ids.distinct()
        if (ids.count() != distinct.count()) {
            throw IllegalArgumentException("Duplicate redirect ids found: ${ids.groupingBy { it }.eachCount().filter { it.value > 1 }.keys} script: $name")
        }
    }

    /**
     * Assigns the remaining dialogues with id's
     */
    private fun assign(all: List<Dialogue>) {
        val ids = all.asSequence().filter { it.id != -1 }.map { it.id }

        //Automatically assign the remaining id's
        var count = 0
        all.asSequence().filter { it.id == -1 }.forEach {
            while (ids.contains(count))
                count++

            it.id = count++
        }
    }

    /**
     * Collects all the dialogues into one list
     */
    private fun collect(dialogue: Dialogue): List<Dialogue> {
        val list = ArrayList<Dialogue>()
        list.add(dialogue)

        if (dialogue is DialogueLinear && dialogue.next != null) {
            list.addAll(collect(dialogue.next!!))
        } else if (dialogue is OptionsDialogue) {
            list.addAll(dialogue.options.flatMap { collect(it) })
        }
        return list
    }

    companion object {

        /**
         * Prints dialogues list in readable format
         */
        fun print(all: List<Dialogue>) {
            //Print results
            all.forEach { d ->
                println(when (d) {
                    is OptionsDialogue -> "$d -> ${d.options}"
                    is DialogueLinear -> "$d -> ${d.next}"
                    is RedirectDialogue -> "$d -> ${all[d.directId]}"
                    else -> "$d -> [] ([])"
                })
            }
        }
    }
} 