---
title: Hit - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.events</a> / <a href="./index.html">Hit</a></div>

# Hit

<div class="signature"><code><span class="keyword">class </span><span class="identifier">Hit</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">Event</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">Hit</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.Hit$<init>(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Int, kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.Hit$<init>(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Int, kotlin.Int)/amount">amount</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.Hit$<init>(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Int, kotlin.Int)/mark">mark</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.Hit$<init>(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Int, kotlin.Int)/delay">delay</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a>&nbsp;<span class="symbol">=</span>&nbsp;0<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.Hit$<init>(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Int, kotlin.Int)/critical">critical</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a>&nbsp;<span class="symbol">=</span>&nbsp;false<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.Hit$<init>(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Int, kotlin.Int)/source">source</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a>&nbsp;<span class="symbol">=</span>&nbsp;-1<span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.events.Hit$<init>(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Boolean, kotlin.Int, kotlin.Int)/soak">soak</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a>&nbsp;<span class="symbol">=</span>&nbsp;-1<span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="amount.html">amount</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">amount</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="critical.html">critical</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">critical</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="delay.html">delay</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">delay</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
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

<a href="mark.html">mark</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">mark</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="soak.html">soak</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">soak</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="source.html">source</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">source</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
</tbody>
</table>
