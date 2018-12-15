---
title: PacketHandler - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game</a> / <a href="./index.html">PacketHandler</a></div>

# PacketHandler

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">PacketHandler</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

PacketHandler
Template for systems handling inbound <a href="../../worlds.gregs.hestia.game.events/-client-packet/index.html">worlds.gregs.hestia.game.events.ClientPacket</a>s

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">PacketHandler</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

PacketHandler
Template for systems handling inbound <a href="../../worlds.gregs.hestia.game.events/-client-packet/index.html">worlds.gregs.hestia.game.events.ClientPacket</a>s


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="handle.html">handle</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">handle</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.PacketHandler$handle(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.PacketHandler$handle(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Packet</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.PacketHandler$handle(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/length">length</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Handles incoming packet


</td>
</tr>
</tbody>
</table>

### Companion Object Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="game-packets.html">gamePackets</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">gamePackets</span><span class="symbol">: </span><span class="identifier">PacketMap</span><span class="symbol">&lt;</span><a href="./index.html"><span class="identifier">PacketHandler</span></a><span class="symbol">&gt;</span></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.network.in/-command-handler/index.html">CommandHandler</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">CommandHandler</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">PacketHandler</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.network.in/-interface-handler/index.html">InterfaceHandler</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">InterfaceHandler</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">PacketHandler</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.network.in/-walking-handler/index.html">WalkingHandler</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">WalkingHandler</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">PacketHandler</span></a></code></div>

</td>
</tr>
</tbody>
</table>
