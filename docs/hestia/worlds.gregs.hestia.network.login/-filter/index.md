---
title: Filter - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.network.login</a> / <a href="./index.html">Filter</a></div>

# Filter

<div class="signature"><code><span class="identifier">@Sharable</span> <span class="keyword">class </span><span class="identifier">Filter</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">ChannelInboundHandlerAdapter</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">Filter</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="connections.html">connections</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">connections</span><span class="symbol">: </span><span class="identifier">ConcurrentHashMultiset</span><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html"><span class="identifier">String</span></a><span class="symbol">&gt;</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="channel-registered.html">channelRegistered</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">channelRegistered</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.login.Filter$channelRegistered(io.netty.channel.ChannelHandlerContext)/ctx">ctx</span><span class="symbol">:</span>&nbsp;<span class="identifier">ChannelHandlerContext</span><span class="symbol">?</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="channel-unregistered.html">channelUnregistered</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">channelUnregistered</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.login.Filter$channelUnregistered(io.netty.channel.ChannelHandlerContext)/ctx">ctx</span><span class="symbol">:</span>&nbsp;<span class="identifier">ChannelHandlerContext</span><span class="symbol">?</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-host.html">getHost</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getHost</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.login.Filter$getHost(io.netty.channel.Channel)/channel">channel</span><span class="symbol">:</span>&nbsp;<span class="identifier">Channel</span><span class="symbol">?</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html"><span class="identifier">String</span></a></code></div>

</td>
</tr>
</tbody>
</table>
