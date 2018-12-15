---
title: UpdateFlags - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api.client</a> / <a href="./index.html">UpdateFlags</a></div>

# UpdateFlags

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">UpdateFlags</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">IteratingSystem</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">UpdateFlags</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.client.UpdateFlags$<init>(com.artemis.Aspect.Builder)/builder">builder</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="update-flags.html">updateFlags</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">updateFlags</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html"><span class="identifier">ArrayList</span></a><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.update/-update-flag/index.html"><span class="identifier">UpdateFlag</span></a><span class="symbol">&gt;</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="create.html">create</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">create</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.client.UpdateFlags$create(kotlin.Int, com.artemis.Aspect.Builder, worlds.gregs.hestia.game.update.UpdateEncoder, kotlin.Boolean)/mask">mask</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.client.UpdateFlags$create(kotlin.Int, com.artemis.Aspect.Builder, worlds.gregs.hestia.game.update.UpdateEncoder, kotlin.Boolean)/aspect">aspect</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.client.UpdateFlags$create(kotlin.Int, com.artemis.Aspect.Builder, worlds.gregs.hestia.game.update.UpdateEncoder, kotlin.Boolean)/unit">unit</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-update-encoder/index.html"><span class="identifier">UpdateEncoder</span></a><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.api.client.UpdateFlags$create(kotlin.Int, com.artemis.Aspect.Builder, worlds.gregs.hestia.game.update.UpdateEncoder, kotlin.Boolean)/added">added</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a>&nbsp;<span class="symbol">=</span>&nbsp;false<br/><span class="symbol">)</span><span class="symbol">: </span><a href="../../worlds.gregs.hestia.game.update/-update-flag/index.html"><span class="identifier">UpdateFlag</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="insert.html">insert</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">insert</span><span class="symbol">(</span><span class="keyword">vararg</span> <span class="parameterName" id="worlds.gregs.hestia.game.api.client.UpdateFlags$insert(kotlin.Array((worlds.gregs.hestia.game.update.UpdateFlag)))/flags">flags</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-update-flag/index.html"><span class="identifier">UpdateFlag</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="insert-after.html">insertAfter</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">fun </span><span class="identifier">insertAfter</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.client.UpdateFlags$insertAfter(kotlin.Int, worlds.gregs.hestia.game.update.UpdateFlag)/mask">mask</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">, </span><span class="parameterName" id="worlds.gregs.hestia.game.api.client.UpdateFlags$insertAfter(kotlin.Int, worlds.gregs.hestia.game.update.UpdateFlag)/flag">flag</span><span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-update-flag/index.html"><span class="identifier">UpdateFlag</span></a><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

Flags have to be in a very specific order to work


</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../-mob-update-flags/index.html">MobUpdateFlags</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">MobUpdateFlags</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateFlags</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../-player-update-flags/index.html">PlayerUpdateFlags</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">PlayerUpdateFlags</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateFlags</span></a></code></div>

</td>
</tr>
</tbody>
</table>
