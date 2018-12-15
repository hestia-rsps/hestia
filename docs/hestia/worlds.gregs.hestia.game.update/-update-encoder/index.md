---
title: UpdateEncoder - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.game.update</a> / <a href="./index.html">UpdateEncoder</a></div>

# UpdateEncoder

<div class="signature"><code><span class="keyword">interface </span><span class="identifier">UpdateEncoder</span></code></div>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="encode.html">encode</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">abstract</span> <span class="keyword">val </span><span class="identifier">encode</span><span class="symbol">: </span><span class="identifier">Builder</span><span class="symbol">.</span><span class="symbol">(</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="get.html">get</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="symbol">&lt;</span><span class="identifier">T</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">Component</span><span class="symbol">&gt;</span> <span class="identifier">get</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.update.UpdateEncoder$get(com.artemis.ComponentMapper((worlds.gregs.hestia.game.update.UpdateEncoder.get.T)), kotlin.Int)/componentMapper">componentMapper</span><span class="symbol">:</span>&nbsp;<span class="identifier">ComponentMapper</span><span class="symbol">&lt;</span><a href="get.html#T"><span class="identifier">T</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.update.UpdateEncoder$get(com.artemis.ComponentMapper((worlds.gregs.hestia.game.update.UpdateEncoder.get.T)), kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="get.html#T"><span class="identifier">T</span></a><span class="symbol">?</span></code></div>

</td>
</tr>
</tbody>
</table>

### Inheritors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-clan-member-mask/index.html">ClanMemberMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ClanMemberMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-force-chat-mask/index.html">ForceChatMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">ForceChatMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-hits-mask/index.html">HitsMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">HitsMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-anim-mask/index.html">MobAnimMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobAnimMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-batch-animation-mask/index.html">MobBatchAnimationMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobBatchAnimationMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-colour-overlay-mask/index.html">MobColourOverlayMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobColourOverlayMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-combat-level-mask/index.html">MobCombatLevelMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobCombatLevelMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-facing-mask/index.html">MobFacingMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobFacingMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-force-movement-mask/index.html">MobForceMovementMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobForceMovementMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-graphic-mask/index.html">MobGraphicMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobGraphicMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-model-change-mask/index.html">MobModelChangeMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobModelChangeMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-name-mask/index.html">MobNameMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobNameMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-time-bar-mask/index.html">MobTimeBarMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobTimeBarMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-transform-mask/index.html">MobTransformMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobTransformMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.mob/-mob-watch-entity-mask/index.html">MobWatchEntityMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobWatchEntityMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-anim-mask/index.html">PlayerAnimMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerAnimMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-appearance-mask/index.html">PlayerAppearanceMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerAppearanceMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-batch-animation-mask/index.html">PlayerBatchAnimationMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerBatchAnimationMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-colour-overlay-mask/index.html">PlayerColourOverlayMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerColourOverlayMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-facing-mask/index.html">PlayerFacingMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerFacingMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-force-movement-mask/index.html">PlayerForceMovementMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerForceMovementMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-graphic-mask/index.html">PlayerGraphicMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerGraphicMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-mini-map-mask/index.html">PlayerMiniMapMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerMiniMapMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-move-type-mask/index.html">PlayerMoveTypeMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerMoveTypeMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-movement-mask/index.html">PlayerMovementMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerMovementMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-time-bar-mask/index.html">PlayerTimeBarMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerTimeBarMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-player-watch-entity-mask/index.html">PlayerWatchEntityMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">PlayerWatchEntityMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.network.update.player/-unknown-mask/index.html">UnknownMask</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">class </span><span class="identifier">UnknownMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="./index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

</td>
</tr>
</tbody>
</table>
