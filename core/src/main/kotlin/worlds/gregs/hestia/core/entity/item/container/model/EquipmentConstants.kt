package worlds.gregs.hestia.core.entity.item.container.model

import worlds.gregs.hestia.service.cache.definition.definitions.ItemDefinition

object EquipConstants {
    /**
     * Checks if an item's definitions will result in being full body.
     *
     * @param def
     * The definitions
     */
    fun isFullBody(def: ItemDefinition): Boolean {
        val weapon: String = def.name
        for (name in FULL_BODY) {
            if (weapon.contains(name)) {
                return true
            }
        }
        return def.id == 6107 || def.id == 13624 || def.id == 13887
    }

    /**
     * Checks if an item's definitions will result in being full hat.
     *
     * @param def
     * The definitions
     */
    fun isFullHat(def: ItemDefinition): Boolean {
        val weapon: String = def.name
        for (name in FULL_HAT) {
            if (weapon.endsWith(name)) {
                return true
            }
        }
        return def.id === 14824
    }

    /**
     * Checks if an item's definitions will result in being full mask.
     *
     * @param def
     * The definitions
     */
    fun isFullMask(def: ItemDefinition): Boolean {
        val weapon: String = def.name
        for (name in FULL_MASK) {
            if (weapon.endsWith(name)) {
                return true
            }
        }
        return false
    }

//        fun isTwoHanded(item: Item): Boolean {
//            return item.getDefinitions().getEquipType() == 5
//        }

    /**
     * The names of items that are fully body items
     */
    val FULL_BODY = arrayOf("Investigator's coat", "armour", "hauberk", "top", "shirt", "platebody", "Ahrims robetop", "Karils leathertop", "brassard", "Robe top", "robetop", "platebody (t)", "platebody (g)", "chestplate", "torso", "Morrigan's", "leather body", "robe top", "Pernix body", "Torva platebody")
    /**
     * The names of items that are full hat items
     */
    val FULL_HAT = arrayOf("sallet", "med helm", "coif", "Dharok's helm", "hood", "Initiate helm", "Coif", "Helm of neitiznot")
    /**
     * The names of items that are full mask items
     */
    val FULL_MASK = arrayOf("Christmas ghost hood", "Dragon full helm (or)", "sallet", "full helm", "mask", "Veracs helm", "Guthans helm", "Torags helm", "Karils coif", "full helm (t)", "full helm (g)", "mask")
}
