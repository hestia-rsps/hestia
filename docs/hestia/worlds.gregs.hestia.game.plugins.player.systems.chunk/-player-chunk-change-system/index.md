---
title: PlayerChunkChangeSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.player.systems.chunk</a> / <a href="./index.html">PlayerChunkChangeSystem</a></div>

# PlayerChunkChangeSystem

<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerChunkChangeSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.movement/-chunk-changed/index.html"><span class="identifier">ChunkChanged</span></a></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">PlayerChunkChangeSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="changed-chunk.html">changedChunk</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">changedChunk</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkChangeSystem$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkChangeSystem$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/from">from</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.player.systems.chunk.PlayerChunkChangeSystem$changedChunk(kotlin.Int, kotlin.Int, kotlin.Int)/to">to</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Activates when an entity changes chunk


</td>
</tr>
</tbody>
</table>

### Inherited Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-chunk-changed/changed.html">changed</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">changed</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Handles the position change


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-chunk-changed/get-plane.html">getPlane</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">getPlane</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$getPlane(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$getPlane(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.movement/-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span></code></div>

Calculates the new plane (optional)


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-chunk-changed/get-x.html">getX</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">getX</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$getX(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$getX(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.movement/-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Calculates the next x


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-chunk-changed/get-y.html">getY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">getY</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$getY(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$getY(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.movement/-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Calculates the new y


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-chunk-changed/has-changed.html">hasChanged</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">hasChanged</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.ChunkChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

Compares the current position with the new values


</td>
</tr>
</tbody>
</table>
