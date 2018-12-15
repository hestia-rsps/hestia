---
title: PlayerChunkSubscriptionSystem.changedChunk - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.player.systems.chunk</a> / <a href="index.html">PlayerChunkSubscriptionSystem</a> / <a href="./changed-chunk.html">changedChunk</a></div>

# changedChunk

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">changedChunk</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkSubscriptionSystem$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkSubscriptionSystem$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/from">from</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkSubscriptionSystem$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/to">to</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Overrides <a href="../../worlds.gregs.hestia.game.api.movement/-chunk-subscription/changed-chunk.html">ChunkSubscription.changedChunk</a>

Activates when an entity changes chunk

### Parameters

<code>entityId</code> - the id of the entity which changed

<code>from</code> - the chunk hash they were in

<code>to</code> - the chunk hash they are now in