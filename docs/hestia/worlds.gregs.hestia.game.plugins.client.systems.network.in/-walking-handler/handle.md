---
title: WalkingHandler.handle - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.client.systems.network.in</a> / <a href="index.html">WalkingHandler</a> / <a href="./handle.html">handle</a></div>

# handle

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">handle</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.network.in.WalkingHandler$handle(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.network.in.WalkingHandler$handle(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/packet">packet</span><span class="symbol">:</span>&nbsp;<span class="identifier">Packet</span><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.client.systems.network.in.WalkingHandler$handle(kotlin.Int, world.gregs.hestia.core.network.packets.Packet, kotlin.Int)/length">length</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Overrides <a href="../../worlds.gregs.hestia.game/-packet-handler/handle.html">PacketHandler.handle</a>

Handles incoming packet

### Parameters

<code>entityId</code> - The entity of the client whom sent the packet

<code>packet</code> - The packet received

<code>length</code> - The packet length read