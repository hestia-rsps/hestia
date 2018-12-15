---
title: RegionBuilder - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.region</a> / <a href="./index.html">RegionBuilder</a></div>

# RegionBuilder

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">RegionBuilder</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

RegionBuilder
Toolset to dynamically modify region maps
Uses exact chunk coordinates, not local

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">RegionBuilder</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

RegionBuilder
Toolset to dynamically modify region maps
Uses exact chunk coordinates, not local


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="build.html">build</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">build</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$build(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

Applies the changes to the region <a href="build.html#worlds.gregs.hestia.game.api.region.RegionBuilder$build(kotlin.Int)/regionId">regionId</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="clear.html">clear</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">clear</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Clears the chunk at <a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</a>, <a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</a>, <a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">clear</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Clears an area of <a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</a>x<a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</a> chunks

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">clear</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Clears the whole region


</td>
</tr>
<tr>
<td markdown="1">

<a href="reset.html">reset</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">reset</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Reset the chunk at <a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</a>, <a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</a>, <a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">reset</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Resets an area of <a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</a>x<a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</a> to it's original chunks

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">reset</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Resets the whole region

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">reset</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Resets the builder


</td>
</tr>
<tr>
<td markdown="1">

<a href="set.html">set</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">set</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toChunkX">toChunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toChunkY">toChunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toPlane">toPlane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a>&nbsp;<span class="symbol">=</span>&nbsp;0<br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Sets a single chunk at <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</a>, <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</a>, <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a> to the chunk at <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toChunkX">toChunkX</a>, <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toChunkY">toChunkY</a>, <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toPlane">toPlane</a> with <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</a>

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">set</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toChunkX">toChunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toChunkY">toChunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/toPlane">toPlane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Sets an area <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</a>x<a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</a> of chunks with <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</a>

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">set</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int)/toRegionId">toRegionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a>&nbsp;<span class="symbol">=</span>&nbsp;0<br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Set's the whole region <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int)/regionId">regionId</a> to <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int)/toRegionId">toRegionId</a> with <a href="set.html#worlds.gregs.hestia.game.api.region.RegionBuilder$set(kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</a>


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.region.systems/-region-builder-system/index.html">RegionBuilderSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionBuilderSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">RegionBuilder</span></a></code></div>

DynamicRegionBuilder
Toolset to modify region maps
Uses exact chunk coordinates, not local


</td>
</tr>
</tbody>
</table>
