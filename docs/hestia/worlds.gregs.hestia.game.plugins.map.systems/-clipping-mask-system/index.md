---
title: ClippingMaskSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.map.systems</a> / <a href="./index.html">ClippingMaskSystem</a></div>

# ClippingMaskSystem

<div class="signature"><code><span class="identifier">@Wire</span><span class="symbol">(</span>false<span class="symbol">)</span> <span class="keyword">class </span><span class="identifier">ClippingMaskSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.map/-clipping-masks/index.html"><span class="identifier">ClippingMasks</span></a></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">ClippingMaskSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="add-mask.html">addMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">addMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/mask">mask</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Adds clipping mask for a tile


</td>
</tr>
<tr>
<td markdown="1">

<a href="add-object.html">addObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">addObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/sizeX">sizeX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/sizeY">sizeY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/solid">solid</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/notAlternative">notAlternative</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Adds clipping masks for an object


</td>
</tr>
<tr>
<td markdown="1">

<a href="add-wall.html">addWall</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">addWall</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/solid">solid</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$addWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/notAlternative">notAlternative</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Adds clipping masks for a wall


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-mask.html">getMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/clipping">clipping</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.map/-clipping/index.html"><span class="identifier">Clipping</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Get's a clipping mask for the tile at <a href="../../worlds.gregs.hestia.game.api.map/-clipping-masks/get-mask.html#worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</a>, <a href="../../worlds.gregs.hestia.game.api.map/-clipping-masks/get-mask.html#worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</a>, <a href="../../worlds.gregs.hestia.game.api.map/-clipping-masks/get-mask.html#worlds.gregs.hestia.game.api.map.ClippingMasks$getMask(kotlin.Int, worlds.gregs.hestia.game.api.map.Clipping, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="remove-mask.html">removeMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">removeMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/mask">mask</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Removes clipping mask for a tile


</td>
</tr>
<tr>
<td markdown="1">

<a href="remove-object.html">removeObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">removeObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/sizeX">sizeX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/sizeY">sizeY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/solid">solid</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/notAlternative">notAlternative</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Removes clipping masks for an object


</td>
</tr>
<tr>
<td markdown="1">

<a href="remove-wall.html">removeWall</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">removeWall</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/solid">solid</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$removeWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/notAlternative">notAlternative</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Removes clipping masks for a wall


</td>
</tr>
<tr>
<td markdown="1">

<a href="set-mask.html">setMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">setMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/mask">mask</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Sets clipping mask for a tile


</td>
</tr>
<tr>
<td markdown="1">

<a href="set-object.html">setObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">setObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/sizeX">sizeX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/sizeY">sizeY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/solid">solid</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setObject(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Boolean, kotlin.Boolean)/notAlternative">notAlternative</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Sets clipping masks for an object


</td>
</tr>
<tr>
<td markdown="1">

<a href="set-wall.html">setWall</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">setWall</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/solid">solid</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.ClippingMaskSystem$setWall(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Boolean)/notAlternative">notAlternative</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Sets clipping masks for a wall


</td>
</tr>
</tbody>
</table>

### Companion Object Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-a-d-d_-m-a-s-k.html">ADD_MASK</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">ADD_MASK</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-r-e-m-o-v-e_-m-a-s-k.html">REMOVE_MASK</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">REMOVE_MASK</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-s-e-t_-m-a-s-k.html">SET_MASK</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">SET_MASK</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
</tbody>
</table>
