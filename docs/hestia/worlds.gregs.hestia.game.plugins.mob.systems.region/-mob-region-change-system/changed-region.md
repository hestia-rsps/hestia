---
title: MobRegionChangeSystem.changedRegion - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.mob.systems.region</a> / <a href="index.html">MobRegionChangeSystem</a> / <a href="./changed-region.html">changedRegion</a></div>

# changedRegion

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">changedRegion</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.mob.systems.region.MobRegionChangeSystem$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.mob.systems.region.MobRegionChangeSystem$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/from">from</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.mob.systems.region.MobRegionChangeSystem$changedRegion(kotlin.Int, kotlin.Int, kotlin.Int)/to">to</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Overrides <a href="../../worlds.gregs.hestia.game.api.movement/-region-changed/changed-region.html">RegionChanged.changedRegion</a>

Activates when an entity changes region

### Parameters

<code>entityId</code> - the id of the entity which changed region

<code>from</code> - the region id they were in

<code>to</code> - the region id they are now in