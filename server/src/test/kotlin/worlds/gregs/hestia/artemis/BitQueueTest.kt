package worlds.gregs.hestia.artemis

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class BitQueueTest {

    private lateinit var queue: BitQueue

    @BeforeEach
    fun setup() {
        queue = BitQueue()
    }

    @Test
    fun `Polling functions`() {
        queue.add(10)
        queue.add(100)
        queue.add(1000)
        //When
        assertThat(queue.contains(10)).isTrue()
        assertThat(queue.poll()).isEqualTo(10)
        assertThat(queue.contains(10)).isFalse()
        assertThat(queue.contains(100)).isTrue()
        assertThat(queue.poll()).isEqualTo(100)
        assertThat(queue.contains(100)).isFalse()
        assertThat(queue.contains(1000)).isTrue()
        assertThat(queue.poll()).isEqualTo(1000)
        assertThat(queue.contains(1000)).isFalse()
        assertThat(queue.poll()).isNull()
    }
}