---
title: RegionBuilder.clear - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.region</a> / <a href="index.html">RegionBuilder</a> / <a href="./clear.html">clear</a></div>

# clear

<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">clear</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Clears the chunk at <a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</a>, <a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</a>, <a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>

### Parameters

<code>chunkX</code> - The x position of the chunk to remove

<code>chunkY</code> - The y position of the chunk to remove

<code>plane</code> - The z position of the chunk to remove

</div>
<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">clear</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Clears an area of <a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</a>x<a href="clear.html#worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</a> chunks

### Parameters

<code>chunkX</code> - The x position of the chunk to replace

<code>chunkY</code> - The y position of the chunk to replace

<code>plane</code> - The z position of the chunk to replace

<code>width</code> - The number of chunks to replace on the x axis

<code>height</code> - The number of chunks to replace on the y axis

</div>
<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">clear</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$clear(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Clears the whole region

</div>
