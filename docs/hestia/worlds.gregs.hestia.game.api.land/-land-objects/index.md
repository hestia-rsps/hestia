---
title: LandObjects - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.land</a> / <a href="./index.html">LandObjects</a></div>

# LandObjects

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">LandObjects</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

LandObjects
Loads objects

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">LandObjects</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

LandObjects
Loads objects


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="load.html">load</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">load</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/landContainerData">landContainerData</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/settings">settings</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><span class="symbol">&gt;</span><span class="symbol">&gt;</span><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.land.LandObjects$load(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.ByteArray, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkPlane">chunkPlane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Loads all the objects within a region or chunk


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.land.systems/-land-object-system/index.html">LandObjectSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">LandObjectSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">LandObjects</span></a></code></div>

MapObjectSystem
Adds region objects using <a href="../-land/index.html">Land</a> from the <a href="../../worlds.gregs.hestia.game.plugins.region.systems.load/-region-file-system/index.html">worlds.gregs.hestia.game.plugins.region.systems.load.RegionFileSystem</a> data


</td>
</tr>
</tbody>
</table>
