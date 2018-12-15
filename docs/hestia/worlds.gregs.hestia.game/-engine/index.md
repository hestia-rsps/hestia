---
title: Engine - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game</a> / <a href="./index.html">Engine</a></div>

# Engine

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">Engine</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="http://docs.oracle.com/javase/9/docs/api/java/lang/Thread.html"><span class="identifier">Thread</span></a></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">Engine</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="current-time.html">currentTime</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">currentTime</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html"><span class="identifier">Long</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="is-running.html">isRunning</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">isRunning</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="sleep-time.html">sleepTime</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">sleepTime</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html"><span class="identifier">Long</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="run.html">run</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">run</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="start.html">start</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">start</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="tick.html">tick</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">tick</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.Engine$tick(kotlin.Long, kotlin.Float)/time">time</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html"><span class="identifier">Long</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.Engine$tick(kotlin.Long, kotlin.Float)/delta">delta</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html"><span class="identifier">Float</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

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
<div class="signature"><code><span class="keyword">class </span><span class="identifier">GameServer</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Engine</span></a><span class="symbol">, </span><a href="../../worlds.gregs.hestia.network/-world-change-listener/index.html"><span class="identifier">WorldChangeListener</span></a></code></div>

</td>
</tr>
</tbody>
</table>
