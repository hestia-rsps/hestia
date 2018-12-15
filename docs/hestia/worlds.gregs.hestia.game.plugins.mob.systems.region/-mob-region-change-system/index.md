---
title: MobRegionChangeSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.mob.systems.region</a> / <a href="./index.html">MobRegionChangeSystem</a></div>

# MobRegionChangeSystem

<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobRegionChangeSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.movement/-region-changed/index.html"><span class="identifier">RegionChanged</span></a></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">MobRegionChangeSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="changed-region.html">changedRegion</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">changedRegion</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.mob.systems.region.MobRegionChangeSystem$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.mob.systems.region.MobRegionChangeSystem$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/from">from</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.mob.systems.region.MobRegionChangeSystem$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/to">to</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Activates when an entity changes region


</td>
</tr>
</tbody>
</table>

### Inherited Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-region-changed/changed.html">changed</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">changed</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$changed(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Handles the position change


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-region-changed/get-plane.html">getPlane</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">getPlane</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$getPlane(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$getPlane(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.movement/-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span></code></div>

Calculates the new plane (optional)


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-region-changed/get-x.html">getX</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">getX</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$getX(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$getX(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.movement/-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Calculates the next x


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-region-changed/get-y.html">getY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">getY</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$getY(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$getY(worlds.gregs.hestia.game.plugins.core.components.map.Position, worlds.gregs.hestia.game.api.movement.Shift)/shift">shift</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.movement/-shift/index.html"><span class="identifier">Shift</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Calculates the new y


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-region-changed/has-changed.html">hasChanged</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">hasChanged</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$hasChanged(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

Compares the current position with the new values


</td>
</tr>
</tbody>
</table>
