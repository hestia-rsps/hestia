---
title: GamePacketInboundHandler - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.network</a> / <a href="./index.html">GamePacketInboundHandler</a></div>

# GamePacketInboundHandler

<div class="signature"><code><span class="keyword">class </span><span class="identifier">GamePacketInboundHandler</span>&nbsp;<span class="symbol">:</span>&nbsp;<br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="identifier">PacketInboundHandler</span><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game/-packet-handler/index.html"><span class="identifier">PacketHandler</span></a><span class="symbol">&gt;</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">GamePacketInboundHandler</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.GamePacketInboundHandler$<init>(com.artemis.World)/world">world</span><span class="symbol">:</span>&nbsp;<span class="identifier">World</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="disconnect.html">disconnect</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">disconnect</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.GamePacketInboundHandler$disconnect(world.gregs.hestia.core.network.Session)/session">session</span><span class="symbol">:</span>&nbsp;<span class="identifier">Session</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="process.html">process</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">process</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.GamePacketInboundHandler$process(world.gregs.hestia.core.network.Session, worlds.gregs.hestia.game.PacketHandler, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/session">session</span><span class="symbol">:</span>&nbsp;<span class="identifier">Session</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.GamePacketInboundHandler$process(world.gregs.hestia.core.network.Session, worlds.gregs.hestia.game.PacketHandler, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/handler">handler</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game/-packet-handler/index.html"><span class="identifier">PacketHandler</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.GamePacketInboundHandler$process(world.gregs.hestia.core.network.Session, worlds.gregs.hestia.game.PacketHandler, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Packet</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.GamePacketInboundHandler$process(world.gregs.hestia.core.network.Session, worlds.gregs.hestia.game.PacketHandler, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/length">length</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>
