---
title: TickTaskSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.core.systems</a> / <a href="./index.html">TickTaskSystem</a></div>

# TickTaskSystem

<div class="signature"><code><span class="keyword">class </span><span class="identifier">TickTaskSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">BaseSystem</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">TickTaskSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="tasks-count.html">tasksCount</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">tasksCount</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="check-processing.html">checkProcessing</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">checkProcessing</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="process-system.html">processSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">processSystem</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="schedule.html">schedule</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">schedule</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem$schedule(worlds.gregs.hestia.game.events.TaskEvent)/event">event</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.events/-task-event/index.html"><span class="identifier">TaskEvent</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">schedule</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem$schedule(kotlin.Int, kotlin.Int, kotlin.Function1((worlds.gregs.hestia.game.TickTask, kotlin.Unit)))/delay">delay</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem$schedule(kotlin.Int, kotlin.Int, kotlin.Function1((worlds.gregs.hestia.game.TickTask, kotlin.Unit)))/period">period</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem$schedule(kotlin.Int, kotlin.Int, kotlin.Function1((worlds.gregs.hestia.game.TickTask, kotlin.Unit)))/task">task</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game/-tick-task/index.html"><span class="identifier">TickTask</span></a><span class="symbol">.</span><span class="symbol">(</span><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Schedules a tick task


</td>
</tr>
</tbody>
</table>
