---
title: MapSettingsSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.map.systems</a> / <a href="./index.html">MapSettingsSystem</a></div>

# MapSettingsSystem

<div class="signature"><code><span class="identifier">@Wire</span><span class="symbol">(</span>false<span class="symbol">)</span> <span class="keyword">class </span><span class="identifier">MapSettingsSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.api.map/-map-settings/index.html"><span class="identifier">MapSettings</span></a></code></div>

MapSettingsSystem
Adds clipping masks using <a href="../-clipping-mask-system/index.html">ClippingMaskSystem</a> from the <a href="../../worlds.gregs.hestia.game.plugins.region.systems.load/-region-file-system/index.html">worlds.gregs.hestia.game.plugins.region.systems.load.RegionFileSystem</a> data

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">MapSettingsSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

MapSettingsSystem
Adds clipping masks using <a href="../-clipping-mask-system/index.html">ClippingMaskSystem</a> from the <a href="../../worlds.gregs.hestia.game.plugins.region.systems.load/-region-file-system/index.html">worlds.gregs.hestia.game.plugins.region.systems.load.RegionFileSystem</a> data


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="apply.html">apply</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">apply</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/settings">settings</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><span class="symbol">&gt;</span><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkPlane">chunkPlane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Adds masks to clipping based on the settings provided


</td>
</tr>
<tr>
<td markdown="1">

<a href="load.html">load</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">load</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$load(kotlin.ByteArray)/mapContainerData">mapContainerData</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><span class="symbol">&gt;</span><span class="symbol">&gt;</span></code></div>

Loads the region tile settings


</td>
</tr>
</tbody>
</table>
