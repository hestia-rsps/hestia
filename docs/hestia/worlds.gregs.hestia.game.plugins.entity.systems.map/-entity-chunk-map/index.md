---
title: EntityChunkMap - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.entity.systems.map</a> / <a href="./index.html">EntityChunkMap</a></div>

# EntityChunkMap

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">EntityChunkMap</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">EntityChunkMap</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="add.html">add</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">add</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$add(kotlin.Int, kotlin.Int)/hash">hash</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$add(kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Adds an entity id to a chunk
Creates the chunk if it hasn't be created yet.


</td>
</tr>
<tr>
<td markdown="1">

<a href="get.html">get</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">get</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$get(kotlin.Int)/hash">hash</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span><span class="symbol">?</span></code></div>

Returns the list of entities at a chunk


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-all.html">getAll</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getAll</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$getAll(kotlin.ranges.IntRange, kotlin.ranges.IntRange, kotlin.Int)/xRange">xRange</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html"><span class="identifier">IntRange</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$getAll(kotlin.ranges.IntRange, kotlin.ranges.IntRange, kotlin.Int)/yRange">yRange</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/-int-range/index.html"><span class="identifier">IntRange</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$getAll(kotlin.ranges.IntRange, kotlin.ranges.IntRange, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span></code></div>

Returns a list of all entities in the range of chunks specified


</td>
</tr>
<tr>
<td markdown="1">

<a href="remove.html">remove</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">remove</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$remove(kotlin.Int, kotlin.Int)/hash">hash</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkMap$remove(kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Removes entity id from a chunk


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.mob.systems.chunk.map/-mob-chunk-map/index.html">MobChunkMap</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobChunkMap</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">EntityChunkMap</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.player.systems.chunk.map/-player-chunk-map/index.html">PlayerChunkMap</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerChunkMap</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">EntityChunkMap</span></a></code></div>

</td>
</tr>
</tbody>
</table>
