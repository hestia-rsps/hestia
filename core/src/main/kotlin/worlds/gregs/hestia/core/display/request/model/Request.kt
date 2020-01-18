package worlds.gregs.hestia.core.display.request.model

import worlds.gregs.hestia.core.display.dialogue.model.ChatType.Assist
import worlds.gregs.hestia.core.display.dialogue.model.ChatType.GameAssist
import worlds.gregs.hestia.core.display.dialogue.model.ChatType.GameTrade
import worlds.gregs.hestia.core.display.dialogue.model.ChatType.Trade
import worlds.gregs.hestia.core.display.interfaces.model.PlayerOptions

enum class Request(val sendRequest: String, val sendResponse: String?, val receiveRequest: String, val reqType: Int, val otherType: Int, val option: PlayerOptions, vararg val ids: Int) {
    TRADE("Sending trade offer...", null, "wishes to trade with you.", Trade, GameTrade, PlayerOptions.TRADE, PlayerOptions.TRADE.slot),
    ASSIST("Sending assistance request.", "Sending assistance response.", "is requesting your assistance.", Assist, GameAssist, PlayerOptions.ASSIST, PlayerOptions.ASSIST.slot),
//    DUEL("", PlayerOptions.DUEL.slot, 14),
//    CLAN_WAR("", PlayerOptions.CLAN_WAR.slot, 14),
//    ALLIANCE("", PlayerOptions.ALLIANCE.slot, 50)
    ;

    companion object {
        fun getRequest(response: Int) = values().firstOrNull { it.ids.contains(response) }
    }
}