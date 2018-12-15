---
title: TickTaskSystem.schedule - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.core.systems</a> / <a href="index.html">TickTaskSystem</a> / <a href="./schedule.html">schedule</a></div>

# schedule

<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">schedule</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem$schedule(worlds.gregs.hestia.game.events.TaskEvent)/event">event</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.events/-task-event/index.html"><span class="identifier">TaskEvent</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</div>
<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">schedule</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem$schedule(kotlin.Int, kotlin.Int, kotlin.Function1((worlds.gregs.hestia.game.TickTask, kotlin.Unit)))/delay">delay</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem$schedule(kotlin.Int, kotlin.Int, kotlin.Function1((worlds.gregs.hestia.game.TickTask, kotlin.Unit)))/period">period</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.core.systems.TickTaskSystem$schedule(kotlin.Int, kotlin.Int, kotlin.Function1((worlds.gregs.hestia.game.TickTask, kotlin.Unit)))/task">task</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game/-tick-task/index.html"><span class="identifier">TickTask</span></a><span class="symbol">.</span><span class="symbol">(</span><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Schedules a tick task

### Parameters

<code>delay</code> - the number of ticks to wait before starting (0 is instant)

<code>period</code> - how often to repeat the task until stopped (0 doesn't repeat)

<code>task</code> - the task to run

</div>
