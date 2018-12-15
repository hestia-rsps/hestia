---
title: ChunkRotationSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.region.systems.load</a> / <a href="./index.html">ChunkRotationSystem</a></div>

# ChunkRotationSystem

<div class="signature"><code><span class="keyword">class </span><span class="identifier">ChunkRotationSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

ChunkRotationSystem
Rotation algorithms used by <a href="#">MapSettingsSystem</a> &amp; <a href="#">MapObjectSystem</a>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">ChunkRotationSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

ChunkRotationSystem
Rotation algorithms used by <a href="#">MapSettingsSystem</a> &amp; <a href="#">MapObjectSystem</a>


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="translate-x.html">translateX</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">translateX</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateX(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateX(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateX(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/mapRotation">mapRotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateX(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/sizeX">sizeX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateX(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/sizeY">sizeY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateX(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/objectRotation">objectRotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Get's the X coordinate of a mask or object which have been rotated <a href="translate-x.html#worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateX(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/mapRotation">mapRotation</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="translate-y.html">translateY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">translateY</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateY(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateY(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateY(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/mapRotation">mapRotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateY(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/sizeX">sizeX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateY(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/sizeY">sizeY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-short/index.html"><span class="identifier">Short</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateY(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/objectRotation">objectRotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Get's the Y coordinate of a mask or object which have been rotated <a href="translate-y.html#worlds.gregs.hestia.game.plugins.region.systems.load.ChunkRotationSystem$translateY(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Short, kotlin.Short, kotlin.Int)/mapRotation">mapRotation</a>


</td>
</tr>
</tbody>
</table>
