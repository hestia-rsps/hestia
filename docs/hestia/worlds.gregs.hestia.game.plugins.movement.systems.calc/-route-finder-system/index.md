---
title: RouteFinderSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.movement.systems.calc</a> / <a href="./index.html">RouteFinderSystem</a></div>

# RouteFinderSystem

<div class="signature"><code><span class="identifier">@Wire</span><span class="symbol">(</span>false<span class="symbol">)</span> <span class="keyword">class </span><span class="identifier">RouteFinderSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">PassiveSystem</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">RouteFinderSystem</span><span class="symbol">(</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="last-path-buffer-x.html">lastPathBufferX</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">lastPathBufferX</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html"><span class="identifier">IntArray</span></a></code></div>

Get's last path buffer x. Modifying the buffer in any way is prohibited.


</td>
</tr>
<tr>
<td markdown="1">

<a href="last-path-buffer-y.html">lastPathBufferY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">lastPathBufferY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int-array/index.html"><span class="identifier">IntArray</span></a></code></div>

Get's last path buffer y. Modifying the buffer in any way is prohibited.


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="find-route.html">findRoute</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">findRoute</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem$findRoute(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.path.RouteStrategy, kotlin.Boolean)/srcX">srcX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem$findRoute(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.path.RouteStrategy, kotlin.Boolean)/srcY">srcY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem$findRoute(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.path.RouteStrategy, kotlin.Boolean)/srcZ">srcZ</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem$findRoute(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.path.RouteStrategy, kotlin.Boolean)/sizeX">sizeX</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem$findRoute(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.path.RouteStrategy, kotlin.Boolean)/sizeY">sizeY</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem$findRoute(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.path.RouteStrategy, kotlin.Boolean)/strategy">strategy</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.path/-route-strategy/index.html"><span class="identifier">RouteStrategy</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.movement.systems.calc.RouteFinderSystem$findRoute(kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, kotlin.Int, worlds.gregs.hestia.game.path.RouteStrategy, kotlin.Boolean)/findAlternative">findAlternative</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

Find's route using given strategy. Returns amount of steps found. If
steps &gt; 0, route exists. If steps = 0, route exists, but no need to move.
If steps &lt; 0, route does not exist.


</td>
</tr>
<tr>
<td markdown="1">

<a href="last-is-alternative.html">lastIsAlternative</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">lastIsAlternative</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

Whether last path is only alternative path.


</td>
</tr>
</tbody>
</table>
