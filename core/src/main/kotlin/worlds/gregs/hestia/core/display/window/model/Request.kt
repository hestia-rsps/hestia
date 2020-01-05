package worlds.gregs.hestia.core.display.window.model

import worlds.gregs.hestia.core.script.dsl.task.ChatType.Assist
import worlds.gregs.hestia.core.script.dsl.task.ChatType.GameAssist
import worlds.gregs.hestia.core.script.dsl.task.ChatType.GameTrade
import worlds.gregs.hestia.core.script.dsl.task.ChatType.Trade

enum class Request(val sendRequest: String, val sendResponse: String, val receiveRequest: String, val reqType: Int, val otherType: Int, vararg val ids: Int) {
    TRADE("Sending trade offer...", "", "wishes to trade with you.", Trade, GameTrade, PlayerOptions.TRADE.slot),
    ASSIST("Sending assistance request.", "Sending assistance response.", "is requesting your assistance.", Assist, GameAssist, PlayerOptions.ASSIST.slot),
//    DUEL("", PlayerOptions.DUEL.slot, 14),
//    CLAN_WAR("", PlayerOptions.CLAN_WAR.slot, 14),
//    ALLIANCE("", PlayerOptions.ALLIANCE.slot, 50)
    ;

    companion object {
        fun getRequest(response: Int) = values().firstOrNull { it.ids.contains(response) }
    }
}