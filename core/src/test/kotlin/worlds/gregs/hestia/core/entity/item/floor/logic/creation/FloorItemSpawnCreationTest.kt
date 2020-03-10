package worlds.gregs.hestia.core.entity.item.floor.logic.creation

import com.artemis.WorldConfigurationBuilder
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import worlds.gregs.hestia.MockkGame
import worlds.gregs.hestia.artemis.getComponent
import worlds.gregs.hestia.core.entity.entity.model.components.Position
import worlds.gregs.hestia.core.entity.entity.model.components.Type
import worlds.gregs.hestia.core.entity.item.floor.model.components.Amount
import worlds.gregs.hestia.core.entity.item.floor.model.components.SpawnPoint
import worlds.gregs.hestia.core.entity.item.floor.model.events.CreateFloorItemSpawn

@ExtendWith(MockKExtension::class)
internal class FloorItemSpawnCreationTest : MockkGame() {

    private val creation = FloorItemSpawnCreation()

    override fun config(config: WorldConfigurationBuilder) {
        config.with(creation)
    }

    @Test
    fun `Create default values`() {
        //Given
        val event = CreateFloorItemSpawn(4, 5, 15, 10, 2, 10)
        //When
        val entityId = creation.create(event)
        //Then
        val entity = world.getEntity(entityId)
        val type = entity.getComponent(Type::class)
        val amount = entity.getComponent(Amount::class)
        val position = entity.getComponent(Position::class)
        val spawnPoint = entity.getComponent(SpawnPoint::class)

        assertNotNull(type)
        assertNotNull(amount)
        assertNotNull(position)
        assertNotNull(spawnPoint)
        assertEquals(4, type!!.id)
        assertEquals(5, amount!!.amount)
        assertEquals(15, position!!.x)
        assertEquals(10, position.y)
        assertEquals(2, position.plane)
        assertEquals(10, spawnPoint!!.delay)
        assertEquals(10, spawnPoint.delayTime)
        assertEquals(-1, spawnPoint.entityId)
    }

}