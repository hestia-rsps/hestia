---
title: Sync - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.update</a> / <a href="./index.html">Sync</a></div>

# Sync

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">Sync</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">IteratingSystem</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">Sync</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$<init>(com.artemis.Aspect.Builder)/builder">builder</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="global.html">global</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">global</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$global(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$global(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/global">global</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$global(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$global(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/update">update</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Process a single global entity


</td>
</tr>
<tr>
<td markdown="1">

<a href="local.html">local</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">local</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/local">local</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/update">update</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Process a single local entity


</td>
</tr>
<tr>
<td markdown="1">

<a href="skip.html">skip</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">skip</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$skip(kotlin.collections.List((kotlin.Int)), kotlin.collections.List((kotlin.Int)))/locals">locals</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$skip(kotlin.collections.List((kotlin.Int)), kotlin.collections.List((kotlin.Int)))/globals">globals</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Skip the processing and write an empty packet


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update/-base-entity-sync-system/index.html">BaseEntitySyncSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">BaseEntitySyncSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Sync</span></a></code></div>

</td>
</tr>
</tbody>
</table>
