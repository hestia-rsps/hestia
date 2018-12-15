---
title: SubscriptionSystem - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.api</a> / <a href="./index.html">SubscriptionSystem</a></div>

# SubscriptionSystem

<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">SubscriptionSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">BaseEntitySystem</span></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">SubscriptionSystem</span><span class="symbol">(</span><span class="parameterName" id="worlds.gregs.hestia.game.api.SubscriptionSystem$<init>(com.artemis.Aspect.Builder)/aspect">aspect</span><span class="symbol">:</span>&nbsp;<span class="identifier">Builder</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="check-processing.html">checkProcessing</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">checkProcessing</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html"><span class="identifier">Boolean</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="process-system.html">processSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="identifier">processSystem</span><span class="symbol">(</span><span class="symbol">)</span><span class="symbol">: </span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.player.systems.update/-appearance-system/index.html">AppearanceSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">AppearanceSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-chunk-subscription/index.html">ChunkSubscription</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">ChunkSubscription</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.network/-client-connect-system/index.html">ClientConnectSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClientConnectSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.network/-client-disconnect-system/index.html">ClientDisconnectSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClientDisconnectSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.region/-client-region-change-system/index.html">ClientRegionChangeSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClientRegionChangeSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

ClientRegionChangeSystem
Sends the initial region on client login
Sets <a href="../../worlds.gregs.hestia.game.plugins.client.components/-last-loaded-region/index.html">LastLoadedRegion</a> when subsequent region updates are requested


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.entity.systems.map/-entity-chunk-system/index.html">EntityChunkSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">EntityChunkSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.entity.systems.sync/-entity-index-system/index.html">EntityIndexSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">EntityIndexSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

Doesn't really do anything, a back-up system for when player/mob plugins aren't attached
See <a href="#">PlayerIndexSystem</a> or <a href="#">MobIndexSystem</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.network/-interface-system/index.html">InterfaceSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">InterfaceSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.mob.systems.sync/-mob-index-system/index.html">MobIndexSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobIndexSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.player.systems.sync/-player-index-system/index.html">PlayerIndexSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerIndexSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.region/-region/index.html">Region</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">Region</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.region.systems/-region-clean-system/index.html">RegionCleanSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionCleanSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

Region Clean System
Unloads regions which has no prioritised entities in
Entities in this case refers to non-mob's (Players &amp; bots)


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.region.systems.load/-region-file-system/index.html">RegionFileSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionFileSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

RegionFileSystem
Loads the maps from the cache and passes the data off to <a href="../../worlds.gregs.hestia.game.api.map/-map-settings/index.html">MapSettings</a> &amp; <a href="../../worlds.gregs.hestia.game.api.land/-land-objects/index.html">LandObjects</a>


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.client.systems.region/-region-sender-system/index.html">RegionSenderSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionSenderSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.movement/-region-subscription/index.html">RegionSubscription</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">RegionSubscription</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.api.region/-regions/index.html">Regions</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">class </span><span class="identifier">Regions</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

Regions
Holds list of regions


</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.plugins.core.systems/-viewport-system/index.html">ViewportSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ViewportSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

ViewportSystem
Adds a player to it's own viewport &amp; flags <a href="../../worlds.gregs.hestia.game.plugins.core.components.entity/-created/index.html">Created</a>


</td>
</tr>
</tbody>
</table>
