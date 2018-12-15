---
title: PlayerChunkMapSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.player.systems.chunk.map</a> / <a href="./index.html">PlayerChunkMapSystem</a></div>

# PlayerChunkMapSystem

<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerChunkMapSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.entity.systems.map/-entity-chunk-system/index.html"><span class="identifier">EntityChunkSystem</span></a></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">PlayerChunkMapSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="get-map.html">getMap</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getMap</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="../../worlds.gregs.hestia.game.plugins.entity.systems.map/-entity-chunk-map/index.html"><span class="identifier">EntityChunkMap</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inherited Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.entity.systems.map/-entity-chunk-system/get.html">get</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">get</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem$get(worlds.gregs.hestia.game.plugins.core.components.map.Position)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span></code></div>

Returns a list of all the entities local to a chunk

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">get</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem$get(kotlin.Int, kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem$get(kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span></code></div>

Returns a list of all the entities in a region


</td>
</tr>
</tbody>
</table>
