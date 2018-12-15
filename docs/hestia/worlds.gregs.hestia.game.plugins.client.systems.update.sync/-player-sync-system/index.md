---
title: PlayerSyncSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.client.systems.update.sync</a> / <a href="./index.html">PlayerSyncSystem</a></div>

# PlayerSyncSystem

<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerSyncSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-player-sync-system/index.html"><span class="identifier">BasePlayerSyncSystem</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../../worlds.gregs.hestia.game.api.update/-entity-sync/index.html"><span class="identifier">EntitySync</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../../worlds.gregs.hestia.game.api.update/-player-sync/index.html"><span class="identifier">PlayerSync</span></a></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">PlayerSyncSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="global-handlers.html">globalHandlers</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">globalHandlers</span><span class="symbol">: </span><a href="http://docs.oracle.com/javase/9/docs/api/java/util/HashMap.html"><span class="identifier">HashMap</span></a><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">,</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">.</span><span class="symbol">(</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a><span class="symbol">&gt;</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="local-handlers.html">localHandlers</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">localHandlers</span><span class="symbol">: </span><a href="http://docs.oracle.com/javase/9/docs/api/java/util/HashMap.html"><span class="identifier">HashMap</span></a><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">,</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">.</span><span class="symbol">(</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a><span class="symbol">&gt;</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="begin.html">begin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">begin</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="global.html">global</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">global</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$global(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$global(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/global">global</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$global(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$global(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/update">update</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Process a single global entity


</td>
</tr>
<tr>
<td markdown="1">

<a href="local.html">local</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">local</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/local">local</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$local(kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/update">update</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Process a single local entity


</td>
</tr>
<tr>
<td markdown="1">

<a href="middle.html">middle</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">middle</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="process.html">process</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">process</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$process(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="skip.html">skip</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">skip</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$skip(kotlin.collections.List((kotlin.Int)), kotlin.collections.List((kotlin.Int)))/locals">locals</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.sync.PlayerSyncSystem$skip(kotlin.collections.List((kotlin.Int)), kotlin.collections.List((kotlin.Int)))/globals">globals</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Skip the processing and write an empty packet


</td>
</tr>
</tbody>
</table>

### Inherited Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.update/-player-sync/add-global.html">addGlobal</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">addGlobal</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="keyword">vararg</span> <span class="parameterName" id="worlds.gregs.hestia.game.api.update.PlayerSync$addGlobal(kotlin.Array((worlds.gregs.hestia.game.update.DisplayFlag)), kotlin.Function3((world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, , kotlin.Unit)))/stages">stages</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.PlayerSync$addGlobal(kotlin.Array((worlds.gregs.hestia.game.update.DisplayFlag)), kotlin.Function3((world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, , kotlin.Unit)))/handler">handler</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">.</span><span class="symbol">(</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.update/-entity-sync/add-local.html">addLocal</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">addLocal</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="keyword">vararg</span> <span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$addLocal(kotlin.Array((worlds.gregs.hestia.game.update.DisplayFlag)), kotlin.Function3((world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, , kotlin.Unit)))/stages">stages</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$addLocal(kotlin.Array((worlds.gregs.hestia.game.update.DisplayFlag)), kotlin.Function3((world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, , kotlin.Unit)))/handler">handler</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">.</span><span class="symbol">(</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-player-sync-system/count.html">count</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">count</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BasePlayerSyncSystem$count(kotlin.collections.List((kotlin.Int)), kotlin.collections.Map((kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)))/locals">locals</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BasePlayerSyncSystem$count(kotlin.collections.List((kotlin.Int)), kotlin.collections.Map((kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)))/stages">stages</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html"><span class="identifier">Map</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">&gt;</span><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Calculate the number of local entities after some have been removed


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-player-sync-system/globals.html">globals</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">globals</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BasePlayerSyncSystem$globals(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="http://docs.oracle.com/javase/9/docs/api/java/util/ArrayList.html"><span class="identifier">ArrayList</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span></code></div>

Get global entities


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.update/-player-sync/invoke-global.html">invokeGlobal</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">invokeGlobal</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.PlayerSync$invokeGlobal(worlds.gregs.hestia.game.update.DisplayFlag, world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.PlayerSync$invokeGlobal(worlds.gregs.hestia.game.update.DisplayFlag, world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.PlayerSync$invokeGlobal(worlds.gregs.hestia.game.update.DisplayFlag, world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.PlayerSync$invokeGlobal(worlds.gregs.hestia.game.update.DisplayFlag, world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int)/global">global</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.update/-entity-sync/invoke-local.html">invokeLocal</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">invokeLocal</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$invokeLocal(world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$invokeLocal(world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$invokeLocal(world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)/local">local</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.update.EntitySync$invokeLocal(world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.update.DisplayFlag)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-player-sync-system/locals.html">locals</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">locals</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BasePlayerSyncSystem$locals(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="http://docs.oracle.com/javase/9/docs/api/java/util/ArrayList.html"><span class="identifier">ArrayList</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span></code></div>

Get local entities


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-player-sync-system/packet.html">packet</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">packet</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync.BasePlayerSyncSystem$packet(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><span class="identifier">Builder</span></code></div>

Get the packet to write too


</td>
</tr>
</tbody>
</table>
