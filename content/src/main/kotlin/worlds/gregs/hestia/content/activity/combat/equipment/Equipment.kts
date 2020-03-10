import worlds.gregs.hestia.content.activity.combat.equipment.EquippedItem
import worlds.gregs.hestia.content.activity.combat.equipment.UnequippedItem
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.SLOT_WEAPON
import worlds.gregs.hestia.core.entity.item.container.logic.EquipmentSystem.Companion.equipSlots
import worlds.gregs.hestia.core.entity.player.model.components.update.Emote
import worlds.gregs.hestia.core.script.on
import worlds.gregs.hestia.service.cache.definition.systems.ItemDefinitionSystem

lateinit var definitions: ItemDefinitionSystem

on<EquippedItem> {
    where { equipSlots[item.type] == SLOT_WEAPON }
    then {
        val definition = definitions.get(item.type)
        val renderAnim = definition.params?.get(644) as? Int ?: 1426
        entity.create(Emote::class).id = renderAnim
    }
}

on<UnequippedItem> {
    where { equipSlots[item.type] == SLOT_WEAPON }
    then {
        entity.create(Emote::class).id = 1426
    }
}