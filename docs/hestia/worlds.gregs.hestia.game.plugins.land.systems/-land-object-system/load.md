---
title: LandObjectSystem.load - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.land.systems</a> / <a href="index.html">LandObjectSystem</a> / <a href="./load.html">load</a></div>

# load

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">load</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/landContainerData">landContainerData</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/settings">settings</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><span class="symbol">&gt;</span><span class="symbol">&gt;</span><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandObjectSystem$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkPlane">chunkPlane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Overrides <a href="../../worlds.gregs.hestia.game.api.land/-land-objects/load.html">LandObjects.load</a>

Loads all the objects within a region or chunk

### Parameters

<code>entityId</code> - The regions entity id

<code>x</code> - The regions x coordinate

<code>y</code> - The regions y coordinate

<code>landContainerData</code> - The cache map file data

<code>settings</code> - The clipping settings

<code>rotation</code> - The chunks rotation

<code>chunkX</code> - The chunks x coordinate

<code>chunkY</code> - The chunks x coordinate

<code>chunkPlane</code> - The regions plane coordinate