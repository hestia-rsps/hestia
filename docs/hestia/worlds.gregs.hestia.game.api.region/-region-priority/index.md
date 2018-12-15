---
title: RegionPriority - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.region</a> / <a href="./index.html">RegionPriority</a></div>

# RegionPriority

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">RegionPriority</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

RegionPriority
Keeps count of prioritised entities (players)

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">RegionPriority</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

RegionPriority
Keeps count of prioritised entities (players)


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
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">add</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionPriority$add(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Adds priority to the region <a href="add.html#worlds.gregs.hestia.game.api.region.RegionPriority$add(kotlin.Int)/regionId">regionId</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="remove.html">remove</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">remove</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.region.RegionPriority$remove(kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Removes priority from the region <a href="remove.html#worlds.gregs.hestia.game.api.region.RegionPriority$remove(kotlin.Int)/regionId">regionId</a>


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.region.systems/-region-priority-system/index.html">RegionPrioritySystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionPrioritySystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">RegionPriority</span></a></code></div>

</td>
</tr>
</tbody>
</table>
