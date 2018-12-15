---
title: ClippingMasks.getMask - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.map</a> / <a href="index.html">ClippingMasks</a> / <a href="./get-mask.html">getMask</a></div>

# getMask

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">getMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/clipping">clipping</span><span class="symbol">:</span>&nbsp;<a href="../-clipping/index.html"><span class="identifier">Clipping</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Get's a clipping mask for the tile at <a href="get-mask.html#worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</a>, <a href="get-mask.html#worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</a>, <a href="get-mask.html#worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>

### Parameters

<code>entityId</code> - The region entity id

<code>clipping</code> - The clipping to get the mask from

<code>localX</code> - The local x coordinate of the wall

<code>localY</code> - The local y coordinate of the wall

<code>plane</code> - The plane coordinate of the wall