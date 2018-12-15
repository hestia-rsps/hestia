---
title: ClippingMaskSystem.addMask - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.map.systems</a> / <a href="index.html">ClippingMaskSystem</a> / <a href="./add-mask.html">addMask</a></div>

# addMask

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">addMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/mask">mask</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Overrides <a href="../../worlds.gregs.hestia.game.api.map/-clipping-masks/add-mask.html">ClippingMasks.addMask</a>

Adds clipping mask for a tile

### Parameters

<code>entityId</code> - The region entity id

<code>localX</code> - The x coordinate of the wall

<code>localY</code> - The y coordinate of the wall

<code>plane</code> - The plane coordinate of the wall

<code>mask</code> - The mask to add