---
title: RegionChanged.changedRegion - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.movement</a> / <a href="index.html">RegionChanged</a> / <a href="./changed-region.html">changedRegion</a></div>

# changedRegion

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">changedRegion</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/from">from</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.movement.RegionChanged$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/to">to</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Activates when an entity changes region

### Parameters

<code>entityId</code> - the id of the entity which changed region

<code>from</code> - the region id they were in

<code>to</code> - the region id they are now in