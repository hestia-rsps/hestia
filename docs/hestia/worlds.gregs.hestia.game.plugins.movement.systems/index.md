---
title: worlds.gregs.hestia.game.plugins.movement.systems - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../index.html">hestia</a> / <a href="./index.html">worlds.gregs.hestia.game.plugins.movement.systems</a></div>

## Package worlds.gregs.hestia.game.plugins.movement.systems

### Types

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-mobile-system/index.html">MobileSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobileSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">IteratingSystem</span></code></div>

MobileSystem
Handles entities which are mobile
TODO this could run on delta's so it only processes when position has changed


</td>
</tr>
<tr>
<td markdown="1">

<a href="-move-system/index.html">MoveSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MoveSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../worlds.gregs.hestia.game.api.movement/-move/index.html"><span class="identifier">Move</span></a></code></div>

MoveSystem
Instantly moves player to <a href="../worlds.gregs.hestia.game.plugins.core.components.map/-position/index.html">Position</a>
Aka teleporting without delay &amp; animations


</td>
</tr>
<tr>
<td markdown="1">

<a href="-position-shift-system/index.html">PositionShiftSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PositionShiftSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">IteratingSystem</span></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-run-system/index.html">RunSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RunSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../worlds.gregs.hestia.game.api.movement/-run/index.html"><span class="identifier">Run</span></a></code></div>

RunSystem
Processes entity run steps
Entity must have <a href="../worlds.gregs.hestia.game.plugins.movement.components/-run-toggled/index.html">RunToggled</a> toggled


</td>
</tr>
<tr>
<td markdown="1">

<a href="-walk-system/index.html">WalkSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">WalkSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../worlds.gregs.hestia.game.api.movement/-walk/index.html"><span class="identifier">Walk</span></a></code></div>

WalkSystem
Processes entity walk steps


</td>
</tr>
</tbody>
</table>
