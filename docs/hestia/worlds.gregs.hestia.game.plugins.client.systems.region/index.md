---
title: worlds.gregs.hestia.game.plugins.client.systems.region - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../index.html">hestia</a> / <a href="./index.html">worlds.gregs.hestia.game.plugins.client.systems.region</a></div>

## Package worlds.gregs.hestia.game.plugins.client.systems.region

### Types

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-client-region-change-system/index.html">ClientRegionChangeSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClientRegionChangeSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../worlds.gregs.hestia.game.api/-subscription-system/index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

ClientRegionChangeSystem
Sends the initial region on client login
Sets <a href="../worlds.gregs.hestia.game.plugins.client.components/-last-loaded-region/index.html">LastLoadedRegion</a> when subsequent region updates are requested


</td>
</tr>
<tr>
<td markdown="1">

<a href="-client-region-load-system/index.html">ClientRegionLoadSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClientRegionLoadSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">IteratingSystem</span></code></div>

ClientRegionLoadSystem
Sends map region updates to the client


</td>
</tr>
<tr>
<td markdown="1">

<a href="-region-sender-system/index.html">RegionSenderSystem</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">RegionSenderSystem</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../worlds.gregs.hestia.game.api/-subscription-system/index.html"><span class="identifier">SubscriptionSystem</span></a></code></div>

</td>
</tr>
</tbody>
</table>
