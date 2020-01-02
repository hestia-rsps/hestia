package worlds.gregs.hestia.core.script.dsl.artemis

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.artemis.Aspect

internal class SubscribeBuilderTest {

    @Test
    fun `Aspect null if left empty`() {
        //Given
        val builder = SubscribeBuilder(Aspect.all(), null, null)
        //Then
        assertThat(builder.build().aspect).isNull()
    }

    @Test
    fun `'insert' sets insert action`() {
        //Given
        val builder = SubscribeBuilder(null, null, null)
        //When
        val function: (Int) -> Unit = {}
        builder.insert(function)
        //Then
        assertThat(builder.build().inserted).isEqualTo(function)
    }

    @Test
    fun `Can't override insert action`() {
        //Given
        val builder = SubscribeBuilder(null, null, null)
        //When
        builder.insert {  }
        //Then
        assertThrows<IllegalArgumentException> {
            builder.insert {  }
        }
    }

    @Test
    fun `'remove' sets remove action`() {
        //Given
        val builder = SubscribeBuilder(null, null, null)
        //When
        val function: (Int) -> Unit = {}
        builder.remove(function)
        //Then
        assertThat(builder.build().removed).isEqualTo(function)
    }

    @Test
    fun `Can't override remove action`() {
        //Given
        val builder = SubscribeBuilder(null, null, null)
        //When
        builder.remove {  }
        //Then
        assertThrows<IllegalArgumentException> {
            builder.remove {  }
        }
    }
}