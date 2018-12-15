---
title: Map - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.map</a> / <a href="./index.html">Map</a></div>

# Map

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">Map</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

Map
Area of clipping data

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">Map</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

Map
Area of clipping data


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="create-clipping.html">createClipping</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">createClipping</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.map.Map$createClipping(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="../-clipping/index.html"><span class="identifier">Clipping</span></a></code></div>

Creates or returns existing region clipping map


</td>
</tr>
<tr>
<td markdown="1">

<a href="create-projectile-map.html">createProjectileMap</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">createProjectileMap</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.map.Map$createProjectileMap(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="../-clipping/index.html"><span class="identifier">Clipping</span></a></code></div>

Creates or returns existing region projectile clipping map


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-clipping.html">getClipping</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">getClipping</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.map.Map$getClipping(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">)</span><span class="symbol">: </span><a href="../-clipping/index.html"><span class="identifier">Clipping</span></a><span class="symbol">?</span></code></div>

Get's a region's clipping map


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-projectile-map.html">getProjectileMap</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">getProjectileMap</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.map.Map$getProjectileMap(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">)</span><span class="symbol">: </span><a href="../-clipping/index.html"><span class="identifier">Clipping</span></a><span class="symbol">?</span></code></div>

Get's a region's projectile clipping map


</td>
</tr>
<tr>
<td markdown="1">

<a href="unload.html">unload</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">unload</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.map.Map$unload(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Clears map clipping data for region <a href="unload.html#worlds.gregs.hestia.game.api.map.Map$unload(kotlin.Int)/entityId">entityId</a>


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.map.systems/-map-system/index.html">MapSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MapSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Map</span></a></code></div>

MapSystem
Handles clipping data


</td>
</tr>
</tbody>
</table>
