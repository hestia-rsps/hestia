---
title: RegionBuilder.reset - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.region</a> / <a href="index.html">RegionBuilder</a> / <a href="./reset.html">reset</a></div>

# reset

<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">reset</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Reset the chunk at <a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</a>, <a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</a>, <a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>

### Parameters

<code>chunkX</code> - The x position of the chunk to remove

<code>chunkY</code> - The y position of the chunk to remove

<code>plane</code> - The z position of the chunk to remove

</div>
<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">reset</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Resets an area of <a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/width">width</a>x<a href="reset.html#worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/height">height</a> to it's original chunks

### Parameters

<code>chunkX</code> - The x position of the chunk to replace

<code>chunkY</code> - The y position of the chunk to replace

<code>plane</code> - The z position of the chunk to replace

<code>width</code> - The number of chunks to replace on the x axis

<code>height</code> - The number of chunks to replace on the y axis

</div>
<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">reset</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionBuilder$reset(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Resets the whole region

</div>
<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">reset</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Resets the builder

</div>
