package worlds.gregs.hestia.network.world

import world.gregs.hestia.core.network.protocol.Details

class WorldDetails(ip: String, region: String, country: Int, location: Int, activity: String, flag: Int) : Details(-1, 0, ip, region, country, location, activity, flag) {

    constructor(ip: String, region: String, country: Country, location: Int, activity: String, vararg settings: Setting) : this(ip, region, country.id, location, activity, getFlag(settings))

    enum class Country(val id: Int) {
        AUSTRALIA(16),
        BELGIUM(22),
        BRAZIL(31),
        CANADA(38),
        DENMARK(58),
        FINLAND(69),
        IRELAND(101),
        MEXICO(152),
        NETHERLANDS(161),
        NORWAY(162),
        SWEDEN(191),
        UK(77),
        USA(225);
    }

    enum class Setting(val flag: Int) {
        NON_MEMBERS(0x0),
        MEMBERS(0x1),
        PVP(0x4),
        LOOT_SHARE(0x8),
        HIGHLIGHT(0x10),
        HIGH_RISK(0x400);
    }

    companion object {
        private fun getFlag(settings: Array<out Setting>): Int {
            var flags = 0
            settings.forEach { flags = flags or it.flag }
            return flags
        }
    }
}