---
title: LoginServerInboundHandler - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.network</a> / <a href="./index.html">LoginServerInboundHandler</a></div>

# LoginServerInboundHandler

<div class="signature"><code><span class="keyword">class </span><span class="identifier">LoginServerInboundHandler</span>&nbsp;<span class="symbol">:</span>&nbsp;<br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="identifier">PacketInboundHandler</span><span class="symbol">&lt;</span><span class="identifier">InboundPacket</span><span class="symbol">&gt;</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">LoginServerInboundHandler</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$<init>(world.gregs.hestia.core.WorldDetails, worlds.gregs.hestia.network.WorldChangeListener, world.gregs.hestia.core.services.load.PacketMap((world.gregs.hestia.core.network.packets.InboundPacket)))/info">info</span><span class="symbol">:</span>&nbsp;<span class="identifier">WorldDetails</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$<init>(world.gregs.hestia.core.WorldDetails, worlds.gregs.hestia.network.WorldChangeListener, world.gregs.hestia.core.services.load.PacketMap((world.gregs.hestia.core.network.packets.InboundPacket)))/listener">listener</span><span class="symbol">:</span>&nbsp;<a href="../-world-change-listener/index.html"><span class="identifier">WorldChangeListener</span></a><span class="symbol">?</span>&nbsp;<span class="symbol">=</span>&nbsp;null<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$<init>(world.gregs.hestia.core.WorldDetails, worlds.gregs.hestia.network.WorldChangeListener, world.gregs.hestia.core.services.load.PacketMap((world.gregs.hestia.core.network.packets.InboundPacket)))/packets">packets</span><span class="symbol">:</span>&nbsp;<span class="identifier">PacketMap</span><span class="symbol">&lt;</span><span class="identifier">InboundPacket</span><span class="symbol">&gt;</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="connect.html">connect</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">connect</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$connect(world.gregs.hestia.core.network.Session)/session">session</span><span class="symbol">:</span>&nbsp;<span class="identifier">Session</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="disconnect.html">disconnect</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">disconnect</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$disconnect(world.gregs.hestia.core.network.Session)/session">session</span><span class="symbol">:</span>&nbsp;<span class="identifier">Session</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="process.html">process</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">process</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$process(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/session">session</span><span class="symbol">:</span>&nbsp;<span class="identifier">Session</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$process(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/handler">handler</span><span class="symbol">:</span>&nbsp;<span class="identifier">InboundPacket</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$process(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Packet</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginServerInboundHandler$process(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/length">length</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>
