package worlds.gregs.hestia.core.script.dsl

import com.artemis.Component
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import worlds.gregs.hestia.service.Aspect
import worlds.gregs.hestia.service.all

internal class SystemBuilderTest {

    @Test
    fun `Aspect left empty is null`() {
        //Given
        val builder = SystemBuilder(mutableListOf())
        //Then
        assertThat(builder.build().aspect).isNull()
    }

    @Test
    fun `Subscription local if aspect is empty`() {
        //Given
        val subscriptions = mutableListOf<ArtemisSubscription>()
        val builder = SystemBuilder(subscriptions)
        //When
        builder.subscribe()
        //Then
        assertThat(builder.build().subscription).isNotNull
        assertThat(subscriptions).isEmpty()
    }

    @Test
    fun `Can't have multiple local subscriptions`() {
        //Given
        val builder = SystemBuilder(mutableListOf())
        builder.subscribe()
        //Then
        assertThrows<IllegalArgumentException> {
            builder.subscribe()
        }
    }

    @Test
    fun `Add entity subscription`() {
        //Given
        val subscriptions = mutableListOf<ArtemisSubscription>()
        val builder = SystemBuilder(subscriptions)
        builder.aspect.all(Component::class)
        //When
        builder.subscribe(Aspect.all(Component::class))
        //Then
        assertThat(builder.build().subscription).isNull()
        assertThat(subscriptions).isNotEmpty
    }

    @Test
    fun `Add process`() {
        //Given
        val builder = SystemBuilder(mutableListOf())
        //When
        val process: (Int) -> Unit = {}
        builder.process(process)
        //Then
        assertThat(builder.build().process).isEqualTo(process)
    }

    @Test
    fun `Can't override process`() {
        //Given
        val builder = SystemBuilder(mutableListOf())
        builder.process { }
        //Then
        assertThrows<IllegalArgumentException> {
            builder.process {  }
        }
    }

    @Test
    fun `Add initialize`() {
        //Given
        val builder = SystemBuilder(mutableListOf())
        //When
        val initialize: () -> Unit = {}
        builder.initialize(initialize)
        //Then
        assertThat(builder.build().initialize == initialize)
    }

    @Test
    fun `Can't override initialize`() {
        //Given
        val builder = SystemBuilder(mutableListOf())
        builder.initialize { }
        //Then
        assertThrows<IllegalArgumentException> {
            builder.initialize {  }
        }
    }

    @Test
    fun `Add dispose`() {
        //Given
        val builder = SystemBuilder(mutableListOf())
        //When
        val dispose: () -> Unit = {}
        builder.dispose(dispose)
        //Then
        assertThat(builder.build().dispose == dispose)
    }

    @Test
    fun `Can't override dispose`() {
        //Given
        val builder = SystemBuilder(mutableListOf())
        builder.dispose { }
        //Then
        assertThrows<IllegalArgumentException> {
            builder.dispose {  }
        }
    }

}