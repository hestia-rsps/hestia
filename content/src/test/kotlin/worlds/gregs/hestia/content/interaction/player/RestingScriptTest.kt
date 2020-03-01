package worlds.gregs.hestia.content.interaction.player

import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.core.display.variable.api.Variables
import worlds.gregs.hestia.script.ScriptTester

internal class RestingScriptTest : ScriptTester<Resting>() {

    val targetId = 0

    @RelaxedMockK
    lateinit var variables: Variables

    override fun loadInjections() {
        inject(Variables::class, variables)
        super.loadInjections()
    }

    @Test
    fun `Listen to musician resting`() {

    }

    @Test
    fun `Energy orb resting`() {

    }

    @Test
    fun `Resting cancelled`() {

    }

    @Test
    fun `Energy orb toggle run`() {

    }

    @Test
    fun `Energy orb toggle walk`() {

    }

    @Test
    fun `Energy orb toggle run when resting`() {

    }

    @Test
    fun `Energy orb toggle walk when resting`() {

    }

    @Test
    fun `Energy orb toggle run when listening`() {

    }

    @Test
    fun `Energy orb toggle walk when listening`() {

    }

}