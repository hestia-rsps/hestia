---
title: ViewDistanceSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.entity.systems</a> / <a href="./index.html">ViewDistanceSystem</a></div>

# ViewDistanceSystem

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">ViewDistanceSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">IteratingSystem</span></code></div>

This system prioritises keeping a square viewport over displaying the maximum entities possible

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">ViewDistanceSystem</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.ViewDistanceSystem$<init>(kotlin.reflect.KClass((worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem)), kotlin.Int, kotlin.Int, kotlin.reflect.KClass((com.artemis.Component)))/entityChunkSystem">entityChunkSystem</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html"><span class="identifier">KClass</span></a><span class="symbol">&lt;</span><span class="keyword">out</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.entity.systems.map/-entity-chunk-system/index.html"><span class="identifier">EntityChunkSystem</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.ViewDistanceSystem$<init>(kotlin.reflect.KClass((worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem)), kotlin.Int, kotlin.Int, kotlin.reflect.KClass((com.artemis.Component)))/maxViewDistance">maxViewDistance</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.ViewDistanceSystem$<init>(kotlin.reflect.KClass((worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem)), kotlin.Int, kotlin.Int, kotlin.reflect.KClass((com.artemis.Component)))/maxLocalEntities">maxLocalEntities</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.ViewDistanceSystem$<init>(kotlin.reflect.KClass((worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem)), kotlin.Int, kotlin.Int, kotlin.reflect.KClass((com.artemis.Component)))/viewDistance">viewDistance</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.reflect/-k-class/index.html"><span class="identifier">KClass</span></a><span class="symbol">&lt;</span><span class="keyword">out</span>&nbsp;<span class="identifier">Component</span><span class="symbol">&gt;</span><span class="symbol">)</span></code></div>

This system prioritises keeping a square viewport over displaying the maximum entities possible


</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="get-mapper.html">getMapper</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">fun </span><span class="identifier">getMapper</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><span class="identifier">ComponentMapper</span><span class="symbol">&lt;</span><span class="keyword">out</span>&nbsp;<span class="identifier">Component</span><span class="symbol">&gt;</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="process.html">process</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">process</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.ViewDistanceSystem$process(kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Companion Object Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-d-e-f-a-u-l-t_-v-i-e-w_-d-i-s-t-a-n-c-e.html">DEFAULT_VIEW_DISTANCE</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">DEFAULT_VIEW_DISTANCE</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.mob.systems.sync/-mob-view-distance-system/index.html">MobViewDistanceSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobViewDistanceSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">ViewDistanceSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.player.systems.sync/-player-view-distance-system/index.html">PlayerViewDistanceSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerViewDistanceSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">ViewDistanceSystem</span></a></code></div>

</td>
</tr>
</tbody>
</table>
