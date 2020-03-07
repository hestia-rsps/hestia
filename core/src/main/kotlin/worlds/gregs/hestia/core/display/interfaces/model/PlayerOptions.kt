package worlds.gregs.hestia.core.display.interfaces.model

enum class PlayerOptions(val string: String, val slot: Int, val top: Boolean = false) {
    ATTACK("Attack", 1, true),
    FOLLOW("Follow", 2),
    TRADE("Trade with", 4),
    ASSIST("Req Assist", 7),
    DUEL("Challenge", 1),
    CLAN_WAR("Challenge", 1),
    ALLIANCE("Alliance", 8);

    companion object {
        fun getOption(slot: Int) = values().firstOrNull { it.slot == slot }
        val names = values().map { it.string to it.slot }
    }
}