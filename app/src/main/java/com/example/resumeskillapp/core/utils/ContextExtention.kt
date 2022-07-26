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

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(text: CharSequence) =
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.toast(@StringRes textId: Int) =
    Toast.makeText(this, textId, Toast.LENGTH_SHORT).show()

fun Context.getCurrentTheme(nightModeOn: () -> Unit, nightModeOff: () -> Unit) {
    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> { nightModeOff() }
        Configuration.UI_MODE_NIGHT_YES -> { nightModeOn() }
    }
}

