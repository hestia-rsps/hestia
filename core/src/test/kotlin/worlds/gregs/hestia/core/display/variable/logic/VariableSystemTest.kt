package worlds.gregs.hestia.core.display.variable.logic

import com.artemis.WorldConfigurationBuilder
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.send
import worlds.gregs.hestia.core.display.variable.api.Variable
import worlds.gregs.hestia.core.display.variable.model.VariableStore
import worlds.gregs.hestia.core.display.variable.model.variable.BitwiseVariable
import worlds.gregs.hestia.network.client.encoders.messages.Varbit
import worlds.gregs.hestia.network.client.encoders.messages.Varc
import worlds.gregs.hestia.network.client.encoders.messages.VarcStr
import worlds.gregs.hestia.network.client.encoders.messages.Varp

@ExtendWith(MockKExtension::class)
internal class VariableSystemTest : MockkGame() {

    val key = "key"

    @SpyK
    private var system = VariableSystem()

    @SpyK
    private var component = VariableStore()

    @RelaxedMockK
    lateinit var variable: Variable<Int>

    override fun config(config: WorldConfigurationBuilder) {
        config.with(system)
    }

    override fun start() {
        world.createEntity().edit().add(component)
        VariableSystem.names.clear()
        VariableSystem.variables.clear()
        setVariable(key, variable)
    }

    private fun setVariable(key: String, variable: Variable<*>) {
        VariableSystem.names[key] = variable.hash
        VariableSystem.variables[variable.hash] = variable
    }

    @Test
    fun `Set value`() {
        //Given
        setVariable(key, variable)
        val map = mutableMapOf<Int, Any>(variable.hash to 1)
        every { component.values } returns map
        every { system.send(any(), any()) } answers {}
        //When
        system.set(0, key, 42, true)
        //Then
        assertEquals(42, map[variable.hash])
        verify { system.send(any(), any()) }
    }

    @Test
    fun `Set removes default value`() {
        //Given
        every { variable.defaultValue } returns 42
        val map = mutableMapOf<Int, Any>(variable.hash to 1)
        every { component.values } returns map
        every { system.send(any(), any()) } answers {}
        //When
        system.set(0, key, 42, true)
        //Then
        assertTrue( map.isEmpty())
        verify { system.send(any(), any()) }
    }

    @Test
    fun `Set value no refresh`() {
        //Given
        val map = mutableMapOf<Int, Any>(variable.hash to 1)
        every { component.values } returns map
        every { system.send(any(), any()) } answers {}
        //When
        system.set(0, key, 42, false)
        //Then
        assertEquals(42, map[variable.hash])
        verify(exactly = 0) { system.send(any(), any()) }
    }

    @Test
    fun `Send varp`() {
        //Given
        every { variable.type } returns Variable.Type.VARP
        //When
        system.send(0, key)
        //Then
        verify { es.send(0, Varp(variable.id, 0)) }
    }

    @Test
    fun `Send varbit`() {
        //Given
        every { variable.type } returns Variable.Type.VARBIT
        //When
        system.send(0, key)
        //Then
        verify { es.send(0, Varbit(variable.id, 0)) }
    }

    @Test
    fun `Send varc`() {
        //Given
        every { variable.type } returns Variable.Type.VARC
        //When
        system.send(0, key)
        //Then
        verify { es.send(0, Varc(variable.id, 0)) }
    }

    @Test
    fun `Send varcstr`() {
        //Given
        val variable = mockk<Variable<String>>(relaxed = true)
        every { variable.type } returns Variable.Type.VARCSTR
        every { variable.defaultValue } returns "nothing"
        setVariable(key, variable)
        //When
        system.send(0, key)
        //Then
        verify { es.send(0, VarcStr(variable.id, "nothing")) }
    }

    @Test
    fun `Get variable`() {
        //Given
        every { component.values } returns mutableMapOf(variable.hash to 42)
        //When
        val result = system.get(0, key, -1)
        //Then
        assertEquals(42, result)
    }

    @Test
    fun `Get default value`() {
        //Given
        every { component.values } returns mutableMapOf()
        every { variable.defaultValue } returns 42
        //When
        val result = system.get(0, key, -1)
        //Then
        assertEquals(42, result)
    }

    @Test
    fun `Get no variable`() {
        //Given
        VariableSystem.variables.clear()
        //When
        val result = system.get(0, key, -1)
        //Then
        assertEquals(-1, result)
    }

    @Test
    fun `Add bitwise`() {
        //Given
        val variable = BitwiseVariable(0, Variable.Type.VARP, values = listOf("First", "Second"))
        val map = mutableMapOf<Int, Any>(variable.hash to 0)
        setVariable(key, variable)
        every { component.values } returns map
        //When
        system.add(0, key, "First", true)
        //Then
        assertEquals(1, map[variable.hash])
        verify{ system.send(0, key) }
    }

    @Test
    fun `Add bitwise two`() {
        //Given
        val variable = BitwiseVariable(0, Variable.Type.VARP, values = listOf("First", "Second"))
        val map = mutableMapOf<Int, Any>(variable.hash to 1)
        setVariable(key, variable)
        every { component.values } returns map
        //When
        system.add(0, key, "Second", true)
        //Then
        assertEquals(3, map[variable.hash])
        verify{ system.send(0, key) }
    }

    @Test
    fun `Add bitwise existing`() {
        //Given
        val variable = BitwiseVariable(0, Variable.Type.VARP, values = listOf("First", "Second"))
        val map = mutableMapOf<Int, Any>(variable.hash to 1)
        setVariable(key, variable)
        every { component.values } returns map
        //When
        system.add(0, key, "First", true)
        //Then
        assertEquals(1, map[variable.hash])//Doesn't change
        verify(exactly = 0) { system.send(0, key) }
    }

    @Test
    fun `Add bitwise no refresh`() {
        //Given
        val variable = BitwiseVariable(0, Variable.Type.VARP, values = listOf("First", "Second"))
        val map = mutableMapOf<Int, Any>(variable.hash to 0)
        setVariable(key, variable)
        every { component.values } returns map
        //When
        system.add(0, key, "First", false)
        //Then
        assertEquals(1, map[variable.hash])
        verify(exactly = 0) { system.send(0, key) }
    }

    @Test
    fun `Remove bitwise`() {
        //Given
        val variable = BitwiseVariable(0, Variable.Type.VARP, values = listOf("First", "Second"))
        val map = mutableMapOf<Int, Any>(variable.hash to 3)
        setVariable(key, variable)
        every { component.values } returns map
        //When
        system.remove(0, key, "First", true)
        //Then
        assertEquals(2, map[variable.hash])
        verify { system.send(0, key) }
    }

    @Test
    fun `Remove bitwise no refresh`() {
        //Given
        val variable = BitwiseVariable(0, Variable.Type.VARP, values = listOf("First", "Second"))
        val map = mutableMapOf<Int, Any>(variable.hash to 3)
        setVariable(key, variable)
        every { component.values } returns map
        //When
        system.remove(0, key, "First", false)
        //Then
        assertEquals(2, map[variable.hash])
        verify(exactly = 0) { system.send(0, key) }
    }
}