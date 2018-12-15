---
title: EntityChunkSystem.get - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugins.entity.systems.map</a> / <a href="index.html">EntityChunkSystem</a> / <a href="./get.html">get</a></div>

# get

<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">get</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem$get(worlds.gregs.hestia.game.plugins.core.components.map.Position)/position">position</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html"><span class="identifier">Position</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span></code></div>

Returns a list of all the entities local to a chunk

### Parameters

<code>position</code> - initial chunk

<code>size</code> - How large of a radius of chunks to get TODO

**Return**
list of all entity ids

</div>
<div class="overload-group" markdown="1">

<div class="signature"><code><span class="keyword">fun </span><span class="identifier">get</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem$get(kotlin.Int, kotlin.Int)/regionId">regionId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.plugins.entity.systems.map.EntityChunkSystem$get(kotlin.Int, kotlin.Int)/plane">plane</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html"><span class="identifier">List</span></a><span class="symbol">&lt;</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">&gt;</span></code></div>

Returns a list of all the entities in a region

### Parameters

<code>regionId</code> -

**Return**
list of all entity ids

</div>
