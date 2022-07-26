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

import com.example.resumeskillapp.core.AppManager
import com.kaopiz.kprogresshud.KProgressHUD

interface ProgressDialog {
    fun showProgressDialog()
    fun hideProgressDialog()
}

class ProgressDialogImpl : ProgressDialog {

    private val progressBar = KProgressHUD
        .create(AppManager.activityContext())
        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
        .setCancellable(true)
        .setAnimationSpeed(ANIMATION_SPEED)
        .setDimAmount(DIM_AMOUNT)

    override fun showProgressDialog() {
        if (!progressBar.isShowing) progressBar.show()
    }

    override fun hideProgressDialog() {
        if (progressBar.isShowing) progressBar.dismiss()
    }

    companion object {
        const val ANIMATION_SPEED = 2
        const val DIM_AMOUNT = 0.5f
    }
}