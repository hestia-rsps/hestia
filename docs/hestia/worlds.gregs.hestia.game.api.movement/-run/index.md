---
title: Run - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.movement</a> / <a href="./index.html">Run</a></div>

# Run

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">Run</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">IteratingSystem</span></code></div>

Run

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">Run</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.Run$<init>(com.artemis.Aspect.Builder)/builder">builder</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">)</span></code></div>

Run


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="has-step.html">hasStep</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">hasStep</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.Run$hasStep(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="is-running.html">isRunning</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">isRunning</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.movement.Run$isRunning(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.movement.systems/-run-system/index.html">RunSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RunSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Run</span></a></code></div>

RunSystem
Processes entity run steps
Entity must have <a href="../../worlds.gregs.hestia.game.plugins.movement.components/-run-toggled/index.html">RunToggled</a> toggled


</td>
</tr>
</tbody>
</table>
