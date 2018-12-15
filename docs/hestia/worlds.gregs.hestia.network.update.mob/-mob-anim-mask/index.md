---
title: MobAnimMask - hestia
layout: api
---

<div class='api-docs-breadcrumbs'><a href="../../index.html">hestia</a> / <a href="../index.html">worlds.gregs.hestia.network.update.mob</a> / <a href="./index.html">MobAnimMask</a></div>

# MobAnimMask

<div class="signature"><code><span class="keyword">class </span><span class="identifier">MobAnimMask</span>&nbsp;<span class="symbol">:</span>&nbsp;<a href="../../worlds.gregs.hestia.game.update/-update-encoder/index.html"><span class="identifier">UpdateEncoder</span></a></code></div>

### Constructors

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="-init-.html">&lt;init&gt;</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="identifier">MobAnimMask</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.update.mob.MobAnimMask$<init>(com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.FirstAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.SecondAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.ThirdAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.FourthAnimation)))/firstAnimationMapper">firstAnimationMapper</span><span class="symbol">:</span>&nbsp;<span class="identifier">ComponentMapper</span><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.plugins.entity.components.update.anim/-first-animation/index.html"><span class="identifier">FirstAnimation</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.update.mob.MobAnimMask$<init>(com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.FirstAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.SecondAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.ThirdAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.FourthAnimation)))/secondAnimationMapper">secondAnimationMapper</span><span class="symbol">:</span>&nbsp;<span class="identifier">ComponentMapper</span><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.plugins.entity.components.update.anim/-second-animation/index.html"><span class="identifier">SecondAnimation</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.update.mob.MobAnimMask$<init>(com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.FirstAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.SecondAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.ThirdAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.FourthAnimation)))/thirdAnimationMapper">thirdAnimationMapper</span><span class="symbol">:</span>&nbsp;<span class="identifier">ComponentMapper</span><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.plugins.entity.components.update.anim/-third-animation/index.html"><span class="identifier">ThirdAnimation</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.network.update.mob.MobAnimMask$<init>(com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.FirstAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.SecondAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.ThirdAnimation)), com.artemis.ComponentMapper((worlds.gregs.hestia.game.plugins.entity.components.update.anim.FourthAnimation)))/fourthAnimationMapper">fourthAnimationMapper</span><span class="symbol">:</span>&nbsp;<span class="identifier">ComponentMapper</span><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.plugins.entity.components.update.anim/-fourth-animation/index.html"><span class="identifier">FourthAnimation</span></a><span class="symbol">&gt;</span><span class="symbol">)</span></code></div>

</td>
</tr>
</tbody>
</table>

### Properties

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="encode.html">encode</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">val </span><span class="identifier">encode</span><span class="symbol">: </span><span class="identifier">Builder</span><span class="symbol">.</span><span class="symbol">(</span><a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">,</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><span class="symbol">)</span>&nbsp;<span class="symbol">-&gt;</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html"><span class="identifier">Unit</span></a></code></div>

</td>
</tr>
</tbody>
</table>

### Inherited Functions

<table class="api-docs-table">
<tbody>
<tr>
<td markdown="1">

<a href="../../worlds.gregs.hestia.game.update/-update-encoder/get.html">get</a>


</td>
<td markdown="1">
<div class="signature"><code><span class="keyword">open</span> <span class="keyword">fun </span><span class="symbol">&lt;</span><span class="identifier">T</span>&nbsp;<span class="symbol">:</span>&nbsp;<span class="identifier">Component</span><span class="symbol">&gt;</span> <span class="identifier">get</span><span class="symbol">(</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.update.UpdateEncoder$get(com.artemis.ComponentMapper((worlds.gregs.hestia.game.update.UpdateEncoder.get.T)), kotlin.Int)/componentMapper">componentMapper</span><span class="symbol">:</span>&nbsp;<span class="identifier">ComponentMapper</span><span class="symbol">&lt;</span><a href="../../worlds.gregs.hestia.game.update/-update-encoder/get.html#T"><span class="identifier">T</span></a><span class="symbol">&gt;</span><span class="symbol">, </span><br/>&nbsp;&nbsp;&nbsp;&nbsp;<span class="parameterName" id="worlds.gregs.hestia.game.update.UpdateEncoder$get(com.artemis.ComponentMapper((worlds.gregs.hestia.game.update.UpdateEncoder.get.T)), kotlin.Int)/entityId">entityId</span><span class="symbol">:</span>&nbsp;<a href="https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html"><span class="identifier">Int</span></a><br/><span class="symbol">)</span><span class="symbol">: </span><a href="../../worlds.gregs.hestia.game.update/-update-encoder/get.html#T"><span class="identifier">T</span></a><span class="symbol">?</span></code></div>

</td>
</tr>
</tbody>
</table>
