---
title: LandSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.land.systems</a> / <a href="./index.html">LandSystem</a></div>

# LandSystem

<div class="signature"><code><span class="identifier">@Wire</span><span class="symbol">(</span>false<span class="symbol">)</span> <span class="keyword">class </span><span class="identifier">LandSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.land/-land/index.html"><span class="identifier">Land</span></a></code></div>

RegionObjectSystem
TODO requires a rewrite

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">LandSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

RegionObjectSystem
TODO requires a rewrite


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="add-map-object.html">addMapObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">addMapObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addMapObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addMapObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/object">object</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addMapObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addMapObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="add-object.html">addObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">addObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int, kotlin.Int)/object">object</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$addObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="clear.html">clear</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">clear</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="contains-object.html">containsObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">containsObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$containsObject(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$containsObject(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position)/id">id</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$containsObject(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position)/tile">tile</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-object.html">getObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getObject(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getObject(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position)/id">id</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getObject(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position)/tile">tile</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">?</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-objects.html">getObjects</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getObjects</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getObjects(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getObjects(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getObjects(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/x">x</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getObjects(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/y">y</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">?</span><span class="symbol">&gt;</span><span class="symbol">?</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-removed-objects.html">getRemovedObjects</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getRemovedObjects</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getRemovedObjects(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">&gt;</span><span class="symbol">?</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-spawned-object.html">getSpawnedObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getSpawnedObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getSpawnedObject(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getSpawnedObject(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int)/tile">tile</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getSpawnedObject(kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int)/id">id</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">?</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-spawned-objects.html">getSpawnedObjects</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getSpawnedObjects</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$getSpawnedObjects(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html"><span class="identifier">MutableList</span></a><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">&gt;</span><span class="symbol">?</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="remove-map-object.html">removeMapObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">removeMapObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeMapObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeMapObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/object">object</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeMapObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeMapObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="remove-object.html">removeObject</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">removeObject</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/object">object</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/localX">localX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject, kotlin.Int, kotlin.Int)/localY">localY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">removeObject</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$removeObject(kotlin.Int, worlds.gregs.hestia.game.map.GameObject)/object">object</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.map/-game-object/index.html"><span class="identifier">GameObject</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="unload.html">unload</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">unload</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.land.systems.LandSystem$unload(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Clears objects for region <a href="../../worlds.gregs.hestia.game.api.land/-land/unload.html#worlds.gregs.hestia.game.api.land.Land$unload(kotlin.Int)/entityId">entityId</a>


</td>
</tr>
</tbody>
</table>
