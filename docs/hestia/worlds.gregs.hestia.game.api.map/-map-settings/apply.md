---
title: MapSettings.apply - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.map</a> / <a href="index.html">MapSettings</a> / <a href="./apply.html">apply</a></div>

# apply

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">apply</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.MapSettings$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.MapSettings$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/settings">settings</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><span class="symbol">&gt;</span><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.MapSettings$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.MapSettings$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.MapSettings$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.MapSettings$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkPlane">chunkPlane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Adds clipping masks to whole region or single chunk based on the settings provided

### Parameters

<code>entityId</code> - The region entity

<code>settings</code> - The map height settings

<code>rotation</code> - Chunk rotation

<code>chunkX</code> - Chunk x coordinate

<code>chunkY</code> - Chunk y coordinate

<code>chunkPlane</code> - Chunk plane coordinate