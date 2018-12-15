---
title: ForceMovement - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.entity.components.update</a> / <a href="./index.html">ForceMovement</a></div>

# ForceMovement

<div class="signature"><code><span class="identifier">@PooledWeaver</span> <span class="keyword">class </span><span class="identifier">ForceMovement</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">Component</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">ForceMovement</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.components.update.ForceMovement$<init>(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int)/first">first</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.components.update.ForceMovement$<init>(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int)/delay">delay</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.components.update.ForceMovement$<init>(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int)/second">second</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">?</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.components.update.ForceMovement$<init>(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int)/secondDelay">secondDelay</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.components.update.ForceMovement$<init>(worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, worlds.gregs.hestia.game.plugins.core.components.map.Position, kotlin.Int, kotlin.Int)/direction">direction</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span></code></div>

<div class="signature"><code><span class="identifier">ForceMovement</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="direction.html">direction</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">direction</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="first-delay.html">firstDelay</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">firstDelay</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="first-position.html">firstPosition</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">firstPosition</span><span class="symbol">: </span><a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">?</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="second-delay.html">secondDelay</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">secondDelay</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="second-position.html">secondPosition</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">var </span><span class="identifier">secondPosition</span><span class="symbol">: </span><a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">?</span></code></div>

</td>
</tr>
</tbody>
</table>

### Companion Object Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-e-a-s-t.html">EAST</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">EAST</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-n-o-r-t-h.html">NORTH</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">NORTH</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-s-o-u-t-h.html">SOUTH</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">SOUTH</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-w-e-s-t.html">WEST</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">WEST</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
</tbody>
</table>
