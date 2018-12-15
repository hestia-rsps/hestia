---
title: Sync.local - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.update</a> / <a href="index.html">Sync</a> / <a href="./local.html">local</a></div>

# local

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">local</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/local">local</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.Sync$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/update">update</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Process a single local entity

### Parameters

<code>entityId</code> - client entity

<code>local</code> - local entity

<code>type</code> - <a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html">DisplayFlag</a>

<code>update</code> - if requires an update