---
title: ClientPacket - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.events</a> / <a href="./index.html">ClientPacket</a></div>

# ClientPacket

<div class="signature"><code><span class="keyword">data</span> <span class="keyword">class </span><span class="identifier">ClientPacket</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">Event</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">ClientPacket</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.events.ClientPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.events.ClientPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Packet</span><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.events.ClientPacket$<init>(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/length">length</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="entity-id.html">entityId</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">entityId</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="length.html">length</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">length</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

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
