package com.blinkev.weathertest.ui

import androidx.lifecycle.ViewModel
import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.util.toDataStatus
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModelImpl<C> : ViewModel(), BaseViewModel<C> {

    override var screenComponent: C? = null

    protected val compositeDisposable = CompositeDisposable()
    private val operationSet = hashSetOf<Int>()

    override fun onCleared() = compositeDisposable.clear()

    protected fun <T> Observable<DataStatus<T>>.trackAndSubscribe(operationId: Int, onNext: (DataStatus<T>) -> Unit) {
        if (!operationSet.contains(operationId)) {
            compositeDisposable.add(this
                .doOnNext {
                    when (it) {
                        is DataStatus.Error -> operationSet.remove(operationId)
                    }
                }
                .doFinally { operationSet.remove(operationId) }
                .doOnSubscribe { operationSet.add(operationId) }
                .onErrorResumeNext { t: Throwable -> Observable.just(DataStatus.error(t)) }
                .subscribe(onNext)
            )
        }
    }
}