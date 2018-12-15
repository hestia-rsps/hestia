---
title: TileClipping - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.map</a> / <a href="./index.html">TileClipping</a></div>

# TileClipping

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">TileClipping</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

TileClipping
Checks whether the next tile to step on is free (unclipped)

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">TileClipping</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

TileClipping
Checks whether the next tile to step on is free (unclipped)


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="is-tile-walkable.html">isTileWalkable</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">isTileWalkable</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.TileClipping$isTileWalkable(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.TileClipping$isTileWalkable(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.TileClipping$isTileWalkable(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.TileClipping$isTileWalkable(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/dir">dir</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.TileClipping$isTileWalkable(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/sizeX">sizeX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.map.TileClipping$isTileWalkable(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/sizeY">sizeY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.map.systems/-tile-check-system/index.html">TileCheckSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">TileCheckSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">TileClipping</span></a></code></div>

TileClipping
Checks whether the next tile to step on is free (unclipped)


</td>
</tr>
</tbody>
</table>
