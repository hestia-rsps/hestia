---
title: OutBoundPacket - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.events</a> / <a href="./index.html">OutBoundPacket</a></div>

# OutBoundPacket

<div class="signature"><code><span class="keyword">data</span> <span class="keyword">class </span><span class="identifier">OutBoundPacket</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">Event</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">OutBoundPacket</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.OutBoundPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Boolean)/entity">entity</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.OutBoundPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Boolean)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.OutBoundPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet.Builder, kotlin.Boolean)/close">close</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a>&nbsp;<span class="symbol">=</span>&nbsp;false<span class="symbol">)</span></code></div>

<div class="signature"><code><span class="identifier">OutBoundPacket</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.OutBoundPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Boolean)/entity">entity</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.OutBoundPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Boolean)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Packet</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.OutBoundPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Boolean)/close">close</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a>&nbsp;<span class="symbol">=</span>&nbsp;false<span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="close.html">close</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">close</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="entity.html">entity</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">entity</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="packet.html">packet</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">packet</span><span class="symbol">: </span><span class="identifier">Packet</span></code></div>

</td>
</tr>
</tbody>
</table>
