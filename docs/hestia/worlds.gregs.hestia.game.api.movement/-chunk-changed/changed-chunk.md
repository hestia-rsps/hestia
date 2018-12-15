---
title: ChunkChanged.changedChunk - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.movement</a> / <a href="index.html">ChunkChanged</a> / <a href="./changed-chunk.html">changedChunk</a></div>

# changedChunk

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">changedChunk</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/from">from</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/to">to</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Activates when an entity changes chunk

### Parameters

<code>entityId</code> - the id of the entity which changed

<code>from</code> - the chunk hash they were in

<code>to</code> - the chunk hash they are now in