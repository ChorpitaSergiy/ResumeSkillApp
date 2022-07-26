/*
 *  MIT License
 *
 *  Copyright (C) 2022 Serhii Chorpita
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.example.resumeskillapp.core.utils

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

fun View.visibleWithAnimation(duration: Long) {
    if (View.VISIBLE == visibility) return
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, 0f, 1f).setDuration(duration)
    animator.addListener(object : SimpleAnimatorListener() {
        override fun onAnimationStart(animation: Animator?) {
            this@visibleWithAnimation.visible()
        }
    })
    animator.start()
}

fun View.goneWithAnimation(duration: Long, listener: () -> Unit) {
    if (View.GONE == visibility) return
    val animator = ObjectAnimator.ofFloat(this, View.ALPHA, 1f, 0f).setDuration(duration)
    animator.addListener(object : SimpleAnimatorListener() {
        override fun onAnimationEnd(animation: Animator?) {
            listener.invoke()
            this@goneWithAnimation.gone()
        }
    })
    animator.start()
}

fun View.visible() { visibility = View.VISIBLE }

fun View.gone() { visibility = View.GONE }

abstract class SimpleAnimatorListener : Animator.AnimatorListener {

    override fun onAnimationRepeat(animation: Animator?) = Unit

    override fun onAnimationEnd(animation: Animator?) = Unit

    override fun onAnimationCancel(animation: Animator?) = Unit

    override fun onAnimationStart(animation: Animator?) = Unit
}