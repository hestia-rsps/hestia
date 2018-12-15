---
title: ClientNetwork - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.client</a> / <a href="./index.html">ClientNetwork</a></div>

# ClientNetwork

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">ClientNetwork</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">ClientNetwork</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="setup.html">setup</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">setup</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.client.ClientNetwork$setup(kotlin.Int, io.netty.channel.Channel)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.client.ClientNetwork$setup(kotlin.Int, io.netty.channel.Channel)/channel">channel</span><span class="symbol">:</span>&nbsp;<span class="identifier">Channel</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Sets up the entities client network


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.network/-client-network-system/index.html">ClientNetworkSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClientNetworkSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">ClientNetwork</span></a></code></div>

ClientNetworkSystem
Sets up the client-&gt;server <a href="../../worlds.gregs.hestia.game.plugins.client.components/-network-session/index.html">NetworkSession</a>


</td>
</tr>
</tbody>
</table>
