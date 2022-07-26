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

package com.example.resumeskillapp.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.resumeskillapp.R
import com.example.resumeskillapp.core.App
import com.example.resumeskillapp.core.AppManager.activityContext
import com.example.resumeskillapp.core.utils.ProgressDialog
import com.example.resumeskillapp.core.utils.isNetworkAvailable
import com.example.resumeskillapp.core.utils.toast
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get

abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val progressBar: ProgressDialog = App.instance.get()

    protected fun <T> Single<T>.getInfo(
        doOnSuccess: (T) -> Unit = {},
        doOnError: (Throwable) -> Unit = {},
        doOnComplete: () -> Unit = {},
        showProgress: Boolean = true
    ) {
        if (showProgress) progressBar.showProgressDialog()
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                doOnSuccess.invoke(it)
                doOnComplete.invoke()
                progressBar.hideProgressDialog()
            }, {
                checkNetworkError(it)
                doOnError.invoke(it)
                doOnComplete.invoke()
                progressBar.hideProgressDialog()
            }).also { compositeDisposable.add(it) }
    }

    protected fun <T> Single<T>.getWith(
        resultLiveData: MutableLiveData<T>,
        doOnError: (Throwable) -> Unit = {checkNetworkError(it)},
        doOnComplete: () -> Unit = {},
        showProgress: Boolean = true) {

        if (showProgress) progressBar.showProgressDialog()
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultLiveData.postValue(it)
                progressBar.hideProgressDialog()
            }, {
                checkNetworkError(it)
                doOnError.invoke(it)
                doOnComplete.invoke()
                progressBar.hideProgressDialog()
            }).also { compositeDisposable.add(it) }
    }

    protected fun <T> Observable<T>.getWith(
        resultLiveData: MutableLiveData<T>,
        doOnError: (Throwable) -> Unit = {checkNetworkError(it)},
        doOnComplete: () -> Unit = {},
        showProgress: Boolean = true) {

        if (showProgress) progressBar.showProgressDialog()
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultLiveData.postValue(it)
                progressBar.hideProgressDialog()
            }, {
                checkNetworkError(it)
                doOnError.invoke(it)
                doOnComplete.invoke()
                progressBar.hideProgressDialog()
            }, {
                doOnComplete.invoke()
                progressBar.hideProgressDialog()
            }).also { compositeDisposable.add(it) }
    }

    protected fun <T> Flowable<T>.getWith(
        resultLiveData: MutableLiveData<T>,
        doOnError: (Throwable) -> Unit = {checkNetworkError(it)},
        doOnComplete: () -> Unit = {},
        showProgress: Boolean = true) {

        if (showProgress) progressBar.showProgressDialog()
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                resultLiveData.postValue(it)
                progressBar.hideProgressDialog()
            }, {
                checkNetworkError(it)
                doOnError.invoke(it)
                doOnComplete.invoke()
                progressBar.hideProgressDialog()
            }, {
                doOnComplete.invoke()
                progressBar.hideProgressDialog()
            }).also { compositeDisposable.add(it) }
    }

    private fun checkNetworkError(throwable: Throwable) {
        if (!activityContext().isNetworkAvailable())
            activityContext().toast(R.string.error_inet_missing)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}