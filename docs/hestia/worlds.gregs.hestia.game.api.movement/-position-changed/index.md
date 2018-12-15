---
title: PositionChanged - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.movement</a> / <a href="./index.html">PositionChanged</a></div>

# PositionChanged

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">PositionChanged</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">IteratingSystem</span></code></div>

A base for entity movement position checks

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">PositionChanged</span><span class="symbol">(</span><span class="keyword">vararg</span> <span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$<init>(kotlin.Array((kotlin.reflect.KClass((com.artemis.Component)))))/classes">classes</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html"><span class="identifier">KClass</span></a><span class="symbol">&lt;</span><span class="keyword">out</span>&nbsp;<span class="identifier">Component</span><span class="symbol">&gt;</span><span class="symbol">)</span></code></div>

A base for entity movement position checks


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="changed.html">changed</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">changed</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Handles the position change


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-plane.html">getPlane</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">getPlane</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$getPlane(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$getPlane(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span></code></div>

Calculates the new plane (optional)


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-x.html">getX</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">getX</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$getX(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$getX(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Calculates the next x


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-y.html">getY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">getY</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$getY(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$getY(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Calculates the new y


</td>
</tr>
<tr>
<td markdown="1">

<a href="has-changed.html">hasChanged</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">hasChanged</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

Compares the current position with the new values


</td>
</tr>
<tr>
<td markdown="1">

<a href="process.html">process</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">process</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.PositionChanged$process(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../-chunk-changed/index.html">ChunkChanged</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">ChunkChanged</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">PositionChanged</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../-region-changed/index.html">RegionChanged</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">RegionChanged</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">PositionChanged</span></a></code></div>

</td>
</tr>
</tbody>
</table>
