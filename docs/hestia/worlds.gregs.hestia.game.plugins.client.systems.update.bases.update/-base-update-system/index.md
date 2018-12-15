---
title: BaseUpdateSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.client.systems.update.bases.update</a> / <a href="./index.html">BaseUpdateSystem</a></div>

# BaseUpdateSystem

<div class="signature"><code><span class="keyword">interface </span><span class="identifier">BaseUpdateSystem</span></code></div>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="flags.html">flags</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">flags</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.update/-update-flag/index.html"><span class="identifier">UpdateFlag</span></a><span class="symbol">&gt;</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="is-global.html">isGlobal</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">isGlobal</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$isGlobal(worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$isGlobal(worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/update">update</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="is-local.html">isLocal</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">isLocal</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$isLocal(worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/type">type</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-display-flag/index.html"><span class="identifier">DisplayFlag</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$isLocal(worlds.gregs.hestia.game.update.DisplayFlag, kotlin.Boolean)/update">update</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="update.html">update</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">update</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$update(kotlin.Int, kotlin.Int, world.gregs.hestia.core.network.packets.Packet.Builder, com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.core.components.entity.Created)), kotlin.Boolean)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$update(kotlin.Int, kotlin.Int, world.gregs.hestia.core.network.packets.Packet.Builder, com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.core.components.entity.Created)), kotlin.Boolean)/local">local</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$update(kotlin.Int, kotlin.Int, world.gregs.hestia.core.network.packets.Packet.Builder, com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.core.components.entity.Created)), kotlin.Boolean)/data">data</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$update(kotlin.Int, kotlin.Int, world.gregs.hestia.core.network.packets.Packet.Builder, com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.core.components.entity.Created)), kotlin.Boolean)/createdMapper">createdMapper</span><span class="symbol">:</span>&nbsp;<span class="identifier">ComponentMapper</span><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.plugins.core.components.entity/-created/index.html"><span class="identifier">Created</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.BaseUpdateSystem$update(kotlin.Int, kotlin.Int, world.gregs.hestia.core.network.packets.Packet.Builder, com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.core.components.entity.Created)), kotlin.Boolean)/added">added</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.update/-base-mob-update-system/index.html">BaseMobUpdateSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">BaseMobUpdateSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-mob-sync-system/index.html"><span class="identifier">BaseMobSyncSystem</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="./index.html"><span class="identifier">BaseUpdateSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.update/-base-player-update-system/index.html">BasePlayerUpdateSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">BasePlayerUpdateSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.client.systems.update.bases.update.sync/-base-player-sync-system/index.html"><span class="identifier">BasePlayerSyncSystem</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<a href="./index.html"><span class="identifier">BaseUpdateSystem</span></a></code></div>

</td>
</tr>
</tbody>
</table>
