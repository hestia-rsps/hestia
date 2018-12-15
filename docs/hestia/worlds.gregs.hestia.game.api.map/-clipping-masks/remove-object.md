---
title: ClippingMasks.removeObject - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.map</a> / <a href="index.html">ClippingMasks</a> / <a href="./remove-object.html">removeObject</a></div>

# removeObject

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">removeObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/sizeX">sizeX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/sizeY">sizeY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/solid">solid</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.ClippingMasks$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/notAlternative">notAlternative</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Removes clipping masks for an object

### Parameters

<code>entityId</code> - The region entity id

<code>localX</code> - The local x coordinate of the object

<code>localY</code> - The local y coordinate of the object

<code>plane</code> - The plane coordinate of the object

<code>sizeX</code> - The width of the object

<code>sizeY</code> - The height of the object

<code>solid</code> - Whether the object is solid

<code>notAlternative</code> - Whether the objects clipping can be ignored on an alternative route