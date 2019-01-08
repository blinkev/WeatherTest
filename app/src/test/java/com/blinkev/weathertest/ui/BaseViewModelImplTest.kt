package com.blinkev.weathertest.ui

import com.blinkev.weathertest.domain.DataStatus
import com.blinkev.weathertest.domain.util.toDataStatus
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

class BaseViewModelImplTest {

    class MockComponent

    interface MockRepo {
        fun get(): Observable<DataStatus<Boolean>>
    }

    class MockViewModel(private val repo: MockRepo) : BaseViewModelImpl<MockComponent>() {

        fun disposeAllRequests() {
            onCleared()
        }

        fun runRequest(onNext: (DataStatus<Boolean>) -> Unit = {}) {
            repo.get().trackAndSubscribe(
                operationId = repo.hashCode(),
                onNext = onNext
            )
        }

        fun runRequestWithMapOperator(
            mapFunc: (DataStatus<Boolean>) -> DataStatus<Int>,
            onNext: (DataStatus<Int>) -> Unit = {}
        ) {
            repo.get()
                .map(mapFunc::invoke)
                .trackAndSubscribe(
                    operationId = repo.hashCode(),
                    onNext = onNext
                )
        }
    }

    private lateinit var repo: MockRepo
    private lateinit var viewModel: MockViewModel

    @Before
    fun setUp() {
        repo = mock {}
        viewModel = MockViewModel(repo)
    }

    @Test
    fun `trackAndSubscribe() - when operation is already in progress we should not subscribe to the second one`() {
        var subscribeCount = 0
        whenever(repo.get()).thenReturn(Observable.never<Boolean>().doOnSubscribe { subscribeCount++ }.toDataStatus())

        for (i in 0..1) viewModel.runRequest()

        assertEquals(1, subscribeCount)
    }

    @Test
    fun `trackAndSubscribe() - when error occurs in repo we can subscribe to the second similar operation`() {
        var subscribeCount = 0
        whenever(repo.get()).thenReturn(
            Observable.error<Boolean>(IOException()).doOnSubscribe { subscribeCount++ }.toDataStatus()
        )

        for (i in 0..1) viewModel.runRequest()

        assertEquals(2, subscribeCount)
    }

    @Test
    fun `trackAndSubscribe() - when error occurs in repo we should get error in onNext function`() {
        val error = IOException()
        whenever(repo.get()).thenReturn(Observable.error<Boolean>(error).toDataStatus())

        viewModel.runRequest {
            assertEquals(error, (it as? DataStatus.Error)?.error)
        }
    }

    @Test
    fun `trackAndSubscribe() - when repo emits data we should get it onError function`() {
        val data = true
        whenever(repo.get()).thenReturn(Observable.just(DataStatus.data(data)))

        viewModel.runRequest {
            assertEquals(data, (it as? DataStatus.Data)?.data)
        }
    }

    @Test
    fun `trackAndSubscribe() with additional operator before it - when error occurs in operator we can subscribe to the second similar operation`() {
        var subscribeCount = 0
        whenever(repo.get()).thenReturn(Observable.just(true).doOnSubscribe { subscribeCount++ }.toDataStatus())

        for (i in 0..1) viewModel.runRequestWithMapOperator(mapFunc = { throw IOException() })

        assertEquals(2, subscribeCount)
    }

    @Test
    fun `trackAndSubscribe() - when subscription onComplete we can subscribe to the second similar operation`() {
        var subscribeCount = 0
        whenever(repo.get()).thenReturn(Observable.empty<Boolean>().doOnSubscribe { subscribeCount++ }.toDataStatus())

        for (i in 0..1) viewModel.runRequest()

        assertEquals(2, subscribeCount)
    }

    @Test
    fun `trackAndSubscribe() - when subscription is disposed we can subscribe to the second similar operation`() {
        var subscribeCount = 0
        whenever(repo.get()).thenReturn(Observable.never<Boolean>().doOnSubscribe { subscribeCount++ }.toDataStatus())

        viewModel.runRequest()
        viewModel.disposeAllRequests()
        viewModel.runRequest()

        assertEquals(2, subscribeCount)
    }

    @Test
    fun `trackAndSubscribe() with additional operator before it - when error occurs inside operator we should get it in onNext function`() {
        whenever(repo.get()).thenReturn(Observable.just(true).toDataStatus())
        val error = IOException()

        viewModel.runRequestWithMapOperator(mapFunc = { throw error }) {
            assertEquals(error, (it as? DataStatus.Error)?.error)
        }
    }
}