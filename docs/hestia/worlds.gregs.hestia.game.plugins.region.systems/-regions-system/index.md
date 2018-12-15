---
title: RegionsSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.region.systems</a> / <a href="./index.html">RegionsSystem</a></div>

# RegionsSystem

<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionsSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.region/-regions/index.html"><span class="identifier">Regions</span></a></code></div>

RegionsSystem
Keeps track of regions for easy access to a regions entity id

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">RegionsSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

RegionsSystem
Keeps track of regions for easy access to a regions entity id


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="contains.html">contains</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">contains</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.RegionsSystem$contains(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

Checks if region <a href="../../worlds.gregs.hestia.game.api.region/-regions/contains.html#worlds.gregs.hestia.game.api.region.Regions$contains(kotlin.Int)/regionId">regionId</a> exists


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-entity-id.html">getEntityId</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getEntityId</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.RegionsSystem$getEntityId(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span></code></div>

Returns the id of the region with <a href="../../worlds.gregs.hestia.game.api.region/-regions/get-entity-id.html#worlds.gregs.hestia.game.api.region.Regions$getEntityId(kotlin.Int)/regionId">regionId</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="inserted.html">inserted</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">inserted</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.RegionsSystem$inserted(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="removed.html">removed</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">removed</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.region.systems.RegionsSystem$removed(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>
