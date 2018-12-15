---
title: ClippingImpl - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.map.components</a> / <a href="./index.html">ClippingImpl</a></div>

# ClippingImpl

<div class="signature"><code><span class="identifier">@PooledWeaver</span> <span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">ClippingImpl</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.map/-clipping/index.html"><span class="identifier">Clipping</span></a></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">ClippingImpl</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

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
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">addMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/value">value</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Adds a clipping mask to tile at <a href="../../worlds.gregs.hestia.game.api.map/-clipping/add-mask.html#worlds.gregs.hestia.game.api.map.Clipping$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</a>, <a href="../../worlds.gregs.hestia.game.api.map/-clipping/add-mask.html#worlds.gregs.hestia.game.api.map.Clipping$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</a>, <a href="../../worlds.gregs.hestia.game.api.map/-clipping/add-mask.html#worlds.gregs.hestia.game.api.map.Clipping$addMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-mask.html">getMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">getMask</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$getMask(kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$getMask(kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$getMask(kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Returns clipping masks for a tile


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-masks.html">getMasks</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">getMasks</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$getMasks(kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html"><span class="identifier">IntArray</span></a><span class="symbol">&gt;</span></code></div>

Returns clipping masks for an entire plane


</td>
</tr>
<tr>
<td markdown="1">

<a href="remove-mask.html">removeMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">removeMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/value">value</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Removes the clipping mask of the tile at <a href="../../worlds.gregs.hestia.game.api.map/-clipping/remove-mask.html#worlds.gregs.hestia.game.api.map.Clipping$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</a>, <a href="../../worlds.gregs.hestia.game.api.map/-clipping/remove-mask.html#worlds.gregs.hestia.game.api.map.Clipping$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</a>, <a href="../../worlds.gregs.hestia.game.api.map/-clipping/remove-mask.html#worlds.gregs.hestia.game.api.map.Clipping$removeMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="set-mask.html">setMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">setMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.components.ClippingImpl$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/value">value</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Sets the clipping mask of tile at <a href="../../worlds.gregs.hestia.game.api.map/-clipping/set-mask.html#worlds.gregs.hestia.game.api.map.Clipping$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</a>, <a href="../../worlds.gregs.hestia.game.api.map/-clipping/set-mask.html#worlds.gregs.hestia.game.api.map.Clipping$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</a>, <a href="../../worlds.gregs.hestia.game.api.map/-clipping/set-mask.html#worlds.gregs.hestia.game.api.map.Clipping$setMask(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</a>


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../-clipping-map/index.html">ClippingMap</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClippingMap</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">ClippingImpl</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../-projectile-clipping/index.html">ProjectileClipping</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ProjectileClipping</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">ClippingImpl</span></a></code></div>

</td>
</tr>
</tbody>
</table>
