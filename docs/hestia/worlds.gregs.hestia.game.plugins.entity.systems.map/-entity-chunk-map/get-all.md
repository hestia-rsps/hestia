---
title: EntityChunkMap.getAll - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.entity.systems.map</a> / <a href="index.html">EntityChunkMap</a> / <a href="./get-all.html">getAll</a></div>

# getAll

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getAll</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$getAll(kotlin.ranges.IntRange, kotlin.ranges.IntRange, kotlin.Int)/xRange">xRange</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html"><span class="identifier">IntRange</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$getAll(kotlin.ranges.IntRange, kotlin.ranges.IntRange, kotlin.Int)/yRange">yRange</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html"><span class="identifier">IntRange</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$getAll(kotlin.ranges.IntRange, kotlin.ranges.IntRange, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span></code></div>

Returns a list of all entities in the range of chunks specified

### Parameters

<code>xRange</code> - ChunkX range

<code>yRange</code> - ChunkY range

<code>plane</code> -

**Return**
list of entity ids

