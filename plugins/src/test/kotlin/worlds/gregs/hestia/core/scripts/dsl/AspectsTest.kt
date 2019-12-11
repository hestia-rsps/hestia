package worlds.gregs.hestia.core.scripts.dsl

import com.artemis.Aspect
import com.artemis.Component
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AspectsTest : Aspects {

    @Test
    fun `'all' creates aspect`() {
        //Given
        val aspect = all(Component::class)
        //Then
        assertThat(aspect).isEqualTo(Aspect.all(Component::class.java))
    }

    @Test
    fun `'one' creates aspect`() {
        //Given
        val aspect = one(Component::class)
        //Then
        assertThat(aspect).isEqualTo(Aspect.one(Component::class.java))
    }

    @Test
    fun `'exclude' creates aspect`() {
        //Given
        val aspect = exclude(Component::class)
        //Then
        assertThat(aspect).isEqualTo(Aspect.exclude(Component::class.java))
    }

    @Test
    fun `'all' adds to existing aspect`() {
        //Given
        val aspect = Aspect.all()
        //When
        aspect all Component::class
        //Then
        assertThat(aspect).isEqualTo(Aspect.all(Component::class.java))
    }

    @Test
    fun `'one' adds to existing aspect`() {
        //Given
        val aspect = Aspect.all()
        //When
        aspect one Component::class
        //Then
        assertThat(aspect).isEqualTo(Aspect.one(Component::class.java))
    }
    
    @Test
    fun `'exclude' adds to existing aspect`() {
        //Given
        val aspect = Aspect.all()
        //When
        aspect exclude Component::class
        //Then
        assertThat(aspect).isEqualTo(Aspect.exclude(Component::class.java))
    }

}