package worlds.gregs.hestia.content.dialogue

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.dialogue.api.DialogueBase
import worlds.gregs.hestia.artemis.events.Command
import worlds.gregs.hestia.core.scripts.ScriptBase
import worlds.gregs.hestia.scripts.ScriptTesterMock

internal class DialogScriptTest : ScriptTesterMock<ScriptBase>() {
/*
    override fun build(builder: WorldConfigurationBuilder) {
        val base: DialogueBase = mock()
        builder.with(base, DialogueBoxSystem())
        super.build(builder)
    }

    override fun initiate(dispatcher: PollingEventDispatcher) {
        super.initiate(dispatcher)
        val archetype = ArchetypeBuilder().add(DialogueBox::class).build(world)
        world.create(archetype)
    }*/

    @Test
    fun `Test started dialogue`() {
        //Given
        val dialogueSystem = mock<DialogueBase>()
        whenever(world.getSystem(eq(DialogueBase::class.java))).thenReturn(dialogueSystem)
        //When
        script.listeners.forEach {
            if(it.event == Command::class) {
                it.action.invoke(Command(0, "test"))
            }
        }
        //Then
        verify(dialogueSystem).startDialogue(0, "Man")
    }

}