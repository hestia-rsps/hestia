package worlds.gregs.hestia.core.entity.entity.api

import com.artemis.ArchetypeBuilder

interface ArchetypeFactory {
    fun getBuilder(): ArchetypeBuilder
}