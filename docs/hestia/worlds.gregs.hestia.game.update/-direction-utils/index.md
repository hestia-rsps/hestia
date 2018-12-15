---
title: DirectionUtils - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.update</a> / <a href="./index.html">DirectionUtils</a></div>

# DirectionUtils

<div class="signature"><code><span class="keyword">class </span><span class="identifier">DirectionUtils</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">DirectionUtils</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Companion Object Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-d-e-l-t-a_-x.html">DELTA_X</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">DELTA_X</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html"><span class="identifier">IntArray</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-d-e-l-t-a_-y.html">DELTA_Y</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">DELTA_Y</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html"><span class="identifier">IntArray</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-r-e-g-i-o-n_-m-o-v-e-m-e-n-t.html">REGION_MOVEMENT</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">REGION_MOVEMENT</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html"><span class="identifier">Array</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html"><span class="identifier">IntArray</span></a><span class="symbol">&gt;</span></code></div>

</td>
</tr>
</tbody>
</table>

### Companion Object Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="get-face-direction.html">getFaceDirection</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getFaceDirection</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getFaceDirection(kotlin.Int, kotlin.Int)/xOffset">xOffset</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getFaceDirection(kotlin.Int, kotlin.Int)/yOffset">yOffset</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-mob-move-direction.html">getMobMoveDirection</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getMobMoveDirection</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getMobMoveDirection(kotlin.Int)/direction">direction</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-move-direction.html">getMoveDirection</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getMoveDirection</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getMoveDirection(kotlin.Int, kotlin.Int)/xOffset">xOffset</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getMoveDirection(kotlin.Int, kotlin.Int)/yOffset">yOffset</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-offset.html">getOffset</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getOffset</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getOffset(kotlin.Int, kotlin.Int)/current">current</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getOffset(kotlin.Int, kotlin.Int)/destination">destination</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-player-running-direction.html">getPlayerRunningDirection</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getPlayerRunningDirection</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getPlayerRunningDirection(kotlin.Int, kotlin.Int)/dx">dx</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getPlayerRunningDirection(kotlin.Int, kotlin.Int)/dy">dy</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="get-player-walking-direction.html">getPlayerWalkingDirection</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">getPlayerWalkingDirection</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getPlayerWalkingDirection(kotlin.Int, kotlin.Int)/dx">dx</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.update.DirectionUtils.Companion$getPlayerWalkingDirection(kotlin.Int, kotlin.Int)/dy">dy</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
</tbody>
</table>
