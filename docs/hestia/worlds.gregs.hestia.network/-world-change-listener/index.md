---
title: WorldChangeListener - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.network</a> / <a href="./index.html">WorldChangeListener</a></div>

# WorldChangeListener

<div class="signature"><code><span class="keyword">interface </span><span class="identifier">WorldChangeListener</span></code></div>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="disconnect.html">disconnect</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">disconnect</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.WorldChangeListener$disconnect(kotlin.Int)/id">id</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="init.html">init</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">init</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.network.WorldChangeListener$init(kotlin.Int)/id">id</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia/-game-server/index.html">GameServer</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">GameServer</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game/-engine/index.html"><span class="identifier">Engine</span></a><span class="symbol">, </span><a href="./index.html"><span class="identifier">WorldChangeListener</span></a></code></div>

</td>
</tr>
</tbody>
</table>
