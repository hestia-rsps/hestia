---
title: LoginAttempt - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.network</a> / <a href="./index.html">LoginAttempt</a></div>

# LoginAttempt

<div class="signature"><code><span class="keyword">class </span><span class="identifier">LoginAttempt</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">LoginRequestListener</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">LoginAttempt</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.LoginAttempt$<init>(com.artemis.World, world.gregs.hestia.core.network.codec.inbound.InboundHandler)/w">w</span><span class="symbol">:</span>&nbsp;<span class="identifier">World</span><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.network.LoginAttempt$<init>(com.artemis.World, world.gregs.hestia.core.network.codec.inbound.InboundHandler)/worldHandler">worldHandler</span><span class="symbol">:</span>&nbsp;<span class="identifier">InboundHandler</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="login.html">login</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">login</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginAttempt$login(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.String, kotlin.Long, kotlin.Long)/session">session</span><span class="symbol">:</span>&nbsp;<span class="identifier">Session</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginAttempt$login(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.String, kotlin.Long, kotlin.Long)/handler">handler</span><span class="symbol">:</span>&nbsp;<span class="identifier">InboundPacket</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginAttempt$login(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.String, kotlin.Long, kotlin.Long)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Packet</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginAttempt$login(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.String, kotlin.Long, kotlin.Long)/password">password</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html"><span class="identifier">String</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginAttempt$login(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.String, kotlin.Long, kotlin.Long)/serverSeed">serverSeed</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html"><span class="identifier">Long</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.LoginAttempt$login(world.gregs.hestia.core.network.Session, world.gregs.hestia.core.network.packets.InboundPacket, world.gregs.hestia.core.network.packets.Packet, kotlin.String, kotlin.Long, kotlin.Long)/clientSeed">clientSeed</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html"><span class="identifier">Long</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>
