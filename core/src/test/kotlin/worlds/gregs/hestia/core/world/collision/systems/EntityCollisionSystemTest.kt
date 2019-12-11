package worlds.gregs.hestia.core.world.collision.systems

import com.artemis.WorldConfigurationBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import worlds.gregs.hestia.game.entity.components.Position
import worlds.gregs.hestia.game.entity.components.Size
import worlds.gregs.hestia.api.movement.components.Shift
import worlds.gregs.hestia.tools.PlayerTester
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.PlayerChunkSubscriptionSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.PlayerChunkSystem
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.map.PlayerChunkMap
import worlds.gregs.hestia.core.entity.player.logic.systems.chunk.map.PlayerChunkMapSystem
import worlds.gregs.hestia.services.getSystem

internal class EntityCollisionSystemTest : PlayerTester(WorldConfigurationBuilder().with(EntityCollisionSystem(), PlayerChunkMapSystem(), PlayerChunkMap(), PlayerChunkSystem(), PlayerChunkSubscriptionSystem())) {

    @Test
    fun `Collides with player`() {
        fakePlayer(0, 1)
        tick()
        val system = world.getSystem(EntityCollisionSystem::class)
        system.load(1, Position.create(0, 0, 0))
        assertThat(system.collides(0, 1)).isTrue()
        assertThat(system.collides(0, 0)).isFalse()
    }

    @Test
    fun `Collides with fat player`() {
        fakePlayer(1, 1).edit().add(Size(2, 2))
        tick()
        val system = world.getSystem(EntityCollisionSystem::class)
        system.load(1, Position.create(0, 0, 0))
        assertThat(system.collides(1, 1)).isTrue()
        assertThat(system.collides(1, 2)).isTrue()
        assertThat(system.collides(2, 1)).isTrue()
        assertThat(system.collides(2, 2)).isTrue()
    }

    @Test
    fun `Collides with moving player`() {
        fakePlayer(1, 1).edit().add(Shift(1, 0))
        tick()
        val system = world.getSystem(EntityCollisionSystem::class)
        system.load(1, Position.create(0, 0, 0))
        assertThat(system.collides(1, 1)).isFalse()
        assertThat(system.collides(2, 1)).isTrue()
    }

    @Test
    fun `Collides out of region`() {
        fakePlayer(0, 0)
        fakePlayer(47, 0)
        fakePlayer(69, 0)
        tick()
        val system = world.getSystem(EntityCollisionSystem::class)
        system.load(3, Position.create(68, 0, 0))
        assertThat(system.collides(0, 0)).isFalse()
        assertThat(system.collides(-17, 0)).isFalse()
        assertThat(system.collides(5, 0)).isTrue()//69 - 64
    }

    @Test
    fun `Collides on edge of area`() {
        //West side
        fakePlayer(46, 0)
        fakePlayer(47, 0)
        fakePlayer(48, 0)
        fakePlayer(49, 0)
        //East side
        fakePlayer(86, 0)
        fakePlayer(87, 0)
        fakePlayer(88, 0)
        fakePlayer(89, 0)
        tick()
        val system = world.getSystem(EntityCollisionSystem::class)
        system.load(8, Position.create(68, 0, 0))
        //West
        assertThat(system.collides(-18, 0)).isFalse()
        assertThat(system.collides(-17, 0)).isFalse()
        assertThat(system.collides(-16, 0)).isTrue()
        assertThat(system.collides(-15, 0)).isTrue()
        //East
        assertThat(system.collides(22, 0)).isTrue()
        assertThat(system.collides(23, 0)).isTrue()
        assertThat(system.collides(24, 0)).isFalse()
        assertThat(system.collides(25, 0)).isFalse()
    }

    @Test
    fun `No collision out of area when size is in`() {
        fakePlayer(47, 0).edit().add(Size(2, 1))
        tick()
        val system = world.getSystem(EntityCollisionSystem::class)
        system.load(0, Position.create(68, 0, 0))
        assertThat(system.collides(47, 0)).isFalse()
        assertThat(system.collides(48, 0)).isFalse()
    }

    @Test
    fun `No collision out of area when movement is in`() {
        fakePlayer(47, 0).edit().add(Shift(1, 0))
        tick()
        val system = world.getSystem(EntityCollisionSystem::class)
        system.load(0, Position.create(68, 0, 0))
        assertThat(system.collides(47, 0)).isFalse()
        assertThat(system.collides(48, 0)).isFalse()
    }

    @Test
    fun `Collides out of region real coordinates`() {
        fakePlayer(3063, 3497)
        fakePlayer(3086, 3497)
        fakePlayer(3071, 3497)
        tick()
        val system = world.getSystem(EntityCollisionSystem::class)
        system.load(3, Position.create(3087, 3497, 0))
        assertThat(system.collides(-9, 0)).isFalse()
        assertThat(system.collides(-1, 41)).isTrue()
        assertThat(system.collides(14, 41)).isTrue()
    }
}