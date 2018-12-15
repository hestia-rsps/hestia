---
title: Regions - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.region</a> / <a href="./index.html">Regions</a></div>

# Regions

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">Regions</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api/-subscription-system/index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

Regions
Holds list of regions

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">Regions</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.Regions$<init>(com.artemis.Aspect.Builder)/builder">builder</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">)</span></code></div>

Regions
Holds list of regions


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
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">contains</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.Regions$contains(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

Checks if region <a href="contains.html#worlds.gregs.hestia.game.api.region.Regions$contains(kotlin.Int)/regionId">regionId</a> exists


</td>
</tr>
<tr>
<td markdown="1">

<a href="get-entity-id.html">getEntityId</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">getEntityId</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.Regions$getEntityId(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span></code></div>

Returns the id of the region with <a href="get-entity-id.html#worlds.gregs.hestia.game.api.region.Regions$getEntityId(kotlin.Int)/regionId">regionId</a>


</td>
</tr>
</tbody>
</table>

### Inherited Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api/-subscription-system/check-processing.html">checkProcessing</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">checkProcessing</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api/-subscription-system/process-system.html">processSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">processSystem</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.region.systems/-regions-system/index.html">RegionsSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionsSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Regions</span></a></code></div>

RegionsSystem
Keeps track of regions for easy access to a regions entity id


</td>
</tr>
</tbody>
</table>
