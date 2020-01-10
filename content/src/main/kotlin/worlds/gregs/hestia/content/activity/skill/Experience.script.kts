package worlds.gregs.hestia.content.activity.skill

import worlds.gregs.hestia.network.client.encoders.messages.SkillLevel

var total = 14000000//Temp

on<Experience> {
    then {
        //Add
        total += increase
        entity send SkillLevel(skill.ordinal, 99, total)
    }
}