---
title: Plugin - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.plugin</a> / <a href="./index.html">Plugin</a></div>

# Plugin

<div class="signature"><code><span class="keyword">interface </span><span class="identifier">Plugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">ArtemisPlugin</span></code></div>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="active.html">active</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">active</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Companion Object Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-e-n-t-i-t-y_-i-n-d-e-x_-p-r-i-o-r-i-t-y.html">ENTITY_INDEX_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">ENTITY_INDEX_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-i-n-t-e-r-f-a-c-e_-p-r-i-o-r-i-t-y.html">INTERFACE_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">INTERFACE_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-l-o-g-i-n_-d-e-t-a-i-l-s_-p-r-i-o-r-i-t-y.html">LOGIN_DETAILS_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">LOGIN_DETAILS_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-m-a-p_-r-e-g-i-o-n_-p-r-i-o-r-i-t-y.html">MAP_REGION_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">MAP_REGION_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-m-o-b-i-l-e_-p-r-i-o-r-i-t-y.html">MOBILE_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">MOBILE_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-m-o-v-e_-p-r-i-o-r-i-t-y.html">MOVE_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">MOVE_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-n-a-v-i-g-a-t-i-o-n_-p-r-i-o-r-i-t-y.html">NAVIGATION_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">NAVIGATION_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-p-l-a-y-e-r_-i-n-d-e-x_-p-r-i-o-r-i-t-y.html">PLAYER_INDEX_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">PLAYER_INDEX_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-p-o-s-t_-u-p-d-a-t-e_-p-r-i-o-r-i-t-y.html">POST_UPDATE_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">POST_UPDATE_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-p-r-e_-s-h-i-f-t_-p-r-i-o-r-i-t-y.html">PRE_SHIFT_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">PRE_SHIFT_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-p-r-e_-s-y-n-c_-p-r-i-o-r-i-t-y.html">PRE_SYNC_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">PRE_SYNC_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-r-u-n_-p-r-i-o-r-i-t-y.html">RUN_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">RUN_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-s-h-i-f-t_-p-r-i-o-r-i-t-y.html">SHIFT_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">SHIFT_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-u-p-d-a-t-e_-c-h-a-n-g-e_-p-r-i-o-r-i-t-y.html">UPDATE_CHANGE_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">UPDATE_CHANGE_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-u-p-d-a-t-e_-d-i-s-p-l-a-y_-c-h-a-n-g-e_-p-r-i-o-r-i-t-y.html">UPDATE_DISPLAY_CHANGE_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">UPDATE_DISPLAY_CHANGE_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-u-p-d-a-t-e_-d-i-s-p-l-a-y_-f-l-a-g_-p-r-i-o-r-i-t-y.html">UPDATE_DISPLAY_FLAG_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">UPDATE_DISPLAY_FLAG_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-u-p-d-a-t-e_-f-i-n-i-s-h_-p-r-i-o-r-i-t-y.html">UPDATE_FINISH_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">UPDATE_FINISH_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-u-p-d-a-t-e_-f-l-a-g_-p-r-i-o-r-i-t-y.html">UPDATE_FLAG_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">UPDATE_FLAG_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-u-p-d-a-t-e_-g-l-o-b-a-l_-e-n-t-i-t-y_-p-r-i-o-r-i-t-y.html">UPDATE_GLOBAL_ENTITY_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">UPDATE_GLOBAL_ENTITY_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-u-p-d-a-t-e_-s-y-n-c_-p-r-i-o-r-i-t-y.html">UPDATE_SYNC_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">UPDATE_SYNC_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-u-p-d-a-t-e_-u-p-d-a-t-e_-p-r-i-o-r-i-t-y.html">UPDATE_UPDATE_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">UPDATE_UPDATE_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-v-i-e-w-p-o-r-t_-p-r-i-o-r-i-t-y.html">VIEWPORT_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">VIEWPORT_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="-w-a-l-k_-p-r-i-o-r-i-t-y.html">WALK_PRIORITY</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">const</span> <span class="keyword">val </span><span class="identifier">WALK_PRIORITY</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-bot-plugin/index.html">BotPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">BotPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-client-plugin/index.html">ClientPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClientPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-core-plugin/index.html">CorePlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">CorePlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-entity-plugin/index.html">EntityPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">EntityPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-land-plugin/index.html">LandPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">LandPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-map-plugin/index.html">MapPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MapPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-mob-plugin/index.html">MobPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-movement-plugin/index.html">MovementPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MovementPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-player-plugin/index.html">PlayerPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins/-region-plugin/index.html">RegionPlugin</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionPlugin</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">Plugin</span></a></code></div>

</td>
</tr>
</tbody>
</table>
