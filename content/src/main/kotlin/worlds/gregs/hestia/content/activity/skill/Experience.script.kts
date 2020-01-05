package worlds.gregs.hestia.content.activity.skill

import worlds.gregs.hestia.network.client.encoders.messages.SkillLevel

var total = 14000000//Temp

on<Experience> {
    task {
        val event = event(this)
        val (_, skill, amount) = event
        if(event.isCancelled) {
            return@task//FIXME cancelling
        }
        //Add
        total += amount
        entity send SkillLevel(skill.ordinal, 99, total)
    }
}