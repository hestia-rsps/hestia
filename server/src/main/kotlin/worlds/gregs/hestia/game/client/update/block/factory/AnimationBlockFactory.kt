package worlds.gregs.hestia.game.client.update.block.factory

import com.artemis.ComponentMapper
import worlds.gregs.hestia.api.client.update.components.Renderable
import worlds.gregs.hestia.api.client.update.block.BlockFactory
import worlds.gregs.hestia.game.client.update.block.blocks.AnimationBlock
import worlds.gregs.hestia.api.client.update.components.anim.FirstAnimation
import worlds.gregs.hestia.api.client.update.components.anim.FourthAnimation
import worlds.gregs.hestia.api.client.update.components.anim.SecondAnimation
import worlds.gregs.hestia.api.client.update.components.anim.ThirdAnimation
import worlds.gregs.hestia.services.Aspect
import worlds.gregs.hestia.services.one

open class AnimationBlockFactory(flag: Int, mob: Boolean = false) : BlockFactory<AnimationBlock>(Aspect.all(Renderable::class).one(FirstAnimation::class, SecondAnimation::class, ThirdAnimation::class, FourthAnimation::class), flag = flag, mob = mob) {

    private lateinit var firstAnimationMapper: ComponentMapper<FirstAnimation>
    private lateinit var secondAnimationMapper: ComponentMapper<SecondAnimation>
    private lateinit var thirdAnimationMapper: ComponentMapper<ThirdAnimation>
    private lateinit var fourthAnimationMapper: ComponentMapper<FourthAnimation>

    override fun create(player: Int, other: Int): AnimationBlock {
        return AnimationBlock(flag, firstAnimationMapper.get(other)?.id
                ?: -1, secondAnimationMapper.get(other)?.id ?: -1, thirdAnimationMapper.get(other)?.id
                ?: -1, fourthAnimationMapper.get(other)?.id ?: -1, firstAnimationMapper.get(other)?.speed ?: 0)
    }

}