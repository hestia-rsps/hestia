---
title: MapSettingsSystem.apply - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.map.systems</a> / <a href="index.html">MapSettingsSystem</a> / <a href="./apply.html">apply</a></div>

# apply

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">apply</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/settings">settings</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-byte-array/index.html"><span class="identifier">ByteArray</span></a><span class="symbol">&gt;</span><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/rotation">rotation</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkX">chunkX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkY">chunkY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.map.systems.MapSettingsSystem$apply(kotlin.Int, kotlin.Array((kotlin.Array((kotlin.ByteArray)))), kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int)/chunkPlane">chunkPlane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">?</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Overrides <a href="../../worlds.gregs.hestia.game.api.map/-map-settings/apply.html">MapSettings.apply</a>

Adds masks to clipping based on the settings provided

### Parameters

<code>entityId</code> - The regions entity id

<code>settings</code> - The tile settings

<code>rotation</code> - The chunks rotation

<code>chunkX</code> - The chunks x coordinate

<code>chunkY</code> - The chunks y coordinate

<code>chunkPlane</code> - The chunks plane coordinate