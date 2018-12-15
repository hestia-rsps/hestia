---
title: EntitySync - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.update</a> / <a href="./index.html">EntitySync</a></div>

# EntitySync

<div class="signature"><code><span class="keyword">interface </span><span class="identifier">EntitySync</span></code></div>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="local-handlers.html">localHandlers</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">val </span><span class="identifier">localHandlers</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-hash-map/index.html"><span class="identifier">HashMap</span></a><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">,</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">.</span><span class="symbol">(</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a><span class="symbol">&gt;</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="add-local.html">addLocal</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">addLocal</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="keyword">vararg</span> <span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$addLocal(kotlin.Array((worlds.gregs.hestia.game.update.DisplayFlag)), kotlin.Function3((world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, , kotlin.Unit)))/stages">stages</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$addLocal(kotlin.Array((worlds.gregs.hestia.game.update.DisplayFlag)), kotlin.Function3((world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, , kotlin.Unit)))/handler">handler</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">.</span><span class="symbol">(</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="invoke-local.html">invokeLocal</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">invokeLocal</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$invokeLocal(world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$invokeLocal(world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$invokeLocal(world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)/local">local</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$invokeLocal(world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.sync/-mob-sync-system/index.html">MobSyncSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobSyncSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-mob-sync-system/index.html"><span class="identifier">BaseMobSyncSystem</span></a><span class="symbol">, </span><a href="./index.html"><span class="identifier">EntitySync</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.sync/-player-sync-system/index.html">PlayerSyncSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerSyncSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-player-sync-system/index.html"><span class="identifier">BasePlayerSyncSystem</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="./index.html"><span class="identifier">EntitySync</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../-player-sync/index.html"><span class="identifier">PlayerSync</span></a></code></div>

</td>
</tr>
</tbody>
</table>
